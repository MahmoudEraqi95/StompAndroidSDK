package com.eraqi.chatsdk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eraqi.chatsdk.databinding.ActivityMainBinding
import com.eraqi.chatsdk.databinding.FragmentLoginBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)
        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        lifecycleScope.launch {
            viewModel.getRegisterationFlow().collect {
                if (it){
                    binding.register.setButtonState(LoadingButton.ButtonState.Success)
                    delay(1500)
                    findNavController().navigate(R.id.action_loginFragment_to_allUsersFragment)
                }
            }
        }
        binding.register.setButtonState(LoadingButton.ButtonState.Initial)
        binding.register.setOnClickListener {
            binding.register.setButtonState(LoadingButton.ButtonState.Loading)
            viewModel.registerUser(binding.editTextTextPersonName.toString())
        }

        return binding.root
    }

}