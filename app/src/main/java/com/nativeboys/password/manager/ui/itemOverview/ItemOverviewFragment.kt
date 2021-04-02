package com.nativeboys.password.manager.ui.itemOverview

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.databinding.FragmentItemOverviewBinding
import com.nativeboys.password.manager.ui.adapters.fields.FieldsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import com.zeustech.zeuskit.ui.views.BottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemOverviewFragment : Fragment(R.layout.fragment_item_overview), AdapterClickListener<FieldContentDto>, View.OnClickListener {

    private val viewModel: ItemOverviewViewModel by viewModels()

    private lateinit var navController: NavController
    private var binding: FragmentItemOverviewBinding? = null

    private val fieldsAdapter = FieldsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentItemOverviewBinding.bind(view)
        binding?.apply {
            headerContainer.headlineField.setText(R.string.item_overview)
            headerContainer.trailignBtn.visibility = View.INVISIBLE
            fieldsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            fieldsRecyclerView.adapter = fieldsAdapter
            headerContainer.leadingBtn.setOnClickListener(this@ItemOverviewFragment)
            editBtn.setOnClickListener(this@ItemOverviewFragment)
            deleteBtn.setOnClickListener(this@ItemOverviewFragment)
        }
        fieldsAdapter.adapterClickListener = this

        viewModel.itemFieldsContent.observe(viewLifecycleOwner) { itemFieldsContent ->
            val data = itemFieldsContent ?: return@observe
            binding?.apply {
                /*Glide.with(requireContext())
                    .load(item?.item?.thumbnailId)
                    .transform(CenterCrop())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.thumbnailHolder)*/
                nameField.text = data.item.name
                descriptionField.text = data.item.description
                notesField.setText(data.item.notes)
                tagsField.setText(data.item.tags)
                fieldsAdapter.dataSet = data.fieldsContent
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(view: View, model: FieldContentDto, position: Int) {
        if (view.id == R.id.copy_btn) {
            val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)
            val clip = ClipData.newPlainText("password:manager:field", model.textContent)
            clipboard?.setPrimaryClip(clip)
            BottomBar(requireView() as ViewGroup, R.layout.copy_bottom_cell, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
            R.id.edit_btn -> {
                navController.navigate(R.id.action_itemOverview_to_itemConstructor)
            }
            R.id.delete_btn -> {
                // TODO: implement
            }
        }
    }

}