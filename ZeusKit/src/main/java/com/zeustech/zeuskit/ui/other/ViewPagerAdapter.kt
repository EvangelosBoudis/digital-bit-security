package com.zeustech.zeuskit.ui.other

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class VPData(
    val clazz: Class<*>,
    val bundle: Bundle? = null,
    val title: String? = null
)

class ViewPagerAdapter(
    fm: FragmentManager,
    private val data: List<VPData>
) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    companion object {
        fun new(fm: FragmentManager, data: List<Class<*>>)
                = ViewPagerAdapter(fm, data.map { VPData(it) })
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position].title
    }

    override fun getItem(position: Int): Fragment {
        val constructor = data[position].clazz.getConstructor()
        val fragment = constructor.newInstance() as Fragment
        fragment.arguments = data[position].bundle
        return fragment
    }

}