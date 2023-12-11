package com.eraqi.chatlib

import com.eraqi.chatlib.interfaces.StompWebSocketListener
import com.eraqi.chatlib.utils.COMMAND_CONNECT
import com.eraqi.chatlib.utils.COMMAND_CONNECTED
import com.eraqi.chatlib.utils.COMMAND_MESSAGE
import com.eraqi.chatlib.utils.COMMAND_RECEIVED
import com.eraqi.chatlib.utils.COMMAND_SEND
import com.eraqi.chatlib.utils.COMMAND_SUBSCRIBE
import com.eraqi.chatlib.utils.COMMAND_SUBSCRIBED
import com.eraqi.chatlib.utils.HEADER_ACCEPT_VERSION
import com.eraqi.chatlib.utils.HEADER_CONTENT_TYPE
import com.eraqi.chatlib.utils.HEADER_DESTINATION
import com.eraqi.chatlib.utils.HEADER_ID
import com.eraqi.chatlib.utils.HEADER_VALUE_TEXT_PLAIN
import com.eraqi.chatlib.utils.TERMINATE_SYMBOL
import com.eraqi.chatlib.utils.TIME_OUT_TIME
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
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

            else -> {}
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
            .readTimeout(TIME_OUT_TIME, TimeUnit.MILLISECONDS)
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
                "$HEADER_ID$randomId\n" +
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
