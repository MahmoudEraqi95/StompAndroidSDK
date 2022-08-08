package com.eraqi.chatsdk.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.14:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

}