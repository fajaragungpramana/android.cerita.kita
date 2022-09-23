package com.github.fajaragungpramana.ceritakita.widget.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.size
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.viewpager2.widget.ViewPager2
import com.github.fajaragungpramana.ceritakita.widget.R
import com.github.fajaragungpramana.ceritakita.widget.extension.getApplicationColor

class AppDotIndicator(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private lateinit var mDots: ArrayList<AppDot>

    private lateinit var mDotSelected: AppDot

    private var mDotCount: Int

    var dotColor: Int

    var dotSelectedColor: Int

    var dotSize: Int

    var dotSpacing: Int

    var viewPager: ViewPager2? = null
        set(value) {
            onPageSelected(value)

            if (value != null) {
                removeAllViews()
                initDots()
                initDotSelected()
            }
        }

    init {
        gravity = Gravity.CENTER

        context.obtainStyledAttributes(attrs, R.styleable.AppDotIndicator).also {
            dotColor = it.getColor(R.styleable.AppDotIndicator_appDotIndicatorColor, context.getApplicationColor(R.color.color_secondary_gray))
            mDotCount = it.getInt(R.styleable.AppDotIndicator_appDotIndicatorCount, 5)
            dotSelectedColor = it.getColor(R.styleable.AppDotIndicator_appDotIndicatorSelectedColor, context.getApplicationColor(R.color.color_primary))
            dotSize = it.getDimension(R.styleable.AppDotIndicator_appDotIndicatorSize, 32F).toInt()
            dotSpacing = (it.getDimension(R.styleable.AppDotIndicator_appDotIndicatorSpacing, 8F) / 2).toInt()
        }.recycle()

        init()
        initDots()
        initDotSelected()
    }

    private fun init() {
        mDots = ArrayList()
        mDotSelected = AppDot(context).also {
            it.color = dotSelectedColor
            it.width = dotSize
            it.height = dotSize
        }
    }

    private fun initDots() {

        val dotContainer = LinearLayout(context).also {
            it.orientation = LinearLayout.HORIZONTAL
        }

        val dotParams = LayoutParams(dotSize, dotSize).also {
            it.marginStart = dotSpacing
            it.marginEnd = dotSpacing
        }

        mDots.clear()
        repeat(mDotCount) { i ->
            val dot = AppDot(context).also {
                it.color = dotColor
                it.width = dotSize
                it.height = dotSize
            }
            mDots.add(dot)

            dotContainer.addView(mDots[i], dotParams)
        }

        addView(dotContainer)
    }

    private fun initDotSelected() {
        val dotSelectedParams = LayoutParams(dotSize, dotSize).also {
            it.marginStart = dotSpacing
            it.marginEnd = dotSpacing
        }

        addView(mDotSelected, dotSelectedParams)
    }

    private fun onPageSelected(viewPager: ViewPager2?) {
        if (viewPager?.adapter != null) {
            mDotCount = viewPager.size + 1

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    onPageSelectedPosition(position)
                }
            })
        } else
            throw NullPointerException("No adapter ViewPager2 attached.")
    }

    private fun onPageSelectedPosition(position: Int) {
        SpringAnimation(
            mDotSelected,
            SpringAnimation.TRANSLATION_X,
            if (position == 0) 1F else mDots[position].x - dotSpacing
        ).start()
    }

}