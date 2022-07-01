package com.eraqi.chatlib

import kotlinx.coroutines.flow.Flow
import okhttp3.WebSocket

interface StompWebSocketListener {
    suspend fun connectToSocket(): WebSocket
    suspend fun connect(socket: WebSocket): Flow<Boolean>
    suspend fun subscribe(socket: WebSocket, des: String): Flow<Boolean>
    suspend fun send(socket: WebSocket, des:String, msg:String):Flow<Boolean>
}