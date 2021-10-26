package com.example.knowing.ui.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.knowing.R
import com.example.knowing.databinding.ActivityMainBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.view.main.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private var fmHome : HomeFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fmHome = HomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.frame_layout,fmHome!!).commitAllowingStateLoss()
    }
}