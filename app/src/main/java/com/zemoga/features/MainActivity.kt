package com.zemoga.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zemoga.R
import com.zemoga.databinding.ActivityMainBinding
import com.zemoga.features.favorites.ui.FavoritesFragment
import com.zemoga.features.posts.ui.PostFragment
import com.zemoga.features.posts.ui.PostsParentFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupTabLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.post_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.refresh) {
            val fragment = supportFragmentManager.fragments
                .first { it.isVisible && it.isAdded } as? PostsParentFragment
            fragment?.refresh()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupTabLayout() {
        ViewPagerAdapter(this, binding.tabLayout, binding.viewPager)
            .apply {
                addFragment(PostFragment.newInstance(), getString(R.string.all_tab))
                addFragment(FavoritesFragment.newInstance(), getString(R.string.favorites_tab))
            }.attach()
    }
}

internal class ViewPagerAdapter(
    fm: FragmentActivity,
    private val tabLayout: TabLayout,
    private val viewPager: ViewPager2
) : FragmentStateAdapter(fm) {

    private val fragmentList: ArrayList<Fragment> = arrayListOf()
    private val fragmentTitleList: ArrayList<String> = arrayListOf()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment, title: String? = "Title") {
        fragmentList.add(fragment)
        fragmentTitleList.add(title.toString())
    }

    private fun setTabLayoutMediator() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = this.fragmentTitleList[position]
        }.attach()
    }

    fun attach() {
        viewPager.adapter = this
        setTabLayoutMediator()
    }
}