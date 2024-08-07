package com.anna.usecase_coroutine_and_test.mock.api

import com.anna.usecase_coroutine_and_test.mock.models.MockResponse
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import kotlin.random.Random

class MockNetworkInterceptor : Interceptor {

    private val mockResponses = mutableListOf<MockResponse>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val mockResponse = findMockResponseInList(request)
            ?: throw RuntimeException("No mock response found for url ${request.url}. Please define a mock response in your MockApi!")

        removeResponseIfItShouldNotBePersisted(mockResponse)
        simulateNetworkDelay(mockResponse)

        return if (mockResponse.status < 400) {
            if (mockResponse.errorFrequencyInPercent == 0) {
                createSuccessResponse(mockResponse, request)
            } else {
                maybeReturnErrorResponse(mockResponse, request)
            }
        } else {
            createErrorResponse(request, mockResponse.body())
        }
    }

    fun mock(
        path: String,
        body: () -> String,
        status: Int,
        delayInMs: Long = 250,
        persist: Boolean = true,
        errorFrequencyInPercent: Int = 0
    ) = apply {
        val mockResponse =
            MockResponse(
                path,
                body,
                status,
                delayInMs,
                persist,
                errorFrequencyInPercent
            )
        mockResponses.add(mockResponse)
    }

    private fun simulateNetworkDelay(mockResponse: MockResponse) {
        Thread.sleep(mockResponse.delayInMs)
    }

    private fun findMockResponseInList(request: Request): MockResponse? {
        return mockResponses.find { mockResponse ->
            mockResponse.path.contains(request.url.encodedPath)
        }
    }

    private fun removeResponseIfItShouldNotBePersisted(mockResponse: MockResponse) {
        if (!mockResponse.persist) {
            mockResponses.remove(mockResponse)
        }
    }

    private fun maybeReturnErrorResponse(mockResponse: MockResponse, request: Request) =
        when (Random.nextInt(0, 101)) {
            in 0..mockResponse.errorFrequencyInPercent -> createErrorResponse(request)
            else -> createSuccessResponse(mockResponse, request)
        }

    private fun createSuccessResponse(mockResponse: MockResponse, request: Request): Response {
        return Response.Builder()
            .code(mockResponse.status)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    mockResponse.body.invoke()
                )
            )
            .build()
    }

    private fun createErrorResponse(request: Request, errorBody: String = "Error"): Response {
        return Response.Builder()
            .code(500)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("Internal Server Error: $errorBody")
            .body(
                ResponseBody.create(
                    "text/plain".toMediaType(),
                    errorBody
                )
            )
            .build()
    }


}

