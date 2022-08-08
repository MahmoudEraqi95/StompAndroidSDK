package com.eraqi.chatsdk

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eraqi.chatlib.Stomp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AppActivity : AppCompatActivity() {
    lateinit var connect: LoadingButton
    lateinit var subscribe: LoadingButton
    lateinit var send: Button
    lateinit var remoteMessage: TextView
    lateinit var myMessage: TextView
    val dispatcher = Dispatchers.IO
    lateinit var message: EditText
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        connect = findViewById(R.id.connect)
        connect.setButtonState(LoadingButton.ButtonState.Initial)
        subscribe = findViewById(R.id.subscribe)
        subscribe.setButtonState(LoadingButton.ButtonState.Initial)
        send = findViewById(R.id.btn_send)
        remoteMessage = findViewById(R.id.tv_remote_message)
        myMessage = findViewById(R.id.tv_my_message)
        message = findViewById(R.id.et_message)

        connect.setOnClickListener {
            connect.setButtonState(LoadingButton.ButtonState.Loading)
            Stomp.initSDK("ws://192.168.1.14:8080/ws/websocket", dispatcher)
            Stomp.connect()
            registerListeners()
        }
        send.setOnClickListener {
            val msg = message.text.toString()
            Stomp.send("/app/hello", msg)
            myMessage.text = myMessage.text.toString()+"\n$msg"
        }
        subscribe.setOnClickListener {
            subscribe.setButtonState(LoadingButton.ButtonState.Loading)
            Stomp.subscribe("/topic/chat/123")
        }

    }

    private  fun registerListeners() {
        CoroutineScope(Dispatchers.IO).launch {
            Stomp.listenToReceivedMessages()?.collect{
                withContext(Dispatchers.Main) {

                    remoteMessage.text = remoteMessage.text.toString()+"\n$it"
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {

        Stomp.listenToConnectFlow()?.collect {

            if (it) {
                connect.setButtonState(LoadingButton.ButtonState.Success)
            } else {
                connect.setButtonState(LoadingButton.ButtonState.Failure)

            }
        }
        }

    }




}