package com.eraqi.chatsdk.data.network

import com.eraqi.chatsdk.data.models.LoginResponse
import retrofit2.http.POST

interface NetworkRequests {

    @POST("register")
    fun registerUser(userId: String): LoginResponse;

}