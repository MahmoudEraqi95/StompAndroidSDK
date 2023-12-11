package com.eraqi.chatsdk.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eraqi.chatlib.Stomp
import com.eraqi.chatsdk.R
import com.eraqi.chatsdk.databinding.FragmentAllUsersBinding
import com.eraqi.chatsdk.presentation.AllUsersFragmentViewModel
import com.eraqi.chatsdk.ui.adapters.AllUsersAdapter
import com.eraqi.chatsdk.utils.ALL_USERS_FRAGMENT_TAG
import com.eraqi.chatsdk.utils.SOCKET_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.math.log

class AllUsersFragment : Fragment() {
    private lateinit var binding: FragmentAllUsersBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllUsersBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            try {
                Stomp.initSDK(SOCKET_URL)
                Stomp.connect()
                Stomp.subscribe("/topic/chat/${arguments?.getString("phone")}")
            } catch (e: Exception) {
               Log.e(ALL_USERS_FRAGMENT_TAG, e.message.toString())
            }
        }
        recyclerView = binding.rvUsers
        val userViewModel = ViewModelProvider(this)[AllUsersFragmentViewModel::class.java]
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = AllUsersAdapter(requireContext(), arrayListOf(), itemClick)
        recyclerView.adapter = adapter

        lifecycleScope.launch {

            userViewModel.getUsersFlow().collect {
                withContext(Dispatchers.Main) {
                    adapter.addItems(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        userViewModel.getUsers(arguments?.getString("phone")!!)
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.action_allUsersFragment_to_loginFragment)
        }
    }


    private val itemClick: (String) -> Unit = { phone: String ->
        run {
            println(phone)
            findNavController().navigate(
                R.id.action_allUsersFragment_to_chatFragment,
                bundleOf("my_phone" to phone)
            )
        }
    }
}
