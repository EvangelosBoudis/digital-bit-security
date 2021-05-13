package com.nativeboys.password.manager.ui.content.categoryConstructor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.radiobutton.MaterialRadioButton
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentCategoryTypeChooserBottomBinding
import com.nativeboys.password.manager.presentation.CategoryConstructorViewModel
import com.nativeboys.password.manager.util.checkedRadioButtonIndex
import com.zeustech.zeuskit.ui.fragments.BottomFragment

class CategoryTypeChooserBottomFragment : BottomFragment(
    R.layout.fragment_category_type_chooser_bottom,
    70f,
    true
) {

    private val viewModel: CategoryConstructorViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoryTypeChooserBottomBinding.bind(view)
        binding.apply {
            viewModel.categoriesTypes.forEach {
                val rdBtn = MaterialRadioButton(requireContext())
                rdBtn.text = it.description
                filterRadioGroup.addView(rdBtn)
            }
            dismissBtn.setOnClickListener { dismiss() }
            applyBtn.setOnClickListener {
                binding.filterRadioGroup.checkedRadioButtonIndex()?.let { index ->
                    viewModel.updateFieldType(index)
                    dismiss()
                }
            }
        }
    }

}