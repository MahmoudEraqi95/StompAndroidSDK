package com.eraqi.chatsdk

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eraqi.chatsdk.data.models.LoginResponse
import com.eraqi.chatsdk.di.DaggerAppComponent

import com.eraqi.chatsdk.domain.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(val app: Application): AndroidViewModel(app) {

    private val registerationFlow = MutableSharedFlow<Boolean>()
    fun registerUser(userId: String){

        viewModelScope.launch {
            val  repo = (app.applicationContext as App).appComponent.getLoginRepository()
            val result = repo.registerUser(userId)
            println(result.javaClass)
            if (result is LoginResponse.Result) {
                registerationFlow.emit(result.success)
            }else{
                registerationFlow.emit(false)
            }

        }
    }
    fun getRegisterationFlow(): Flow<Boolean> = registerationFlow
}