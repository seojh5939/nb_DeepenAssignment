package bootcamp.sparta.nb_deepen_assignment.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import bootcamp.sparta.nb_deepen_assignment.R
import bootcamp.sparta.nb_deepen_assignment.ui.mypage.MyPageFragment
import bootcamp.sparta.nb_deepen_assignment.ui.search.SearchFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){
    private val list = ArrayList<MainTab>()

    init {
        list.add(
            MainTab(
                SearchFragment.newInstance(),
                R.string.searchTab
            )
        )
        list.add(
            MainTab(
                MyPageFragment.newInstance(),
                R.string.myPageTab
            )
        )
    }

    fun getTabName(position: Int): Int = list[position].tabNameRes
    fun getSearchFragment() : SearchFragment? = list.find { it.fragment is SearchFragment }?.fragment as? SearchFragment
    fun getMyPageFragment() : MyPageFragment? = list.find { it.fragment is MyPageFragment }?.fragment as? MyPageFragment

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position].fragment
    }
}