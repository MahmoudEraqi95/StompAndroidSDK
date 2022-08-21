package com.eraqi.chatsdk.ui

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment: Fragment() {
    fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }
}