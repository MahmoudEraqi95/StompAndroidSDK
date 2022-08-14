package com.eraqi.chatsdk.data.network

import com.eraqi.chatsdk.data.models.LoginRequest
import com.eraqi.chatsdk.data.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject


interface ApiServices {

    @POST("/register")
    suspend fun registerUser(@Body user: LoginRequest): LoginResponse.Result

}