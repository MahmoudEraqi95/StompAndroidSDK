package com.eraqi.chatsdk.di

import android.app.Application
import com.eraqi.chatsdk.data.network.ApiServices
import com.eraqi.chatsdk.domain.LoginRepository
import com.eraqi.chatsdk.domain.UsersRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf( AppModule::class))
interface AppComponent {

    fun getApiServices(): ApiServices
    fun getLoginRepository(): LoginRepository
    fun getUserRepository(): UsersRepository
}