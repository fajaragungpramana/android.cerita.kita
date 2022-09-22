package com.github.fajaragungpramana.ceritakita.data.app

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

abstract class AppPagingDataSource<M : Any> : PagingSource<Int, M>() {

    protected abstract suspend fun onLoadSuccess(position: Int): LoadResult<Int, M>

    protected abstract fun onLoadFailure(position: Int, e: Exception): LoadResult<Int, M>

    override fun getRefreshKey(state: PagingState<Int, M>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, M> {
        val position = params.key ?: 1
        return try {
            onLoadSuccess(position)
        } catch (e: IOException) {
            onLoadFailure(position, e)
        } catch (e: HttpException) {
            onLoadFailure(position, e)
        } catch (e: UnknownHostException) {
            onLoadFailure(position, e)
        }
    }

}