package com.example.knowing.ui.view.more_information

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.databinding.ActivityMoreInformation1Binding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.MoreInformationActivityViewModel

class MoreInformationActivity1 : BaseActivity<ActivityMoreInformation1Binding>(ActivityMoreInformation1Binding::inflate){
    private lateinit var moreInformationActivityViewModel : MoreInformationActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뷰모델 장착
        moreInformationActivityViewModel=ViewModelProvider(this).get(MoreInformationActivityViewModel::class.java)


        //체크버튼의 백그라운드를 변경하기 위해 뷰모델의 라이브데이터를 관찰
        moreInformationActivityViewModel.currentCheckState.observe(this, Observer {
            if (it)
                binding.btnCheck.setBackgroundResource(R.drawable.group_checkbox_color)
            else
                binding.btnCheck.setBackgroundResource(R.drawable.group_checkbox)
        })

        //체크버튼 클릭 리스너
        binding.btnCheck.setOnClickListener {
            //버튼의 백그라운드를 변경하기 위해 뷰모델의 라이브데이터를 바꾸는(참이면 거짓으로, 거짓이면 참으로) 메소드 호출
            moreInformationActivityViewModel.changeBtnBackground()
        }

        binding.btnSelectDo.setOnClickListener {
            val bottomSheet = SelectDoDialog(this)
            bottomSheet
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }
    }
}