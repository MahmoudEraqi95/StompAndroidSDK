package com.eraqi.chatlib.interfaces

import kotlinx.coroutines.flow.Flow
import okhttp3.WebSocket

interface StompWebSocketListener {
    val receivedMessageFlow: Flow<String>
    fun connectToSocket(): WebSocket?
    suspend fun connect(socket: WebSocket): Flow<Boolean>
    suspend fun subscribe(socket: WebSocket, des: String): Flow<Boolean>
    suspend fun send(socket: WebSocket, des:String, msg:String):Flow<Boolean>
}