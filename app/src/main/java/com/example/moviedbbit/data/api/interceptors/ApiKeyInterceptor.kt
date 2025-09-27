package com.example.moviedbbit.data.api.interceptors

import com.example.moviedbbit.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()
        
        val requestBuilder = original.newBuilder()
            .url(url)
        
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}