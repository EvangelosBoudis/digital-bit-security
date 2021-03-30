package com.nativeboys.password.manager.ui.categoryConstructor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentCategoryConstructorBinding
import com.nativeboys.password.manager.other.setMaterialIcon
import com.nativeboys.password.manager.ui.adapters.newFields.NewFieldsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryConstructorFragment : Fragment(R.layout.fragment_category_constructor), View.OnClickListener {

    private val viewModel: CategoryConstructorViewModel by viewModels()

    private lateinit var fieldsAdapter: NewFieldsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoryConstructorBinding.bind(view)
        fieldsAdapter = NewFieldsAdapter()
        binding.apply {
            headerContainer.headlineField.setText(R.string.create_category)
            fieldsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            fieldsRecyclerView.adapter = fieldsAdapter
            headerContainer.leadingBtn.setOnClickListener(this@CategoryConstructorFragment)
            headerContainer.trailignBtn.setOnClickListener(this@CategoryConstructorFragment)
        }
        viewModel.category.observe(viewLifecycleOwner) { category ->
            binding.apply {
                contentField.setText(category?.name)
                category?.thumbnailCode?.let {
                    thumbnailHolder.setMaterialIcon(it)
                }
            }
        }
        viewModel.fields.observe(viewLifecycleOwner) {
            fieldsAdapter.dataSet = it
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
            R.id.trailign_btn -> {
                // TODO: implement
            }
        }
    }

}