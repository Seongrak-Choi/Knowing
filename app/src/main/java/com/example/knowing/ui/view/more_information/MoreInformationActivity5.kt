package com.example.knowing.ui.view.more_information

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
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

        //~2.9버튼 체크 상태를 관찰해서 백그라운드 변경
        moreInformation5ActivityViewModel.isSelectLow.observe(this, Observer {
            if (it){
                binding.btnLastSemesterScore1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLastSemesterScore1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLastSemesterScore1.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnLastSemesterScore1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLastSemesterScore1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLastSemesterScore1.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //3.0~3.4버튼 체크 상태를 관찰해서 백그라운드 변경
        moreInformation5ActivityViewModel.isSelectMedium.observe(this, Observer {
            if (it){
                binding.btnLastSemesterScore2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLastSemesterScore2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLastSemesterScore2.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnLastSemesterScore2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLastSemesterScore2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLastSemesterScore2.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        // 3.5~3.9 버튼 체크 상태를 관찰해서 백그라운드 변경
        moreInformation5ActivityViewModel.isSelectHigh.observe(this, Observer {
            if (it){
                binding.btnLastSemesterScore3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLastSemesterScore3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLastSemesterScore3.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnLastSemesterScore3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLastSemesterScore3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLastSemesterScore3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //해당 없음 버튼 체크 상태를 관찰해서 백그라운드 변경
        moreInformation5ActivityViewModel.isSelectNone.observe(this, Observer {
            if (it){
                binding.btnLastSemesterScore4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLastSemesterScore4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLastSemesterScore4.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnLastSemesterScore4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLastSemesterScore4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLastSemesterScore4.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //미선택 시 중간 폰트 변경하기 위해 에셋에서 불러옴
            }
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

        //완료 버튼 클릭 리스너
        binding.btnNext.setOnClickListener {
            //카테고리 선택하기 화면으로 이동
            val intent = Intent(this,SelectCategoryActivity::class.java)
            startActivity(intent)
        }

        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener{
            this.onBackPressed()
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
   btnLastSemester1~4버튼들의 클릭 리스너
    */
    private val lastSemesterScoreBtnClickListener: View.OnClickListener = object: View.OnClickListener{
        override fun onClick(v: View?) {
            when(v){
                binding.btnLastSemesterScore1->{
                    moreInformation5ActivityViewModel.changeBtnLow()//~2.9버튼의 상태를 반전 시키위해 메소드 호출
                }
                binding.btnLastSemesterScore2->{
                    moreInformation5ActivityViewModel.changeBtnMedium()//3.0~3.4버튼의 상태를 반전 시키위해 메소드 호출
                }
                binding.btnLastSemesterScore3->{
                    moreInformation5ActivityViewModel.changeBtnHigh()//3.5~3.9버튼의 상태를 반전 시키위해 메소드 호출
                }
                binding.btnLastSemesterScore4->{
                    moreInformation5ActivityViewModel.changeBtnNone()//해당 없음 버튼의 상태를 반전 시키위해 메소드 호출
                }
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