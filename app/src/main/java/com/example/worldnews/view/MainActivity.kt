package com.example.worldnews.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.archive.mynews.view.EverythingFragment
import com.archive.mynews.view.SourceFragment
import com.example.worldnews.view.topHeading.TopHeadingFragment
import com.example.worldnews.databinding.ActivityMainBinding
import com.example.worldnews.view.changeCountry.ChangeCountryDialogFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), ChangeCountryDialogFragment.ChangeCountryListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            val adapter = ViewPagerAdapter(this@MainActivity)
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0-> "TopHeading"
                    1-> "Everything"
                    2 -> "Source"
                    else -> null
                }
            }.attach()

            buttonChangeCountry.setOnClickListener {
                ChangeCountryDialogFragment.show(fragmentManager = supportFragmentManager)
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == 0) {
                        binding.buttonChangeCountry.visibility = View.VISIBLE
                    } else {
                        binding.buttonChangeCountry.visibility = View.GONE
                    }
                    // 페이지 이동 시 hide keyboard
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(window.currentFocus?.windowToken, 0)
                }
            })
        }
    }

    inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TopHeadingFragment()
                1 -> EverythingFragment()
                2 -> SourceFragment()
                else -> TopHeadingFragment()
            }
        }
    }

    override fun onClickChange() {
        val viewPager = binding.viewPager
        val fragment = supportFragmentManager.fragments[viewPager.currentItem]
        if (fragment is TopHeadingFragment) {
            fragment.refresh()
        }
    }
}
