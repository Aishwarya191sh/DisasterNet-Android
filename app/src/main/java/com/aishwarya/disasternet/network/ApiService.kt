package com.aishwarya.disasternet.network

import com.aishwarya.disasternet.model.MessageRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("messages")
    suspend fun getMessages(): List<String>

    @POST("send")
    suspend fun sendMessage(
        @Body request: MessageRequest
    ): Response<Unit>
}