package com.eraqi.chatlib.utils

import android.util.Log

class Logger {
    private val TAG = "stompkt"
    fun logInfo(message: String){
        Log.i(TAG, message)
    }
    fun logError(throwable: Throwable){
        Log.e(TAG, throwable.message, throwable)
    }
}