package com.eraqi.chatsdk.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.eraqi.chatsdk.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllUsersFragmentViewModel(app: Application): AndroidViewModel(app) {
    private val usersFlow = MutableSharedFlow<List<String>>()
    private val repo = (app as App).appComponent.getUserRepository()
    fun getUsers(phone: String) {
        viewModelScope.launch{
         repo.getUsers(phone).collect{
             usersFlow.emit(it)
         }
        }
    }
    fun getUsersFlow(): Flow<List<String>> = usersFlow

}