package bootcamp.sparta.nb_deepen_assignment.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import bootcamp.sparta.nb_deepen_assignment.R

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

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position].fragment
    }
}