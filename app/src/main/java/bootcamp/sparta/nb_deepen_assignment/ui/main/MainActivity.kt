package bootcamp.sparta.nb_deepen_assignment.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bootcamp.sparta.nb_deepen_assignment.databinding.MainActivityBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    private val binding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }
    private val mainViewPagerAdapter by lazy {
        MainViewPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView()=with(binding) {
        viewPagerMain.adapter = mainViewPagerAdapter
        TabLayoutMediator(tabLayoutMain, viewPagerMain) { tab, position ->
            tab.setText(mainViewPagerAdapter.getTabName(position))
        }.attach()
    }
}