package com.github.fajaragungpramana.ceritakita.extension

import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

fun Uri.toFile(context: Context): File {
    val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    val fileName = SimpleDateFormat(
        "dd-MM-yyyy HH:mm:ss",
        Locale.getDefault()
    ).format(System.currentTimeMillis())
    val file = File.createTempFile(fileName, ".jpg", directory)

    val inputStream = context.contentResolver.openInputStream(this) as InputStream
    val outputStream = FileOutputStream(file)

    val byteArray = ByteArray(1024)
    var i: Int

    while (inputStream.read(byteArray).also { i = it } > 0) outputStream.write(byteArray, 0, i)
    outputStream.close()
    inputStream.close()

    return file
}