package com.eraqi.chatsdk

import android.app.Application
import com.eraqi.chatsdk.di.AppComponent
import com.eraqi.chatsdk.di.DaggerAppComponent

class App:Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

}
