package com.raphael.carvalho.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Setup for retrofit
 */
object RetrofitBuilder {
    operator fun invoke(
        apiURL: String = "https://rickandmortyapi.com/api/",
        httpClient: OkHttpClient = okHttpClient
    ) = with(Retrofit.Builder()) {
        baseUrl(apiURL)
        client(httpClient)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }

    private val okHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

    private val logging: HttpLoggingInterceptor
        get() {
            val logging = HttpLoggingInterceptor(
                object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Timber.tag("OkHttp").d(message)
                    }
                }
            ).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // Hide possible sensitive information
            logging.redactHeader("Authorization")
            logging.redactHeader("Cookie")

            return logging
        }
}

inline fun <reified T> Retrofit.new() = create(T::class.java)
