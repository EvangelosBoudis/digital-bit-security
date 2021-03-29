package com.nativeboys.password.manager.ui.categoryConstructor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.other.MockData
import com.nativeboys.password.manager.databinding.FragmentCategoryConstructorBinding
import com.nativeboys.password.manager.ui.adapters.newFields.NewFieldsAdapter

class CategoryConstructorFragment :
    Fragment(R.layout.fragment_category_constructor),
    View.OnClickListener {

    private lateinit var fieldsAdapter: NewFieldsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoryConstructorBinding.bind(view)
        fieldsAdapter = NewFieldsAdapter()
        binding.headerContainer.headlineField.setText(R.string.create_category)
        binding.fieldsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.fieldsRecyclerView.adapter = fieldsAdapter
        binding.headerContainer.leadingBtn.setOnClickListener(this)
        binding.headerContainer.trailignBtn.setOnClickListener(this)
        applyMockData()
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

    private fun applyMockData() {
        fieldsAdapter.dataSet = MockData.fields
    }

}