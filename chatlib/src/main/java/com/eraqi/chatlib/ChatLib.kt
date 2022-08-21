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
    suspend fun initSDK(socketUrl: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        webSocketListener = StompWebSocketListenerImpl(socketUrl)
        this.dispatcher = dispatcher

    }

    suspend fun connect() {
        if (webSocketListener == null) {
            return
        }
        webSocket = webSocketListener!!.connectToSocket()!!
        webSocketListener!!.connect(webSocket!!)
    }

    fun send(des: String, msg: String, userId: String = "") {

        if (!isInitialized()) {
            return
        }
        if (des.isBlank() || msg.isBlank()) {
            return
        }
        CoroutineScope(dispatcher).launch {
            webSocketListener!!.send(webSocket!!, des, msg, userId)
        }

    }

    suspend fun subscribe(des: String) {
        if (!isInitialized())
            return
        webSocketListener!!.subscribe(webSocket!!, des)
    }

    fun listenToReceivedMessages(): Flow<String>? = webSocketListener?.receivedMessageFlow
    fun listenToConnectFlow(): SharedFlow<Boolean>? = webSocketListener?.connectFlow
    fun listenToSubscribeFlow(): Flow<Boolean>? = webSocketListener?.subscribeFlow
    fun listenToMessageFlow(): Flow<Boolean>? = webSocketListener?.messageFlow
    private fun isInitialized(): Boolean {
        if (webSocketListener == null) {
            println("not initialized")
            return false
        }
        if (webSocket == null) {
            println("not initialized")
            return false
        }
        return true
    }

}