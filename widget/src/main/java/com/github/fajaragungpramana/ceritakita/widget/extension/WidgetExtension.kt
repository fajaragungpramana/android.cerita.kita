package com.github.fajaragungpramana.ceritakita.widget.extension

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getApplicationColor(@ColorRes res: Int) = ContextCompat.getColor(this, res)