package com.nativeboys.password.manager.ui.categoryConstructor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentCategoryConstructorBinding

class CategoryConstructorFragment : Fragment(R.layout.fragment_category_constructor) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoryConstructorBinding.bind(view)
        binding.headerContainer.headlineField.setText(R.string.create_category)
    }

}