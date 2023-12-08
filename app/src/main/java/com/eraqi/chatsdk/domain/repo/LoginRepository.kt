package com.eraqi.chatsdk.domain.repo

import com.eraqi.chatsdk.domain.models.LoginResponse

interface LoginRepository {

    suspend fun registerUser(userId: String): LoginResponse

}
