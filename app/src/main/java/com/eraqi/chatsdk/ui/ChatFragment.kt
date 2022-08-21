package com.eraqi.chatsdk.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.eraqi.chatlib.Stomp
import com.eraqi.chatsdk.LoadingButton
import com.eraqi.chatsdk.R
import com.eraqi.chatsdk.databinding.FragmentChatBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatFragment: BaseFragment() {

    lateinit var send: Button
    lateinit var remoteMessage: TextView
    lateinit var myMessage: TextView
    lateinit var message: EditText
    lateinit var binding:FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        send = binding.btnSend
        remoteMessage = binding.tvRemoteMessage
        myMessage = binding.tvMyMessage
        message = binding.etMessage
        registerListeners()
        send.setOnClickListener {
            val msg = message.text.toString()
            arguments?.getString("my_phone")?.let { it1 -> Stomp.send("/app/chat", msg, it1 )}
            myMessage.text = myMessage.text.toString()+"\n$msg"
        }

        return binding.root
    }
    private  fun registerListeners() {
       CoroutineScope(Dispatchers.IO).launch {
            Stomp.listenToReceivedMessages()?.collect{
                withContext(Dispatchers.Main) {
                    remoteMessage.text = remoteMessage.text.toString()+"\n$it"
                }
            }
        }
    }
}