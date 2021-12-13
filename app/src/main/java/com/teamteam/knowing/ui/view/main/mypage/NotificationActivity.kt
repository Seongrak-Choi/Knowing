package com.teamteam.knowing.ui.view.main.mypage

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamteam.knowing.databinding.ActivityNotificationBinding
import com.teamteam.knowing.ui.adapter.MainBookmarkRCAdapter
import com.teamteam.knowing.ui.adapter.NotificationAdapter
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.viewmodel.NotificationActivityViewModel

class NotificationActivity:BaseActivity<ActivityNotificationBinding>(ActivityNotificationBinding::inflate) {
    private lateinit var notificationActivityViewModel : NotificationActivityViewModel

    //공지 뿌려주는 리사이클러뷰 어댑터 선언
    private lateinit var adapter : NotificationAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //뷰모델 장착
        notificationActivityViewModel = ViewModelProvider(this).get(NotificationActivityViewModel::class.java)

        //복지 리사이클러뷰 어댑터에 들어가는 리스트 책임질 라이브데이터 관찰
        notificationActivityViewModel.currentRcList.observe(this, Observer {
            binding.rcNotification.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)
            adapter = NotificationAdapter(it,this)
            binding.rcNotification.adapter = adapter
            adapter.notifyDataSetChanged()
        })


        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        notificationActivityViewModel.tryGetNotification()
    }
}