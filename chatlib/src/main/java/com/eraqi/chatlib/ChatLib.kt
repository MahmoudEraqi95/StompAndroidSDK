package com.eraqi.chatlib

import com.eraqi.chatlib.interfaces.StompWebSocketListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.WebSocket


object Stomp {
    private var webSocketListener: StompWebSocketListener? = null

    @Volatile
    private var webSocket: WebSocket? = null
    private lateinit var dispatcher: CoroutineDispatcher
    fun initSDK(socketUrl: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        webSocketListener = StompWebSocketListenerImpl(socketUrl)
        this.dispatcher = dispatcher

    }

    fun connect() {
        if (webSocketListener == null) {
            return
        }

        CoroutineScope(dispatcher).launch {
            webSocket = webSocketListener!!.connectToSocket()!!
            webSocketListener!!.connect(webSocket!!)
            /*webSocketListener!!.connectFlow.collect {
                if (it){
                    println("Connected Successfuly")
                }else{
                    println("connection error")
                }
            }*/

        }
    }

    fun send(des: String, msg: String) {

        if (!isInitialized()) {
            return
        }

        if (des.isBlank() || msg.isBlank()) {
            return
        }
        CoroutineScope(dispatcher).launch {
            webSocketListener!!.send(webSocket!!, des, msg)
        }

    }

    fun subscribe(des: String) {
        if (!isInitialized())
            return
        CoroutineScope(dispatcher).launch {
            webSocketListener!!.subscribe(webSocket!!, des)
        }


    }

    fun listenToReceivedMessages(): Flow<String>? = webSocketListener?.receivedMessageFlow
    fun listenToConnectFlow(): SharedFlow<Boolean>? = webSocketListener?.connectFlow
    fun listenToSubscribeFlow(): Flow<Boolean>? = webSocketListener?.subscribeFlow
    fun listenToMessageFlow(): Flow<Boolean>? = webSocketListener?.messageFlow
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