package com.teamteam.knowing.ui.view.main.mypage.user_correct

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.R
import com.teamteam.knowing.databinding.DialogSelectSpecialBinding
import com.teamteam.knowing.ui.adapter.SelectDoDialogRCAdapter
import com.teamteam.knowing.ui.viewmodel.MoreInformationActivity1ViewModel
import com.teamteam.knowing.ui.viewmodel.MoreInformationCorrectActivityViewModel


class CorrectSelectSpecialDialog() : DialogFragment() {
    private lateinit var binding: DialogSelectSpecialBinding
    private lateinit var moreInformationCorrectActivityViewModel: MoreInformationCorrectActivityViewModel
    private lateinit var adapter : SelectDoDialogRCAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //다이얼로그가 키패드가 올라와도 밀리지 않게 하기위해 theme에서 스타일을 정의하고 그 스타일을 onCreate에서 설정
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.theme_popupdialog_stlye)
    }

    override fun onResume() {
        super.onResume()

        //다이얼로그를 하단에 붙힘
        dialog?.window?.setGravity(Gravity.BOTTOM)

        /*
        다이얼로그는 테두리에 기본으로 마진이 되어있어서 바텀시트다이얼로그 처럼 화면에 딱 달라붙지 않는다.
        이를 해결하기 위해 임의로 다이얼로그의 마진을 없앴다. 또한 다이얼로그의 기존의 백그라운드를 투명으로 하여
        내가 지정한(위의 모서리가 둥근) 효과를 줄수 있도록 하였다.
         */
        try {
            var windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val deviceSize = Point() //Point형 변수 생성
            display.getSize(deviceSize) //display에서 받아온 사이즈를 위에서 생성한 deviceSize에 저장

            val params = dialog?.window?.attributes
            params!!.width = deviceSize.x //다이얼로그의 가로를 디바이스 만큼 넓히기
            params!!.height = (deviceSize.y/1.6).toInt() //다이얼로그의 세로를 디바이스의 세로의 60%만큼만 차지
            //params!!.horizontalMargin = 0.0f //가로의 마진 없애기인데 사실 이거 없어도 가로가 꽉참.

            //다이얼로그 팝업 애니메이션 효과 주기
            params.windowAnimations=R.style.AnimationPopupStyle

            val window = dialog?.window

            //다이얼로그를 띄우는 백그라운드를 투명하게하여 둥근 커스텀이 보이고 꽉차도록 만듬
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogSelectSpecialBinding.inflate(layoutInflater) //viewBinding


        //뷰모델 장착
        moreInformationCorrectActivityViewModel =
            ViewModelProvider(requireActivity()).get(MoreInformationCorrectActivityViewModel::class.java)

        // 선택사항 없음 체크 상태를 바꾸기 위해 뷰모델의 currentCheckState상태를 옵저버로 확인해 백그라운드를 변경시켜 준다.
        moreInformationCorrectActivityViewModel.currentCheckState.observe(this, Observer {
            if (it)
                binding.btnCheck.setBackgroundResource(R.drawable.group_checkbox_color)
            else
                binding.btnCheck.setBackgroundResource(R.drawable.group_checkbox)
        })

        //중소기업 버튼 체크 상태를 바꾸기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.currentBtnSmallBusiness.observe(this, Observer {
            if (it){
                binding.btnSmallBusiness.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnSmallBusiness.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
            else{
                binding.btnSmallBusiness.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnSmallBusiness.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //저소득층 버튼 체크 상태를 바꾸기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.currentBtnLowIncome.observe(this, Observer {
            if (it){
                binding.btnLowIncome.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLowIncome.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
            else{
                binding.btnLowIncome.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLowIncome.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //장애인 버튼 체크 상태를 바꾸기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.currentBtnDisabled.observe(this, Observer {
            if (it){
                binding.btnDisabled.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnDisabled.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
            else{
                binding.btnDisabled.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnDisabled.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //농업인 버튼 체크 상태를 바꾸기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.currentBtnFarmer.observe(this, Observer {
            if (it){
                binding.btnFarmers.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnFarmers.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
            else{
                binding.btnFarmers.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnFarmers.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //군인 버튼 체크 상태를 바꾸기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.currentBtnSolider.observe(this, Observer {
            if (it){
                binding.btnSoldier.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnSoldier.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
            else{
                binding.btnSoldier.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnSoldier.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //지역인재 버튼 체크 상태를 바꾸기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.currentBtnLocalTalent.observe(this, Observer {
            if (it){
                binding.btnLocalTalent.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLocalTalent.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
            else{
                binding.btnLocalTalent.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLocalTalent.setTextColor(getColor(requireContext(),R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //적용하기 버튼 활성화를 위해 뷰모델의 isSpecialDialogCorrect 라이브 데이터를 관찰
        moreInformationCorrectActivityViewModel.isSpecialDialogCorrect.observe(this, Observer {
                binding.btnOk.isEnabled=it
        })


        //x모양 취소 버튼 클릭 리스너
        binding.btnCancel.setOnClickListener {
            //specialDialog가 닫힐 때 다음 버튼의 활성화 여부를 체크하기 위한 메소드 실행
            //moreInformationCorrectActivityViewModel.checkIsAllCorrect()

            //선택된 버튼들의 정보를 종합해서 checkedBtnInfo라이브 데이터에 저장시키기 위한 메소드 실행
            moreInformationCorrectActivityViewModel.getCheckedBtnInfo()
            dialog?.cancel()
            dialog?.dismiss()
        }

        //적용하기 버튼 클릭 리스너
        binding.btnOk.setOnClickListener {
            //specialDialog가 닫힐 때 다음 버튼의 활성화 여부를 체크하기 위한 메소드 실행
            //moreInformationCorrectActivityViewModel.checkIsAllCorrect()

            //선택된 버튼들의 정보를 종합해서 checkedBtnInfo라이브 데이터에 저장시키기 위한 메소드 실행
            moreInformationCorrectActivityViewModel.getCheckedBtnInfo()
            dialog?.dismiss()
        }

        //선택사항 없음 버튼 클릭 리스너
        binding.btnCheck.setOnClickListener {
            //체크박스처럼 동작하기 위해 상태를 반전시켜주는 메소드 실행해서 라이브 데이터의 값을 반전시킴
            moreInformationCorrectActivityViewModel.changeBtnBackground()
        }

        //중소기업 버튼 클릭 리스너
        binding.btnSmallBusiness.setOnClickListener(specialBtnClickListener)
        //저소득층 버튼 클릭 리스너
        binding.btnLowIncome.setOnClickListener(specialBtnClickListener)
        //장애인 버튼 클릭 리스너
        binding.btnDisabled.setOnClickListener(specialBtnClickListener)
        //농업인 버튼 클릭 리스너
        binding.btnFarmers.setOnClickListener(specialBtnClickListener)
        //군인 버튼 클릭 리스너
        binding.btnSoldier.setOnClickListener(specialBtnClickListener)
        //지역인재 버튼 클릭 리스너
        binding.btnLocalTalent.setOnClickListener(specialBtnClickListener)

        return binding.root
    }

    /*
   btnMajor1~10버튼들의 클릭 리스너
    */
    private val specialBtnClickListener: View.OnClickListener = object:View.OnClickListener{
        override fun onClick(v: View?) {
            when(v){
                binding.btnSmallBusiness->
                    moreInformationCorrectActivityViewModel.changeBtnSmallBusiness()
                binding.btnLowIncome->
                    moreInformationCorrectActivityViewModel.changeBtnLowIncome()
                binding.btnDisabled->
                    moreInformationCorrectActivityViewModel.changeBtnDisabled()
                binding.btnFarmers->
                    moreInformationCorrectActivityViewModel.changeBtnFarmer()
                binding.btnSoldier->
                    moreInformationCorrectActivityViewModel.changeBtnSolider()
                binding.btnLocalTalent->
                    moreInformationCorrectActivityViewModel.changeBtnLocalTalent()
            }
        }
    }
}