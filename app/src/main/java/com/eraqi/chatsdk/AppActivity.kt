package com.eraqi.chatsdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eraqi.chatlib.Stomp



class AppActivity:AppCompatActivity() {
    lateinit var connect: LoadingButton
    lateinit var subscribe: LoadingButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        connect = findViewById(R.id.connect)
        subscribe = findViewById(R.id.subscribe)
        listenForConnectionStatus()
        listenForSubscribetionStatus()
        Stomp.initSDK("ws://192.168.1.2:8080/ws/websocket")
        Stomp.connect()



    }

    private fun listenForSubscribetionStatus() {

        Stomp.connect()
    }

    private fun listenForConnectionStatus() {
        TODO("Not yet implemented")
    }


}