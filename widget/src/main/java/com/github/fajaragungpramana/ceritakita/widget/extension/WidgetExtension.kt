package com.github.fajaragungpramana.ceritakita.widget.extension

import android.content.Context
import android.util.Patterns
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.shown(isShown: Boolean) {
    visibility = if (isShown) View.VISIBLE else View.INVISIBLE
}

fun Context.getApplicationColor(@ColorRes res: Int) = ContextCompat.getColor(this, res)

fun View.snackBar(message: String?, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(this, message ?: "", duration).show()


fun String?.isValidEmail() = Patterns.EMAIL_ADDRESS.matcher(this ?: "").matches()
fun String?.isValidPassword() = (this?.length ?: 0) > 6