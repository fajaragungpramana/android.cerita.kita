package com.github.fajaragungpramana.ceritakita.widget.extension

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Context.getApplicationColor(@ColorRes res: Int) = ContextCompat.getColor(this, res)

fun View.snackbar(message: String?, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(this, message ?: "", duration).show()