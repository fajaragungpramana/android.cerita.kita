package com.github.fajaragungpramana.ceritakita.widget.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.github.fajaragungpramana.ceritakita.widget.R

class AppLottieRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppRefreshLayout(context, attrs, defStyle) {

    private var animationFile: Int = -1
    private val lottieAnimationView by lazy {
        LottieAnimationView(context).apply {
            if (animationFile == -1) {
                throw IllegalStateException("Could not resolve an animation for your pull to refresh layout")
            }

            setAnimation(animationFile)
            repeatCount = LottieDrawable.INFINITE
            val size = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                48f,
                context.resources.displayMetrics
            ).toInt()

            layoutParams = LayoutParams(ViewGroup.LayoutParams(size, size))
        }
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AppLottieRefreshLayout,
            defStyle,
            0
        ).let { style ->
            animationFile =
                style.getResourceId(R.styleable.AppLottieRefreshLayout_appLottieRefreshLayoutLottieRawRes, -1)
            addView(lottieAnimationView)
            style.recycle()
        }

        addProgressListener {
            lottieAnimationView.progress = it / 2
        }

        addTriggerListener {
            lottieAnimationView.resumeAnimation()
        }
    }

    override fun stopRefreshing() {
        super.stopRefreshing()
        lottieAnimationView.pauseAnimation()
    }

    override fun startRefreshing() {
        super.startRefreshing()
        lottieAnimationView.resumeAnimation()
    }
}