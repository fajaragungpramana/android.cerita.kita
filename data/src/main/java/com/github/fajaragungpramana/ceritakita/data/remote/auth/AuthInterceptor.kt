package com.github.fajaragungpramana.ceritakita.data.remote.auth

import com.github.fajaragungpramana.ceritakita.data.constant.DataConstant
import com.github.fajaragungpramana.ceritakita.data.event.AppEvent
import com.github.fajaragungpramana.ceritakita.data.local.preference.IPreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val mPreferenceDataSource: IPreferenceDataSource) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val preference = mPreferenceDataSource.get()
        if (preference.token != null)
            requestBuilder.addHeader(
                DataConstant.Http.Param.AUTHORIZATION,
                "Bearer ${preference.token}"
            )

        val response = chain.proceed(requestBuilder.build())
        when (response.code) {
            DataConstant.Http.Code.UNAUTHORIZED -> {
                if (preference.token != null) EventBus.getDefault().post(AppEvent.ForceLogout)

            }
        }

        return response
    }

}