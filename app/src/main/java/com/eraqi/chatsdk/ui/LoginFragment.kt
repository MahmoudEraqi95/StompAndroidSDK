package com.eraqi.chatsdk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eraqi.chatsdk.LoadingButton
import com.eraqi.chatsdk.presentation.LoginViewModel
import com.eraqi.chatsdk.R
import com.eraqi.chatsdk.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)
        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        lifecycleScope.launch {
            viewModel.getRegistrationFlow().collect {

                if (it) {
                    binding.register.setButtonState(LoadingButton.ButtonState.Success)
                    delay(1500)
                    val bundle = bundleOf("phone" to binding.editTextTextPersonName.text.toString())

                    findNavController().navigate(
                        R.id.action_loginFragment_to_allUsersFragment,
                        bundle
                    )
                }else{
                    Toast.makeText(requireContext(), "Couldn't connect", Toast.LENGTH_LONG).show()
                    binding.register.setButtonState(LoadingButton.ButtonState.Failure)
                }
            }
        }
        binding.register.setButtonState(LoadingButton.ButtonState.Initial)
        binding.register.setOnClickListener {
            binding.register.setButtonState(LoadingButton.ButtonState.Loading)
            viewModel.registerUser(binding.editTextTextPersonName.text.toString())

        }

        return binding.root
    }

}