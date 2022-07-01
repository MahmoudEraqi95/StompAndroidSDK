package com.eraqi.chatlib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.WebSocket


object Stomp{
    private lateinit var webSocketListener: StompWebSocketListener
    private lateinit var webSocket:WebSocket
    private val coroutineDispatcher = Dispatchers.IO
    fun initSDK(socketUrl:String){
         webSocketListener = StompWebSocketListenerImpl(socketUrl)
    }
    fun connect(){
        if (webSocketListener==null) {
            println("you need to call initSDK with your destination url first")
            return
        }
        CoroutineScope(coroutineDispatcher).launch {
          webSocket = webSocketListener.connectToSocket()

        }
    }

    fun send(des:String, msg: String){
        if (!isInitialized()) {
            return
        }

        if (des.isBlank() || msg.isBlank()){
            println("make sure you're sending a valid message and destination")
            return
        }
        CoroutineScope(coroutineDispatcher).launch {
            webSocketListener.send(webSocket, des, msg)
        }

    }
    fun subscribe(des: String){

        if (!isInitialized())
            return
        CoroutineScope(coroutineDispatcher).launch {
            webSocketListener.subscribe(webSocket, des)
        }

    }
    private fun isInitialized():Boolean{
        if (webSocketListener==null) {
            println("you need to call initSDK with your destination url first")
            return false
        }
        if (webSocket==null) {
            println("you need to connect before you start sending messages")
            return false
        }
        return true
    }
}