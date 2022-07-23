package com.eraqi.chatlib

import com.eraqi.chatlib.interfaces.StompWebSocketListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import java.util.concurrent.TimeUnit


class StompWebSocketListenerImpl(private val socketUrl: String) : WebSocketListener(), StompWebSocketListener {

    private val connectFlow = MutableStateFlow(false)
    private val subscribeFlow = MutableStateFlow(false)
    private val messageFlow = MutableStateFlow(false)
    override val receivedMessageFlow = MutableStateFlow("")

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println(text)
        CoroutineScope(Dispatchers.IO).launch {
            when (text.isNotEmpty()) {
                text.startsWith(COMMAND_CONNECTED) -> connectFlow.value = true
                text.startsWith(COMMAND_SUBSCRIBED) -> subscribeFlow.value = true
                text.startsWith(COMMAND_RECEIVED) -> messageFlow.value = true
                text.startsWith(COMMAND_MESSAGE) -> receivedMessageFlow.value = text
            }
        }

    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println(bytes.toString())
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        println(response.message)

    }

    override  fun connectToSocket(): WebSocket {
        val client = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
        val request: Request = Request.Builder()
            .url(socketUrl)
            .build()
        return client.newWebSocket(request, this@StompWebSocketListenerImpl)

    }

    override suspend fun connect(socket: WebSocket): Flow<Boolean> {
        val connect = "$COMMAND_CONNECT\n" +
                "${HEADER_ACCEPT_VERSION}1.1,1.2\n\n$TERMINATE_SYMBOL"


        socket.send(connect)
        return connectFlow
    }

    override suspend fun subscribe(socket: WebSocket, des: String): Flow<Boolean> {
        val subscribeCommand = "$COMMAND_SUBSCRIBE\n" +
                "${HEADER_ID}0123\n" +
                "$HEADER_DESTINATION$des\n\n" +
                TERMINATE_SYMBOL

        socket.send(subscribeCommand)
        return subscribeFlow
    }

    override suspend fun send(socket: WebSocket, des: String, msg: String): Flow<Boolean> {
        val sendCommand = "$COMMAND_SEND\n" +
                "$HEADER_DESTINATION$des\n" +
                "$HEADER_CONTENT_TYPE$HEADER_VALUE_TEXT_PLAIN\n\n" +
                "$msg\n" +
                TERMINATE_SYMBOL
        println(sendCommand)
        socket.send(sendCommand)
        return messageFlow

    }


}