package com.eraqi.chatlib.interfaces

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.WebSocket

interface StompWebSocketListener {

    val connectFlow: MutableSharedFlow<Boolean>
    val subscribeFlow: MutableSharedFlow<Boolean>
    val messageFlow :MutableSharedFlow<Boolean>
    val receivedMessageFlow :MutableSharedFlow<String>
    fun connectToSocket(): WebSocket?
    suspend fun connect(socket: WebSocket)
    suspend fun subscribe(socket: WebSocket, des: String)
    suspend fun send(socket: WebSocket, des: String, msg: String): Flow<Boolean>
}