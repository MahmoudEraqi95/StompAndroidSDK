package com.eraqi.chatsdk

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class LoginViewModel: ViewModel() {
    val registerationFlow = MutableSharedFlow<Boolean>()
    fun registerUser(userId: String){

    }
}