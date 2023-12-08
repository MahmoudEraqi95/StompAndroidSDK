package com.eraqi.chatsdk.di

import com.eraqi.chatsdk.data.network.ApiServices
import com.eraqi.chatsdk.domain.repo.LoginRepository
import com.eraqi.chatsdk.domain.repo.UsersRepository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun getApiServices(): ApiServices
    fun getLoginRepository(): LoginRepository
    fun getUserRepository(): UsersRepository
}
