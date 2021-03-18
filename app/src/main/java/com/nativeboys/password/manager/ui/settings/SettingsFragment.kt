package com.nativeboys.password.manager.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentSettingsBinding
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)
        val iconNames = MaterialDrawableBuilder.IconValue.values().map { it.name }
    }

}