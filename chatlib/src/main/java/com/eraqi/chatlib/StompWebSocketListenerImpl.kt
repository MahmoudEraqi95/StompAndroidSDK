package com.eraqi.chatlib

import com.eraqi.chatlib.interfaces.StompWebSocketListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import java.util.*
import java.util.concurrent.TimeUnit


class StompWebSocketListenerImpl(private val socketUrl: String) : WebSocketListener(),
    StompWebSocketListener {

    override val connectFlow = MutableSharedFlow<Boolean>(
        replay = 1, extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val subscribeFlow = MutableSharedFlow<Boolean>(
        replay = 1, extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val messageFlow = MutableSharedFlow<Boolean>(
        replay = 1, extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val receivedMessageFlow = MutableSharedFlow<String>(
        replay = 1, extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val randomId: String = "user-123";//UUID.randomUUID().toString()

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println(text)

        when (text.isNotEmpty()) {
            text.startsWith(COMMAND_CONNECTED) -> {

                val isEmitted = connectFlow.tryEmit(true)
                println(isEmitted)
            }
            text.startsWith(COMMAND_SUBSCRIBED) -> subscribeFlow.tryEmit(true)
            text.startsWith(COMMAND_RECEIVED) -> messageFlow.tryEmit(true)
            text.startsWith(COMMAND_MESSAGE) -> receivedMessageFlow.tryEmit(text.replaceBefore("" +
                    "\n\n","").trim())
        }


    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println(bytes.toString())
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        println(response.message)

    }

    override fun connectToSocket(): WebSocket {
        val client = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
        val request: Request = Request.Builder()
            .url(socketUrl)
            .build()
        return client.newWebSocket(request, this@StompWebSocketListenerImpl)

    }

    override suspend fun connect(socket: WebSocket) {
        val connect = "$COMMAND_CONNECT\n" +
                "${HEADER_ACCEPT_VERSION}1.1,1.2\n\n$TERMINATE_SYMBOL"

        println(connect)
        socket.send(connect)

    }

    override suspend fun subscribe(socket: WebSocket, des: String) {
        val subscribeCommand = "$COMMAND_SUBSCRIBE\n" +
                "${HEADER_ID}$randomId\n" +
                "$HEADER_DESTINATION$des\n\n" +
                TERMINATE_SYMBOL
        println(subscribeCommand)
        socket.send(subscribeCommand)

    }

    override suspend fun send(socket: WebSocket, des: String, msg: String, userId: String): Flow<Boolean> {
        val sendCommand = "$COMMAND_SEND\n" +
                "$HEADER_DESTINATION$des\n" +
                "$HEADER_ID$userId\n" +
                "$HEADER_CONTENT_TYPE$HEADER_VALUE_TEXT_PLAIN\n\n" +
                "$msg\n" +
                TERMINATE_SYMBOL
        println(sendCommand)
        socket.send(sendCommand)
        return messageFlow

    }


}