package com.raphael.carvalho.api.test

import com.raphael.carvalho.api.test.Resource.read
import com.raphael.carvalho.core.test.shared.TestLifecycleConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

object ApiRequestMockServer : TestLifecycleConfiguration {
    lateinit var server: MockWebServer

    val baseUrl get() = server.url("/").toString()
    val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    override fun beforeClass() {
        server = MockWebServer()
    }

    override fun afterClass() {
        server.shutdown()
    }

    /**
     * Add request to stack with [responseCode] and the response from the [fileLocation]
     */
    fun addRequest(responseCode: Int, fileLocation: String) {
        server.enqueue(
            MockResponse().apply {
                setResponseCode(responseCode)
                setBody(read(fileLocation))
            }
        )
    }
}
