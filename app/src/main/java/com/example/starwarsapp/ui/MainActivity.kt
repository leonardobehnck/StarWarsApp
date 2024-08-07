package com.example.starwarsapp.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.starwarsapp.R
import com.example.starwarsapp.ui.adapter.TabsAdapter
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

  lateinit var tabLayout: TabLayout
  lateinit var viewPager: ViewPager2

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    setupView()
    setuptabs()
  }

  override fun onResume() {
    super.onResume()
    Log.d("RESUME", "RESUME")
  }

  override fun onStart() {
    super.onStart()
    Log.d("START", "START")
  }

  override fun onPause() {
    super.onPause()
    Log.d("PAUSE", "PAUSE")
  }

  override fun onStop() {
    super.onStop()
    Log.d("STOP", "STOP")
  }

  fun setuptabs() {
    val tabsAdapter = TabsAdapter(this)
    viewPager.adapter = tabsAdapter

    tabLayout.addOnTabSelectedListener (object : TabLayout.OnTabSelectedListener {
      override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let { it ->
          viewPager.currentItem = it.position
        }
      }

      override fun onTabUnselected(tab: TabLayout.Tab?) {}
      override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        tabLayout.getTabAt(position)?.select()
      }
    })
  }

  fun setupView() {
    tabLayout = findViewById(R.id.tab_layout)
    viewPager = findViewById(R.id.view_pager)
  }
}
