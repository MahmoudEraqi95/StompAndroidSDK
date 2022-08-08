package com.eraqi.chatsdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eraqi.chatsdk.databinding.ActivityLoginBinding

class LoginActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}