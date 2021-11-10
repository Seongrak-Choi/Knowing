package com.example.knowing.ui.view.more_information

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.databinding.ActivityMoreInformation5Binding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.MoreInformationActivity5ViewModel
import com.example.knowing.ui.viewmodel.SignUpActivityViewModel

class MoreInformationActivity5:BaseActivity<ActivityMoreInformation5Binding>(ActivityMoreInformation5Binding::inflate) {
    private lateinit var lastSemesterScore : MutableMap<Button,Boolean>
    private lateinit var moreInformation5ActivityViewModel: MoreInformationActivity5ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //이 화면은, 오른쪽에서 왼쪽으로 슬라이딩 하면서 켜집니다.
        overridePendingTransition(R.animator.horizon_enter,R.animator.none)

        //뷰모델 장착
        moreInformation5ActivityViewModel =  ViewModelProvider(this).get(MoreInformationActivity5ViewModel::class.java)

        //datePicker에서 선택시 라이브 데이터가 변경되면 옵저버해서 txGrade를 변경함
        moreInformation5ActivityViewModel.currentTxGrade.observe(this, Observer {
            binding.txGrade.text=it.toString()
        })

        //datePicker에서 선택시 라이브 데이터가 변경되면 옵저버해서 txSemester를 변경함
        moreInformation5ActivityViewModel.currentTxSemester.observe(this, Observer {
            binding.txSemester.text=it.toString()
        })

        //추가 학기 / 졸업 유예 체크 상태를 변화시키기 위해 뷰모델의 currentCheckState상태를 옵저버로 확인해 백그라운드를 변경시켜 준다.
        moreInformation5ActivityViewModel.currentCheckState.observe(this, Observer {
            if (it)
                binding.btnCheck.setBackgroundResource(R.drawable.group_checkbox_color)
            else
                binding.btnCheck.setBackgroundResource(R.drawable.group_checkbox)
        })

        //지난학기성적 버튼들을 map으로 선언
        lastSemesterScore = mutableMapOf(binding.btnLastSemesterScore1 to false,
            binding.btnLastSemesterScore2 to false,
            binding.btnLastSemesterScore3 to false,
            binding.btnLastSemesterScore4 to false)


        //지난 학기 학점 버튼들 클릭 리스너 장착
        for (i in lastSemesterScore.keys){
            i.setOnClickListener(lastSemesterScoreBtnClickListener)
        }

        //추가학기/졸업유예 체크 상태 변화는 하기 위한 리스너
        binding.btnCheck.setOnClickListener {
            moreInformation5ActivityViewModel.changeBtnBackground()
        }

        //학년/학기 클릭하면 datePicker 출력하기 위한 클릭 리스너
        binding.btnSelectGradeSemester.setOnClickListener {
            showPickerDialog()
        }
    }

    /*
    화면이 종료 될때 실행되는 메소드
     */
    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing){
            overridePendingTransition(R.animator.none,R.animator.horizon_exit)
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


    /*
   datepicker 출력
    */
    fun showPickerDialog() {
        val dialog = AlertDialog.Builder(this).create()
        val edialog: LayoutInflater = LayoutInflater.from(this)
        val mView: View = edialog.inflate(R.layout.dialog_datepicker_grade_semester, null)

        val grade: NumberPicker = mView.findViewById(R.id.picker_grade)
        val semester: NumberPicker = mView.findViewById(R.id.picker_semester)
        val cancel: Button = mView.findViewById(R.id.btn_cancel)
        val save: Button = mView.findViewById(R.id.btn_ok)

        //  순환 안되게 막기코드인데 나는 순환 되도록 설정함
//        year.wrapSelectorWheel = false
//        month.wrapSelectorWheel = false
//        day.wrapSelectorWheel = false

        //  editText 설정 해제
        grade.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        semester.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        //  최소값 설정
        grade.minValue = 1
        semester.minValue = 1

        //  최대값 설정
        grade.maxValue = 4
        semester.maxValue = 2

        //기본값 설정
        grade.value = 2
        semester.value = 2

        //취소 버튼 클릭 리스너
        cancel.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }

        save.setOnClickListener {
            var strGrade = grade.value.toString() //numberpicker에서 선택한 데이터 중 grade 부분을 저장
            var strSemester = semester.value.toString() //numberpicker에서 선택한 데이터 중 semester 부분을 저장

            //피커에서 선택한 값을 뷰모델의 있는 라이브데이터를 변경시켜줌
            moreInformation5ActivityViewModel.currentTxGrade.value=strGrade
            moreInformation5ActivityViewModel.currentTxSemester.value=strSemester

            //피커 다이얼로그 종료
            dialog.dismiss()
        }

        dialog.setView(mView) //dialog의 view를 설정
        dialog.create() //dialog생성
        dialog.show() //dialog 출력
    }
}