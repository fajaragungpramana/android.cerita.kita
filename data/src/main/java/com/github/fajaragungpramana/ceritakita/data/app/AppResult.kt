package com.github.fajaragungpramana.ceritakita.data.app

sealed class AppResult<T> {

    data class OnSuccess<T>(val data: T?) : AppResult<T>()
    data class OnFailure<T>(val code: Int? = null, val data: T? = null) : AppResult<T>()
    data class OnError<T>(val throwable: Throwable) : AppResult<T>()

}