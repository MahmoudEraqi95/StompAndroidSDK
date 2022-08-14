package com.eraqi.chatsdk.domain

import com.eraqi.chatsdk.data.models.LoginRequest
import com.eraqi.chatsdk.data.models.LoginResponse
import com.eraqi.chatsdk.data.network.ApiServices
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiServices: ApiServices): LoginRepository {
    override suspend fun registerUser(userId: String): LoginResponse {
        return apiServices.registerUser(LoginRequest( userId))
    }

}