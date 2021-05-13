package com.nativeboys.password.manager.ui.content.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_home_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.navBottom,
            navHostFragment.navController
        )
    }

}