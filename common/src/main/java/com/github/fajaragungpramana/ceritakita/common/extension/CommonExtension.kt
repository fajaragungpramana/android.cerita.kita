package com.github.fajaragungpramana.ceritakita.common.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings

inline fun <reified T> Context.startActivity(vararg data: Pair<String, Any>) {
    val i = Intent(this, T::class.java)

    data.forEach {
        when (it.second) {
            is Int -> i.putExtra(it.first, it.second as Int)
            is Byte -> i.putExtra(it.first, it.second as Byte)
            is Char -> i.putExtra(it.first, it.second as Char)
            is Long -> i.putExtra(it.first, it.second as Long)
            is Short -> i.putExtra(it.first, it.second as Short)
            is Double -> i.putExtra(it.first, it.second as Double)
            is Boolean -> i.putExtra(it.first, it.second as Boolean)
            is Bundle -> i.putExtra(it.first, it.second as Bundle)
            is String -> i.putExtra(it.first, it.second as String)
            is IntArray -> i.putExtra(it.first, it.second as IntArray)
            is ByteArray -> i.putExtra(it.first, it.second as ByteArray)
            is CharArray -> i.putExtra(it.first, it.second as CharArray)
            is LongArray -> i.putExtra(it.first, it.second as LongArray)
            is FloatArray -> i.putExtra(it.first, it.second as FloatArray)
            is Parcelable -> i.putExtra(it.first, it.second as Parcelable)
            is ShortArray -> i.putExtra(it.first, it.second as ShortArray)
            is DoubleArray -> i.putExtra(it.first, it.second as DoubleArray)
            is BooleanArray -> i.putExtra(it.first, it.second as Boolean)
            is CharSequence -> i.putExtra(it.first, it.second as CharSequence)
            else -> throw TypeCastException("Can't passing data type ${it.second.javaClass.name}")
        }
    }

    startActivity(i)
}

fun Context.openDeviceSettings() {
    val i = Intent()
    i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    i.data = Uri.fromParts("package", this.packageName, null)

    startActivity(i)
}