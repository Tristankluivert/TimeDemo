package com.hybrid.timedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hybrid.timedemo.adapter.ViewPager
import com.hybrid.timedemo.fragments.AfternoonFrag
import com.hybrid.timedemo.fragments.EveningFrag
import com.hybrid.timedemo.fragments.MorningFrag
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)

        val adapter = ViewPager(supportFragmentManager)
        adapter.addFragment(MorningFrag(), "Morning")
        adapter.addFragment(AfternoonFrag(), "Afternoon")
        adapter.addFragment(EveningFrag(), "Evening")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }
}
