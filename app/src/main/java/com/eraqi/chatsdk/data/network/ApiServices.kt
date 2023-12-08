package com.eraqi.chatsdk.data.network

import com.eraqi.chatsdk.domain.models.LoginRequest
import com.eraqi.chatsdk.domain.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject


interface ApiServices {

    @POST("/register")
    suspend fun registerUser(@Body user: LoginRequest): LoginResponse.Result

    @GET("/getUsers/{phone}")
    suspend fun  getUsers(@Path("phone") phone: String):List<String>

}