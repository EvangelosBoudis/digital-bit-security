package com.nativeboys.password.manager.ui.home.items

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.preferences.SortOrder
import com.nativeboys.password.manager.databinding.FragmentSettingsBottomBinding
import com.nativeboys.password.manager.presentation.SettingsBottomViewModel
import com.nativeboys.password.manager.util.checkedRadioButtonIndex
import com.zeustech.zeuskit.ui.fragments.BottomFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsBottomFragment : BottomFragment(R.layout.fragment_settings_bottom, FRAGMENT_WRAP_CONTENT) {

    private val viewModel: SettingsBottomViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBottomBinding.bind(view)
        binding.apply {
            applyBtn.setOnClickListener {
                val index = filterRadioGroup.checkedRadioButtonIndex()
                viewModel.updateSortOrderAndFavoritesVisibility(
                    if (index == 0) SortOrder.BY_NAME else SortOrder.BY_DATE,
                    favoritesCheckbox.isChecked
                ).observe(viewLifecycleOwner) {
                    dismiss()
                }
            }
            dismissBtn.setOnClickListener { dismiss() }
        }
        viewModel.itemSettings.observe(viewLifecycleOwner) {
            binding.favoritesCheckbox.isChecked = it.hideNonFavorites
            // binding.favoritesCheckbox.jumpDrawablesToCurrentState()

            binding.filterRadioGroup.check(if (it.sortOrder == SortOrder.BY_NAME) R.id.name_radio_btn else R.id.date_radio_btn)
            // binding.filterRadioGroup.jumpDrawablesToCurrentState()
        }
    }

}