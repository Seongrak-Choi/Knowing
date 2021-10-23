package com.example.knowing.ui.view.login

import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import com.example.knowing.databinding.ActivityJoinUpSnsBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.JoinUpSNSActivityViewModel


class JoinUpSNSActivity : BaseActivity<ActivityJoinUpSnsBinding>(ActivityJoinUpSnsBinding::inflate){
    private lateinit var joinUpSNSActivityViewModel : JoinUpSNSActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        joinUpSNSActivityViewModel = ViewModelProvider(this).get(JoinUpSNSActivityViewModel::class.java) //뷰모델 장착

        //네이버아이디로 로그인 버튼 클릭 리스너
        binding.btnLoginNaver.setOnClickListener {
            joinUpSNSActivityViewModel.setActivity(this) //activity의 정보를 viewModel로 넘겨준다. 네이버아이디로로그인 할 때 필요함.
            joinUpSNSActivityViewModel.getNaverToken() //네이버 로그인을 진행한다.
        }

        //카카오계정 로그인 버튼 클릭 리스너
        binding.btnLoginKakao.setOnClickListener {
            joinUpSNSActivityViewModel.getKakaoToken()
        }


    }
}