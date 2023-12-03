package com.eraqi.chatlib.models

import okhttp3.WebSocket
import java.lang.Exception

sealed class ConnectionResult{
    data class Success(val webSocket: WebSocket): ConnectionResult()
    data class Failure(val exception: Exception): ConnectionResult()
}
