package com.eraqi.chatsdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eraqi.chatlib.Stomp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AppActivity : AppCompatActivity() {
    lateinit var connect: LoadingButton
    lateinit var subscribe: LoadingButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        connect = findViewById(R.id.connect)
        subscribe = findViewById(R.id.subscribe)


        Stomp.initSDK("ws://192.168.1.3:8080/ws/websocket")
        Stomp.connect()
        CoroutineScope(Dispatchers.IO).launch {
            listenToReceivedMessages()
        }

        Stomp.subscribe("/topic/greetings")
        Stomp.send("/app/hello", "hello")


    }

    private suspend fun listenToReceivedMessages() {
        Stomp.listenToReceivedMessages()?.collect {
            println(it)
        }
    }


}