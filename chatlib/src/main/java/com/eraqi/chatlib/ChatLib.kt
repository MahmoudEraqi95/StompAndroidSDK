package com.eraqi.chatlib

import com.eraqi.chatlib.interfaces.StompWebSocketListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.WebSocket


object Stomp {
    private var webSocketListener: StompWebSocketListener? = null

    @Volatile
    private var webSocket: WebSocket? = null
    fun initSDK(socketUrl: String) {
        webSocketListener = StompWebSocketListenerImpl(socketUrl)

    }

    fun connect() {
        if (webSocketListener == null) {

            return
        }
        webSocket = webSocketListener!!.connectToSocket()!!
    }

    fun send(des: String, msg: String) {

        if (!isInitialized()) {
            return
        }

        if (des.isBlank() || msg.isBlank()) {
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            webSocketListener!!.send(webSocket!!, des, msg)
        }

    }

    fun subscribe(des: String) {
        println(isInitialized())
        if (!isInitialized())
            return
        CoroutineScope(Dispatchers.IO).launch {
            webSocketListener!!.subscribe(webSocket!!, des)
        }


    }

    fun listenToReceivedMessages(): Flow<String>? = webSocketListener?.receivedMessageFlow

    private fun isInitialized(): Boolean {
        if (webSocketListener == null) {

            return false
        }
        if (webSocket == null) {

            return false
        }
        return true
    }

}