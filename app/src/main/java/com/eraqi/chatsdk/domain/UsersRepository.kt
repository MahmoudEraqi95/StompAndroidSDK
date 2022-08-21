package com.eraqi.chatsdk.domain

import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getUsers(phone:String): Flow<List<String>>
}