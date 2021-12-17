package com.teamteam.knowing.ui.view.main.bookmark

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.R
import com.teamteam.knowing.databinding.DialogSelectFilterBinding
import com.teamteam.knowing.ui.viewmodel.BookmarkFragmentViewModel
import com.teamteam.knowing.ui.viewmodel.CustomWelfareFragmentViewModel


class SelectBookmarkFilterDialog() : DialogFragment() {
    private lateinit var binding: DialogSelectFilterBinding
    private lateinit var bookmarkFragmentViewModel: BookmarkFragmentViewModel


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
            params!!.height = (deviceSize.y/2.8).toInt() //다이얼로그의 세로를 디바이스의 세로의 30%만큼만 차지
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
        binding = DialogSelectFilterBinding.inflate(layoutInflater) //viewBinding

        //뷰모델 장착
        bookmarkFragmentViewModel = ViewModelProvider(requireActivity()).get(BookmarkFragmentViewModel::class.java)

        when(bookmarkFragmentViewModel.currentFilter.value.toString()){
            "높은 금액순"->{
                initButtonDesign()
                binding.btnHighCost.setBackgroundResource(R.drawable.rectangle_ffeed3_radius19_5)
                binding.txHighCost.setTextColor(Color.parseColor("#ff8854"))
            }
            "낮은 금액순"->{
                initButtonDesign()
                binding.btnLowCost.setBackgroundResource(R.drawable.rectangle_ffeed3_radius19_5)
                binding.txLowCost.setTextColor(Color.parseColor("#ff8854"))
            }
            "마감일순"->{
                initButtonDesign()
                binding.btnDeadLine.setBackgroundResource(R.drawable.rectangle_ffeed3_radius19_5)
                binding.txDeadLine.setTextColor(Color.parseColor("#ff8854"))
            }
        }

        //x모양 취소 버튼 클릭 리스너
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        //높은 금액순 클릭 리스너
        binding.btnHighCost.setOnClickListener {
            //모든 버튼 노선택 모드로 변경
            initButtonDesign()
            binding.btnHighCost.setBackgroundResource(R.drawable.rectangle_ffeed3_radius19_5)
            binding.txHighCost.setTextColor(Color.parseColor("#ff8854"))
            bookmarkFragmentViewModel.currentFilter.value="높은 금액순"
            bookmarkFragmentViewModel.sortHighCost()
        }

        //낮은 금액순 클릭 리스너
        binding.btnLowCost.setOnClickListener {
            //모든 버튼 노선택 모드로 변경
            initButtonDesign()
            binding.btnLowCost.setBackgroundResource(R.drawable.rectangle_ffeed3_radius19_5)
            binding.txLowCost.setTextColor(Color.parseColor("#ff8854"))
            bookmarkFragmentViewModel.currentFilter.value="낮은 금액순"
            bookmarkFragmentViewModel.sortLowCost()
        }

        //마감일순 클릭 리스너
        binding.btnDeadLine.setOnClickListener {
            //모든 버튼 노선택 모드로 변경
            initButtonDesign()
            binding.btnDeadLine.setBackgroundResource(R.drawable.rectangle_ffeed3_radius19_5)
            binding.txDeadLine.setTextColor(Color.parseColor("#ff8854"))
            bookmarkFragmentViewModel.currentFilter.value="마감일순"
            bookmarkFragmentViewModel.sortDeadLine()
        }

        return binding.root
    }

    /*
    버튼의 백그라운드와 글씨 색상을 변경하는 메소드
     */
    fun initButtonDesign(){
        binding.btnHighCost.setBackgroundResource(R.color.trans)
        binding.btnLowCost.setBackgroundResource(R.color.trans)
        binding.btnDeadLine.setBackgroundResource(R.color.trans)
        binding.txHighCost.setTextColor(Color.parseColor("#b0b0b0"))
        binding.txLowCost.setTextColor(Color.parseColor("#b0b0b0"))
        binding.txDeadLine.setTextColor(Color.parseColor("#b0b0b0"))
    }
}