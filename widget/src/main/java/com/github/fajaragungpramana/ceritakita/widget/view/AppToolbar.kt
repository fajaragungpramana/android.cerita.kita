package com.github.fajaragungpramana.ceritakita.widget.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.github.fajaragungpramana.ceritakita.widget.R
import com.github.fajaragungpramana.ceritakita.widget.databinding.AppToolbarBinding
import com.github.fajaragungpramana.ceritakita.widget.extension.getApplicationColor
import com.google.android.material.appbar.AppBarLayout

class AppToolbar(context: Context, attrs: AttributeSet?) : AppBarLayout(context, attrs) {

    private var mViewBinding: AppToolbarBinding

    var title: String?
        set(value) {
            mViewBinding.ctlToolbar.title = value
        }
        get() = mViewBinding.ctlToolbar.title.toString()

    var navigationIcon: Drawable?
        set(value) {
            mViewBinding.mtToolbar.navigationIcon = value
        }
        get() = mViewBinding.mtToolbar.navigationIcon

    var navigationIconColor: Int?
        set(value) {
            mViewBinding.mtToolbar.setNavigationIconTint(value ?: context.getApplicationColor(R.color.color_primary_black))
        }
        get() = mViewBinding.mtToolbar.navigationIconTint

    init {
        mViewBinding = AppToolbarBinding.inflate(LayoutInflater.from(context), this)

        context.obtainStyledAttributes(attrs, R.styleable.AppToolbar, 0, 0).apply {
            title = getString(R.styleable.AppToolbar_appToolbarTitle)
            navigationIcon = getDrawable(R.styleable.AppToolbar_appToolbarNavigationIcon)
            navigationIconColor =
                getColor(R.styleable.AppToolbar_appToolbarNavigationIconColor, context.getApplicationColor(R.color.color_primary_black))
        }.recycle()
    }

    fun onBackPress(invoke: (View) -> Unit) {
        mViewBinding.mtToolbar.setNavigationOnClickListener { invoke(it) }
    }

}