package com.nativeboys.password.manager.ui.content.home.searchEngine

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.databinding.FragmentSearchEngineBinding
import com.nativeboys.password.manager.presentation.SearchEngineViewModel
import com.nativeboys.password.manager.ui.adapters.searchItems.SearchItemsAdapter
import com.nativeboys.password.manager.ui.content.home.HomeFragmentDirections
import com.nativeboys.password.manager.util.parentNavController
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import com.zeustech.zeuskit.ui.other.KeyboardManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchEngineFragment : Fragment(
    R.layout.fragment_search_engine
), View.OnClickListener, AdapterClickListener<ItemDto> {

    private val viewModel: SearchEngineViewModel by viewModels()

    private val searchItemsAdapter = SearchItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchEngineBinding.bind(view)
        binding.apply {
            val portrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            // Same result using itemsRecyclerView.layoutManager = StaggeredGridLayoutManager(if (portrait) 4 else 7, StaggeredGridLayoutManager.VERTICAL)
            itemsRecyclerView.layoutManager = GridLayoutManager(view.context, if (portrait) 4 else 7)
            itemsRecyclerView.adapter = searchItemsAdapter
            searchField.doAfterTextChanged { editable ->
                editable?.let { viewModel.updateItemSearchKey(it.toString()) }
            }
            searchField.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) KeyboardManager().hideKeyboard(v)
                return@setOnEditorActionListener true
            }
        }
        searchItemsAdapter.adapterClickListener = this
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

    override fun onClick(view: View, model: ItemDto, position: Int) {
        parentNavController()?.apply {
            navigate(HomeFragmentDirections.actionHomeToItemOverview(model.itemId))
        }
    }

}