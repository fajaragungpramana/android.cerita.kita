package com.github.fajaragungpramana.ceritakita.widget.extension

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun View.shown(isShown: Boolean) {
    visibility = if (isShown) View.VISIBLE else View.INVISIBLE
}

fun Context.getApplicationColor(@ColorRes res: Int) = ContextCompat.getColor(this, res)

fun Fragment.toast(message: String?, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(requireActivity(), message, duration).show()

fun View.snackBar(message: String?, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(this, message ?: "", duration).show()

inline fun View.snackBar(
    message: String?,
    actionMessage: String?,
    crossinline actionListener: (View) -> Unit,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, message ?: "", duration)
        .setAction(actionMessage) { actionListener.invoke(it) }
        .show()
}

fun String?.isValidName() = (this?.length ?: 0) > 6
fun String?.isValidEmail() = Patterns.EMAIL_ADDRESS.matcher(this ?: "").matches()
fun String?.isValidPassword() = (this?.length ?: 0) >= 6

fun Activity?.hideKeyboard() {
    if (this == null && this?.currentFocus == null)
        return

    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Context?.showKeyboard(editText: AppCompatEditText) {
    if (this == null) return

    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    Handler(mainLooper).postDelayed({
        editText.requestFocus()
        imm.showSoftInput(editText, 0)
    }, 100)
}