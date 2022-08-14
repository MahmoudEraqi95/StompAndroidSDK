package com.eraqi.chatsdk.data.models

import java.lang.Exception

sealed class LoginResponse {
    data class Result(val success: Boolean): LoginResponse()
    data class Failure(val exception: Exception): LoginResponse()
}