package com.eraqi.chatsdk.data.network

import com.eraqi.chatsdk.data.models.LoginResponse
import kotlinx.coroutines.flow.Flow

interface NetworkManager {
    suspend fun registerRequest(userId:String) : Flow<LoginResponse>
}