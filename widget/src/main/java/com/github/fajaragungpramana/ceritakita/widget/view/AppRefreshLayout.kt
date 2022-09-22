package com.github.fajaragungpramana.ceritakita.widget.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ListView
import androidx.core.view.*
import androidx.core.widget.ListViewCompat
import com.github.fajaragungpramana.ceritakita.widget.R
import kotlin.math.abs

@SuppressLint("DrawAllocation")
open class AppRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ViewGroup(context, attrs, defStyle), NestedScrollingParent, NestedScrollingChild {

    private var notify: Boolean = true
    var isRefreshing: Boolean = false
        set(refreshing) {
            if (isRefreshing != refreshing) {
                field = refreshing
                if (refreshing) {
                    if (currentState != State.TRIGGERING) {
                        startRefreshing()
                    }
                } else {
                    notify = false
                    currentState = State.ROLLING
                    stopRefreshing()
                }
            }
        }

    var triggerOffSetTop = 0
        private set

    var maxOffSetTop = 0
        private set

    var overlay = true
        private set

    private var downX = 0F
    private var downY = 0F

    private var offsetY = 0F
    private var lastPullFraction = 0F

    private var currentState: State = State.IDLE

    private val onProgressListeners: MutableCollection<(Float) -> Unit> = mutableListOf()
    private val onTriggerListeners: MutableCollection<() -> Unit> = mutableListOf()

    var mNestedScrollingParentHelper: NestedScrollingParentHelper
    var mNestedScrollingChildHelper: NestedScrollingChildHelper
    private val mParentScrollConsumed = IntArray(2)
    private val mParentOffsetInWindow = IntArray(2)
    private var mNestedScrollInProgress = false

    companion object {
        private const val STICKY_FACTOR = 0.66F
        private const val STICKY_MULTIPLIER = 0.75F
        private const val ROLL_BACK_DURATION = 300L
        private const val DEFAULT_INDICATOR_TARGET = 64f
        const val ELEVATION = 4
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AppRefreshLayout,
            defStyle,
            0
        ).let {
            val defaultValue = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_INDICATOR_TARGET,
                context.resources.displayMetrics
            ).toInt()

            triggerOffSetTop = it.getDimensionPixelOffset(
                R.styleable.AppRefreshLayout_appRefreshLayoutTriggerOffsetTop,
                defaultValue
            )
            maxOffSetTop = it.getDimensionPixelOffset(
                R.styleable.AppRefreshLayout_appRefreshLayoutMaxOffsetTop,
                defaultValue * 2
            )

            if (maxOffSetTop <= triggerOffSetTop)
                maxOffSetTop = triggerOffSetTop * 2

