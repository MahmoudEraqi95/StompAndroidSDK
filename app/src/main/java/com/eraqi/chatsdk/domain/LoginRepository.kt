package com.eraqi.chatsdk.domain

import com.eraqi.chatsdk.data.models.LoginResponse

interface LoginRepository {

    suspend fun registerUser(userId: String): LoginResponse

}