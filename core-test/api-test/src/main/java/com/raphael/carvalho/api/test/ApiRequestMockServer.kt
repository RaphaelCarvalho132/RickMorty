package com.raphael.carvalho.api.test

import com.raphael.carvalho.api.test.Resource.read
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

object ApiRequestMockServer : BeforeAllCallback, AfterAllCallback {
    lateinit var server: MockWebServer

    val baseUrl get() = server.url("/").toString()
    val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    override fun beforeAll(context: ExtensionContext?) {
        server = MockWebServer()
    }

    override fun afterAll(context: ExtensionContext?) {
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
