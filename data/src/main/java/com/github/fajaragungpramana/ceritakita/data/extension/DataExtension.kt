package com.github.fajaragungpramana.ceritakita.data.extension

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

inline fun <T> AppResult<T>.onResultListener(
    onLoading: (Boolean) -> Unit,
    onSuccess: (T?) -> Unit,
    onFailure: (Int?) -> Unit,
    onError: (Throwable) -> Unit
) {
    onLoading(true)
    when (this) {
        is AppResult.OnSuccess -> {
            onLoading(false)
            onSuccess(this.data)
        }
        is AppResult.OnFailure -> {
            onLoading(false)
            onFailure(this.code)
        }
        is AppResult.OnError -> {
            onLoading(false)
            onError(this.throwable)
        }
    }
}

inline fun <T> connection(run: () -> AppResult<T>): AppResult<T> =
    try {
        run()
    } catch (e: Throwable) {
        AppResult.OnError(e)
    }

suspend fun <T> Flow<T>.flowAsValue(): T? {
    val job = CoroutineScope(Dispatchers.IO).async {
        var value: T? = null

        this@flowAsValue.collectLatest { value = it }

        value
    }

    return job.await()
}