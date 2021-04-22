package com.nativeboys.password.manager.ui.itemConstructor.bottomFragment

import androidx.fragment.app.viewModels
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.presentation.ItemConstructorViewModel
import com.zeustech.zeuskit.ui.fragments.BottomFragment

open class FactoryBottomFragment : BottomFragment(R.layout.fragment_factory_bottom, FRAGMENT_WRAP_CONTENT) {

    protected val viewModel: ItemConstructorViewModel by viewModels(ownerProducer = { requireParentFragment() })

}