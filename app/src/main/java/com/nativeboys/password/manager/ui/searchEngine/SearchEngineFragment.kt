package com.nativeboys.password.manager.ui.searchEngine

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.MockData
import com.nativeboys.password.manager.databinding.FragmentSearchEngineBinding
import com.nativeboys.password.manager.ui.adapters.searchPasswords.SearchPasswordsAdapter

class SearchEngineFragment : Fragment(R.layout.fragment_search_engine), View.OnClickListener {

    private val searchPasswordsAdapter = SearchPasswordsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchEngineBinding.bind(view)
        val portrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        binding.passwordsRecyclerView.layoutManager = StaggeredGridLayoutManager(if (portrait) 4 else 7, StaggeredGridLayoutManager.VERTICAL)
        binding.passwordsRecyclerView.adapter = searchPasswordsAdapter
        binding.dismissBtn.setOnClickListener(this)
        mockData()
    }

    private fun mockData() {
        searchPasswordsAdapter.dataSet = MockData.passwords
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.dismiss_btn -> {
                activity?.onBackPressed()
            }
        }
    }

}