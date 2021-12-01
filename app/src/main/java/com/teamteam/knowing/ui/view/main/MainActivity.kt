package com.teamteam.knowing.ui.view.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.teamteam.knowing.R
import com.teamteam.knowing.data.model.network.response.MainWelfareResponse
import com.teamteam.knowing.databinding.ActivityMainBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.view.main.alert.AlertFragment
import com.teamteam.knowing.ui.view.main.bookmark.BookmarkFragment
import com.teamteam.knowing.ui.view.main.home.HomeFragment
import com.teamteam.knowing.ui.view.main.mypage.MyPageFragment


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val fmHome  by lazy { HomeFragment() }
    private val fmAlert by lazy { AlertFragment() }
    private val fmBookmark by lazy { BookmarkFragment() }
    private val fmMyPage by lazy { MyPageFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //statusbar 투명하게 설정(네비게이션bar는 투명해지지 않음)
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT

        //로딩화면에서 받아온 복지 데이터
        val welfareInfo = intent.getSerializableExtra("welfareInfo") as MainWelfareResponse


        //Fragment에서 사용할 수 있도록 bundle에 저장
        val bundle = Bundle()
        bundle.putSerializable("welfareInfo",welfareInfo)

        fmHome.arguments=bundle//복지 정보 전달
        fmAlert.arguments=bundle//복지 정보 전달
        fmBookmark.arguments=bundle//복지 정보 전달
        fmMyPage.arguments=bundle//복지 정보 전달


        //Main에서 처음으로 보여질 fragment 설정
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fmHome!!).commit()

        //바텀네비게이션뷰 클릭 시 동작하는 이벤트 리스너
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.main_home_bottomNavi_home->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fmHome!!).commit()
                }
                R.id.main_home_bottomNavi_alert->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fmAlert!!).commit()
                }
                R.id.main_home_bottomNavi_bookmark->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fmBookmark!!).commit()
                }
                R.id.main_home_bottomNavi_my_page->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fmMyPage!!).commit()
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        moveTaskToBack(true)
        finishAndRemoveTask();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}