package com.eraqi.chatsdk.data.network

import com.eraqi.chatsdk.data.models.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NetworkManagerImpl {

    suspend fun registerRequest(userId:String) : Flow<LoginResponse> {
        val request = RetrofitBuilder.getRetrofit().create(NetworkRequests::class.java)
        val result = request.registerUser(userId)
        return flowOf(result)
    }
}