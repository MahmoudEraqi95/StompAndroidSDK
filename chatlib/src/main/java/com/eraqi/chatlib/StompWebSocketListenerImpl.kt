package com.eraqi.chatlib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import java.util.concurrent.TimeUnit


class StompWebSocketListenerImpl(socketUrl: String) : WebSocketListener() , StompWebSocketListener{
    val terminateSymbol = "\u0000"
    val connectFlow = MutableStateFlow(false)
    val subscribeFlow = MutableStateFlow(false)
    val messageFlow = MutableStateFlow(false)
    val receivedMessageFlow = MutableStateFlow("")
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            when (text.isNotEmpty()) {
               text.startsWith("CONNECTED") -> connectFlow.value = true
               text.startsWith("SUBSCRIBED") -> subscribeFlow.value = true
               text.startsWith("RECEIVED") -> messageFlow.value = true
               text.startsWith("Message") -> receivedMessageFlow.value = text
            }
        }

    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println("MESSAGE: ${bytes.hex()}")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        println("MESSAGE:OnOPen")

        //webSocket.close(1000, "Goodbye, World!")
    }
    override suspend fun connectToSocket(): WebSocket {
        val client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
        val request: Request = Request.Builder()
            .url("ws://192.168.1.2:8080/ws/websocket")
            .build()
        return client.newWebSocket(request, this@StompWebSocketListenerImpl)

    }
    override suspend fun connect(socket: WebSocket): Flow<Boolean> {
        val connect = "CONNECT\n" +
                "accept-version:1.1,1.2\n\n$terminateSymbol"

        socket.send(connect)
        return connectFlow
    }
    override suspend fun subscribe(socket: WebSocket, des: String): Flow<Boolean>{
        val subscribeCommand = "SUBSCRIBE\n" +
                "id:0123\n" +
                "destination:$des\n\n$terminateSymbol"

        socket.send(subscribeCommand)
       return subscribeFlow
    }
    override suspend fun send(socket: WebSocket, des:String, msg:String):Flow<Boolean>{
        val sendCommand = "SEND\n" +
                "destination:$des\n" +
                "content-type:text/plain\n" +
                "\n" +
                "$msg\n$terminateSymbol"
        socket.send(sendCommand)
        return messageFlow

    }


}