            overlay = it.getBoolean(
                R.styleable.AppRefreshLayout_appRefreshLayoutIndicatorOverlay,
                overlay
            )
            it.recycle()
        }
        mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
        mNestedScrollingChildHelper = NestedScrollingChildHelper(this)
        isNestedScrollingEnabled = true
    }

    private lateinit var topChildView: ChildView
    private lateinit var contentChildView: ChildView

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (childCount != 2) {
            throw IllegalStateException("Only a topView and a contentView are allowed. Exactly 2 children are expected, but was $childCount")
        }

        topChildView = ChildView(getChildAt(0))
        contentChildView = ChildView(getChildAt(1))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        fun measureChild(childView: ChildView, widthMeasureSpec: Int, heightMeasureSpec: Int) {
            measureChildWithMargins(childView.view, widthMeasureSpec, 0, heightMeasureSpec, 0)
        }

        fun setInitialValues() {
            val topView = topChildView.view
            val layoutParams = topView.layoutParams as LayoutParams
            val topViewHeight =
                topView.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
            topChildView = topChildView.copy(positionAttr = PositionAttr(height = topViewHeight))
        }

        measureChild(topChildView, widthMeasureSpec, heightMeasureSpec)
        measureChild(contentChildView, widthMeasureSpec, heightMeasureSpec)

        setInitialValues()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layoutTopView()
        layoutContentView()
    }

    private fun layoutTopView() {
        val topView = topChildView.view
        val topViewAttr = topChildView.positionAttr

        val lp = topView.layoutParams as LayoutParams
        if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            val left: Int = paddingLeft + lp.leftMargin
            val top: Int = (paddingTop + lp.topMargin) - topViewAttr.height - ELEVATION
            val right: Int = left + topView.measuredWidth
            val bottom = -ELEVATION
            topChildView = topChildView.copy(
                positionAttr = PositionAttr(
                    left = left,
                    top = top,
                    right = right,
                    bottom = bottom
                )
            )
            topView.layout(left, top, right, bottom)
        } else {
            val indicatorWidth: Int = topView.measuredWidth
            val left: Int = width / 2 - indicatorWidth / 2
            val top: Int = (paddingTop + lp.topMargin) - topViewAttr.height - ELEVATION
            val right: Int = width / 2 + indicatorWidth / 2
            val bottom = -ELEVATION

            topChildView = topChildView.copy(
                positionAttr = PositionAttr(
                    left = left,
                    top = top,
                    right = right,
                    bottom = bottom
                )
            )
            topView.layout(left, top, right, bottom)
        }

    }

    private fun layoutContentView() {
        val contentView = contentChildView.view

        val lp = contentView.layoutParams as LayoutParams
        val left: Int = paddingLeft + lp.leftMargin
        val top: Int = paddingTop + lp.topMargin
        val right: Int = left + contentView.measuredWidth
        val bottom: Int = top + contentView.measuredHeight

        contentChildView = contentChildView.copy(
            positionAttr = PositionAttr(
                left = left,
                top = top,
                right = right,
                bottom = bottom
            )
        )
        contentView.layout(left, top, right, bottom)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isEnabled || isRefreshing || currentState == State.ROLLING || mNestedScrollInProgress || canChildScrollUp()) {
            return false
        }

        fun checkIfScrolledFurther(ev: MotionEvent, dy: Float, dx: Float) =
            if (!contentChildView.view.canScrollVertically(-1)) {
                ev.y > downY && abs(dy) > abs(dx)
            } else {
                false
            }

        var shouldStealTouchEvents = false

        if (currentState != State.IDLE) {
            shouldStealTouchEvents = false
        }

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - downX
                val dy = ev.y - downY
                shouldStealTouchEvents = checkIfScrolledFurther(ev, dy, dx)
            }
        }

        return shouldStealTouchEvents
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled || isRefreshing || currentState == State.ROLLING || mNestedScrollInProgress || canChildScrollUp()) {
            return false
        }

        var handledTouchEvent = true

        if (currentState != State.IDLE) {
            handledTouchEvent = false
        }

        parent.requestDisallowInterceptTouchEvent(true)
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                offsetY = (event.y - downY) * (1 - STICKY_FACTOR * STICKY_MULTIPLIER)
                notify = true
                move()
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP,
            -> {
                currentState = State.ROLLING
                stopRefreshing()
            }
        }

        return handledTouchEvent
    }

    open fun startRefreshing() {
        val triggerOffset: Float =
            if (offsetY > triggerOffSetTop) offsetY else triggerOffSetTop.toFloat()

        ValueAnimator.ofFloat(0F, 1F).apply {
            duration = ROLL_BACK_DURATION
            interpolator = DecelerateInterpolator(2f)
            addUpdateListener {
                positionChildren(triggerOffset * animatedValue as Float)
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    offsetY = triggerOffset
                }
            })
            start()
        }
    }

    private fun move() {
        val pullFraction: Float = (offsetY % triggerOffSetTop) / triggerOffSetTop

        offsetY = offsetY.coerceIn(0f, maxOffSetTop.toFloat())

        onProgressListeners.forEach { it(pullFraction) }
        lastPullFraction = pullFraction

        positionChildren(offsetY)
    }

    open fun stopRefreshing() {
        val rollBackOffset = if (offsetY > triggerOffSetTop) offsetY - triggerOffSetTop else offsetY
        val triggerOffset = if (rollBackOffset != offsetY) triggerOffSetTop else 0

        ValueAnimator.ofFloat(1F, 0F).apply {
            duration = ROLL_BACK_DURATION
            interpolator = DecelerateInterpolator(2f)
            addUpdateListener {
                positionChildren(triggerOffset + rollBackOffset * animatedValue as Float)
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    if (notify && triggerOffset != 0 && currentState == State.ROLLING) {
                        currentState = State.TRIGGERING
                        isRefreshing = true
                        offsetY = triggerOffset.toFloat()
                        onTriggerListeners.forEach { it() }
                    } else {
                        currentState = State.IDLE
                        offsetY = 0f
                    }
                }
            })
            start()
        }
    }

    private fun positionChildren(offset: Float) {
        topChildView.view.bringToFront()
        topChildView.view.y = topChildView.positionAttr.top + offset

        if (!overlay) {
            contentChildView.view.y = contentChildView.positionAttr.top + offset
        }
    }

    fun addProgressListener(onProgressListener: (Float) -> Unit) {
        onProgressListeners.add(onProgressListener)
    }

    open fun setOnRefreshListener(listener: () -> Unit) {
        onTriggerListeners.add(listener)
    }

    fun addTriggerListener(onTriggerListener: () -> Unit) {
        onTriggerListeners.add(onTriggerListener)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?) = null != p && p is LayoutParams

    override fun generateDefaultLayoutParams() =
        LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    override fun generateLayoutParams(attrs: AttributeSet?) = LayoutParams(context, attrs)

    override fun generateLayoutParams(p: ViewGroup.LayoutParams?) = LayoutParams(p)

    class LayoutParams : MarginLayoutParams {

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs)

        constructor(width: Int, height: Int) : super(width, height)
        constructor(source: ViewGroup.LayoutParams) : super(source)
    }

    enum class State {
        IDLE,
        ROLLING,
        TRIGGERING
    }

    data class ChildView(val view: View, val positionAttr: PositionAttr = PositionAttr())
    data class PositionAttr(
        val left: Int = 0,
        val top: Int = 0,
        val right: Int = 0,
        val bottom: Int = 0,
        val height: Int = 0
    )

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return (isEnabled && currentState != State.ROLLING && !isRefreshing
                && nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0)
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes)

        startNestedScroll(axes and ViewCompat.SCROLL_AXIS_VERTICAL)
        offsetY = 0f
        mNestedScrollInProgress = true
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        if (dy > 0 && offsetY > 0) {
            if (dy > offsetY) {
                consumed[1] = dy - offsetY.toInt()
                offsetY = 0f
            } else {
                offsetY -= dy.toFloat()
                consumed[1] = dy
            }
            move()
        }

        val parentConsumed: IntArray = mParentScrollConsumed
        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] += parentConsumed[0]
            consumed[1] += parentConsumed[1]
        }
    }

    override fun getNestedScrollAxes(): Int {
        return mNestedScrollingParentHelper.nestedScrollAxes
    }

    override fun onStopNestedScroll(target: View) {
        mNestedScrollingParentHelper.onStopNestedScroll(target)
        mNestedScrollInProgress = false
        if (offsetY > 0) {
            notify = true
            currentState = State.ROLLING
            stopRefreshing()
            offsetY = 0f
        }
        stopNestedScroll()
    }

    override fun onNestedScroll(
        target: View, dxConsumed: Int, dyConsumed: Int,
        dxUnconsumed: Int, dyUnconsumed: Int,
    ) {
        dispatchNestedScroll(
            dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
            mParentOffsetInWindow
        )

        val dy: Int = dyUnconsumed + mParentOffsetInWindow[1]
        if (dy < 0 && !canChildScrollUp()) {
            offsetY += abs(dy).toFloat()
            move()
        }
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mNestedScrollingChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return mNestedScrollingChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return mNestedScrollingChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
        dyUnconsumed: Int, offsetInWindow: IntArray?,
    ): Boolean {
        return mNestedScrollingChildHelper.dispatchNestedScroll(
            dxConsumed, dyConsumed,
            dxUnconsumed, dyUnconsumed, offsetInWindow
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(
            dx, dy, consumed, offsetInWindow
        )
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

    open fun canChildScrollUp(): Boolean {
        return if (contentChildView.view is ListView) {
            ListViewCompat.canScrollList(
                (contentChildView.view as ListView?) ?: ListView(context),
                -1
            )
        } else contentChildView.view.canScrollVertically(-1)
    }

}