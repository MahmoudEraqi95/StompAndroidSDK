package com.eraqi.chatsdk.data.repo

import com.eraqi.chatsdk.data.network.ApiServices
import com.eraqi.chatsdk.domain.repo.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(val apiServices: ApiServices): UsersRepository {
    override suspend fun getUsers(phone: String): Flow<List<String>> {
        val result = apiServices.getUsers(phone)
        println(result)
        return flowOf(result)
    }

}
