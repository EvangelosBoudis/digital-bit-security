package com.nativeboys.password.manager.ui.searchEngine

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentSearchEngineBinding
import com.nativeboys.password.manager.ui.adapters.searchItems.SearchItemsAdapter
import com.zeustech.zeuskit.ui.other.KeyboardManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchEngineFragment : Fragment(R.layout.fragment_search_engine), View.OnClickListener {

    private val viewModel: SearchEngineViewModel by viewModels()

    private val searchItemsAdapter = SearchItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchEngineBinding.bind(view)
        binding.apply {
            headerContainer.headlineField.setText(R.string.advanced_search)
            headerContainer.trailignBtn.visibility = View.INVISIBLE
            val portrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            // Same result using GridLayoutManager
            itemsRecyclerView.layoutManager = StaggeredGridLayoutManager(if (portrait) 4 else 7, StaggeredGridLayoutManager.VERTICAL)
            itemsRecyclerView.adapter = searchItemsAdapter
            headerContainer.leadingBtn.setOnClickListener(this@SearchEngineFragment)
            searchField.requestFocus()
            searchField.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) KeyboardManager().showKeyboard(v)
            }
            searchField.doAfterTextChanged { editable ->
                editable?.let { viewModel.updateItemSearchKey(it.toString()) }
            }
        }
        viewModel.items.observe(viewLifecycleOwner) {
            searchItemsAdapter.submitList(it)
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
        }
    }

}