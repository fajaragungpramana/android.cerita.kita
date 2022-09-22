package com.github.fajaragungpramana.ceritakita.data.extension

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

inline fun <T> AppResult<T>.onResultListener(
    onSuccess: (T?) -> Unit,
    onFailure: (Int?, T?) -> Unit,
    onError: (Throwable) -> Unit
) {
    when (this) {
        is AppResult.OnSuccess -> {
            onSuccess(this.data)
        }
        is AppResult.OnFailure -> {
            onFailure(this.code, this.data)
        }
        is AppResult.OnError -> {
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

inline fun <reified T, M> Response<T>.responseAsFlow(
    crossinline map: (T?) -> M
): AppResult<Flow<M>?> = if (this.isSuccessful) {
    AppResult.OnSuccess(flow { emit(map(this@responseAsFlow.body())) }.flowOn(Dispatchers.IO))
} else {
    val data = Gson().fromJson(errorBody()?.string(), T::class.java)
    AppResult.OnFailure(code = this.code(), data = flow { emit(map(data)) }.flowOn(Dispatchers.IO))
}

suspend fun <T> Flow<T>.flowAsValue(): T? {
    val job = CoroutineScope(Dispatchers.IO).async {
        var value: T? = null

        this@flowAsValue.collectLatest { value = it }

        value
    }

    return job.await()
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> Map<String, T?>.removeNulls(): Map<String, T> {
    return filterValues { it != null } as Map<String, T>
}