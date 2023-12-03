package com.eraqi.chatsdk.domain

import com.eraqi.chatsdk.data.models.LoginRequest
import com.eraqi.chatsdk.data.models.LoginResponse
import com.eraqi.chatsdk.data.network.ApiServices
import java.lang.Exception
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiServices: ApiServices): LoginRepository {
    override suspend fun registerUser(userId: String): LoginResponse {
        println("register user called1")
        var result: LoginResponse
        try {
           result = apiServices.registerUser(LoginRequest(userId))

        }catch (e: Exception){
            result = LoginResponse.Failure(Exception("Connection Exception"))
        }

        return result
    }

}