package com.b21cap0398.acnescan.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.b21cap0398.acnescan.R
import com.b21cap0398.acnescan.databinding.ActivityResultBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ResultActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    private lateinit var binding: ActivityResultBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_3
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultPageAdapter = ResultPageAdapter(this)
        binding.viewPager.adapter = resultPageAdapter

        TabLayoutMediator(binding.tlResultCategory, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.cvBackButton.setOnClickListener {
            onBackPressed()
        }

        supportActionBar?.elevation = 0f
    }
}