package com.nativeboys.password.manager.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.MockData
import com.nativeboys.password.manager.databinding.FragmentHomeBinding
import com.nativeboys.password.manager.ui.adapters.passwords.PasswordsAdapter
import com.nativeboys.password.manager.ui.adapters.tags.TagsAdapter

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private lateinit var navController: NavController
    private val tagsAdapter = TagsAdapter()
    private val passwordsAdapter = PasswordsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.tagsRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        binding.tagsRecyclerView.adapter = tagsAdapter
        binding.passwordsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.passwordsRecyclerView.adapter = passwordsAdapter

        binding.plusBtn.setOnClickListener(this)
        binding.settingsBtn.setOnClickListener(this)

        applyMockData()
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.plus_btn -> {
                navController.navigate(R.id.action_home_to_passwordDetails)
            }
            R.id.settings_btn -> {
                // TODO: implement
            }
        }
    }

    private fun applyMockData() {
        tagsAdapter.dataSet = MockData.adapterTags
        passwordsAdapter.dataSet = MockData.passwords
    }

}