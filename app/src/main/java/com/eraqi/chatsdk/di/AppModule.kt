package com.eraqi.chatsdk.di

import com.eraqi.chatsdk.data.network.ApiServices
import com.eraqi.chatsdk.domain.repo.LoginRepository
import com.eraqi.chatsdk.data.repo.LoginRepositoryImpl
import com.eraqi.chatsdk.domain.repo.UsersRepository
import com.eraqi.chatsdk.data.repo.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {


    @Provides
    @Singleton
    fun provideApiService(): ApiServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.17.143.141:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiServices::class.java)

    }

    @Provides
    @Singleton
    fun provideLoginRepository(apiServices: ApiServices): LoginRepository {
        return LoginRepositoryImpl(apiServices)
    }
    @Provides
    @Singleton
    fun provideUserRepository(apiServices: ApiServices): UsersRepository {
        return UsersRepositoryImpl(apiServices)
    }
}