package com.bytezaptech.jawlineexercise_faceyoga.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(val list: List<Fragment>, fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(pos: Int): Fragment {
        return list[pos]
    }
}