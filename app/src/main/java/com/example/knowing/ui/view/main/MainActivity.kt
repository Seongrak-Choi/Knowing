package com.example.knowing.ui.view.main

import android.os.Bundle
import com.example.knowing.R
import com.example.knowing.data.model.network.response.MainWelfareResponse
import com.example.knowing.databinding.ActivityMainBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.view.main.alert.AlertFragment
import com.example.knowing.ui.view.main.bookmark.BookmarkFragment
import com.example.knowing.ui.view.main.home.HomeFragment
import com.example.knowing.ui.view.main.mypage.MyPageFragment


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val fmHome  by lazy { HomeFragment() }
    private val fmAlert by lazy { AlertFragment() }
    private val fmBookmark by lazy { BookmarkFragment() }
    private val fmMyPage by lazy { MyPageFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //로딩화면에서 받아온 복지 데이터
        val welfareInfo = intent.getSerializableExtra("welfareInfo") as MainWelfareResponse

        //Fragment에서 사용할 수 있도록 bundle에 저장
        val bundle = Bundle()
        bundle.putSerializable("welfareInfo",welfareInfo)

        fmHome.arguments=bundle//복지 정보 전달
        fmAlert.arguments=bundle
        fmBookmark.arguments=bundle
        fmMyPage.arguments=bundle


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
}