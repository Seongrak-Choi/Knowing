package com.example.knowing.ui.view.more_information

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.knowing.R
import com.example.knowing.databinding.ActivityMoreInformation5Binding
import com.example.knowing.ui.base.BaseActivity

class MoreInformationActivity5:BaseActivity<ActivityMoreInformation5Binding>(ActivityMoreInformation5Binding::inflate) {
    private lateinit var lastSemesterScore : MutableMap<Button,Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lastSemesterScore = mutableMapOf(binding.btnLastSemester1 to false,
            binding.btnLastSemester2 to false,
            binding.btnLastSemester3 to false,
            binding.btnLastSemester4 to false)


        //지난 학기 학점 버튼들 클릭 리스너 장착
        for (i in lastSemesterScore.keys){
            i.setOnClickListener(lastSemesterScoreBtnClickListener)
        }

        //추가학기/졸업유예 체크 상태 변화는 하기 위한 리스너
        binding.btnCheck.setOnClickListener {

        }

    }




    /*
 모든 버튼의 선택됨은 미선택됨으로 바꾸어주는 메소드
  */
    fun allPressedFalse(btn:Button){
        for (i in lastSemesterScore.keys){
            i.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
            i.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))
            if(i!=btn) //선택된 버튼의 value값은 true를 유지하기 위한 조건문
                lastSemesterScore[i]=false //선택된 버튼이 아니라면 모든 버튼의 value값은 false처리
        }
    }


    /*
   btnLastSemester1~4버튼들의 클릭 리스너
    */
    private val lastSemesterScoreBtnClickListener: View.OnClickListener = object: View.OnClickListener{
        override fun onClick(v: View?) {
            //선택되어진 view를 버튼으로 형변환
            var btn = v as Button

            allPressedFalse(btn) //특정 버튼이 선택되면 다른 버튼은 미선택 상태로 변경해주기 위한 메소드
            //majorBtnMap의 키값중 버튼에 해당하는 value값이 false이면 선택이 안되어 있는 상태이기 때문에 선택됨으로 변경
            if(lastSemesterScore[btn]==false){
                btn.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                btn.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
                lastSemesterScore[btn]=true //버튼이 false(미선택) 상태였기 때문에 true(선택됨)으로 바꿔준다
            }else{//majorBtnMap의 키값중 버튼에 해당하는 value값이 true이면 선택이 되어 있는 상태이기 때문에 미선택됨으로 변경
                btn.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                btn.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
                lastSemesterScore[btn]=false //버튼이 true(선택됨)인 상태였기 때문에 false(미선택)로 바꿔준다.
            }
        }
    }
}