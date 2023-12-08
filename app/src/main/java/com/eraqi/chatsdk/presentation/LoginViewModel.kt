package com.eraqi.chatsdk.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.eraqi.chatsdk.App
import com.eraqi.chatsdk.domain.models.LoginResponse

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(val app: Application): AndroidViewModel(app) {

    private val registerationFlow = MutableSharedFlow<Boolean>()
    fun registerUser(userId: String){

        viewModelScope.launch {
            val  repo = (app.applicationContext as App).appComponent.getLoginRepository()
            val result = repo.registerUser(userId)
            if (result is LoginResponse.Result) {
                registerationFlow.emit(result.success)
            }else{
                registerationFlow.emit(false)
            }

        }
    }
    fun getRegistrationFlow(): Flow<Boolean> = registerationFlow
}