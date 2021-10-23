package com.example.knowing.ui.viewmodel

import android.app.Application
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.viewpager2.widget.ViewPager2
import com.example.knowing.ui.adapter.OnboardingActivityViewPager2Adapter

class OnboardingActivityViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var viewpager2: ViewPager2
    private val mApplication = application


    fun setViewPager2Adapter(viewPager2: ViewPager2, fragmentActivity: FragmentActivity) {
        this.viewpager2 = viewPager2
        this.viewpager2.adapter = OnboardingActivityViewPager2Adapter(fragmentActivity)
    }

    fun setNextFragment(btn: Button) {
        viewpager2.setCurrentItem(getCurrentItem(+1), true)
        btn.text = "시작하기"
    }

    fun getCurrentItem(i: Int): Int {
        return viewpager2.currentItem + i
    }

}