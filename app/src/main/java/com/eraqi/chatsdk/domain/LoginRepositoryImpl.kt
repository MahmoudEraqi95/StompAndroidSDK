package com.eraqi.chatsdk.domain

import com.eraqi.chatsdk.data.models.LoginResponse
import com.eraqi.chatsdk.data.network.NetworkManager
import kotlinx.coroutines.flow.first

class LoginRepositoryImpl(private val networkManager: NetworkManager): LoginRepository {
    override suspend fun registerUser(userId: String): LoginResponse {
        return networkManager.registerRequest(userId).first()
    }

}