package com.wgllss.sample.datasource.net

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitUtils private constructor(val context: Context) {

    companion object {
        const val base_url = "http://3g.163.com/"

        @Volatile
        private var instance: RetrofitUtils? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: RetrofitUtils(context).also { instance = it }
        }
    }

    private inline val retrofit: Retrofit
        get() {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val timeout = 30000L
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(logging)
//            .addInterceptor(RetrofitClient.BaseUrlInterceptor())
                .callTimeout(timeout, TimeUnit.MILLISECONDS)
                //设置连接超时
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                //设置从主机读信息超时
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                //设置写信息超时
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .cache(Cache(context.cacheDir, 50 * 1024 * 1024)) //10M cache
                .build();
            return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(base_url)
                .build()
        }

    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)!!
    }
}