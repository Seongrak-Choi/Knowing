package com.example.knowing.ui.view.more_information

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.knowing.R
import com.example.knowing.config.ApplicationClass
import com.example.knowing.databinding.DialogSelectCollegeBinding
import com.example.knowing.databinding.DialogSelectDoBinding
import com.example.knowing.databinding.DialogSelectSiBinding
import com.example.knowing.ui.adapter.SelectCollegeDialogRCAdapter
import com.example.knowing.ui.adapter.SelectDoDialogRCAdapter
import com.example.knowing.ui.viewmodel.MoreInformationActivity3ViewModel

class SelectCollegeDialog  : DialogFragment() {
    private lateinit var binding: DialogSelectCollegeBinding
    private lateinit var moreInformationActivity3ViewModel: MoreInformationActivity3ViewModel
    private lateinit var adapter : SelectCollegeDialogRCAdapter

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
            params!!.height = (deviceSize.y/1.4).toInt() //다이얼로그의 세로를 디바이스의 세로의 60%만큼만 차지
            //params!!.horizontalMargin = 0.0f //가로의 마진 없애기인데 사실 이거 없어도 가로가 꽉참.

            //다이얼로그 팝업 애니메이션 효과 주기
            params.windowAnimations= R.style.AnimationPopupStyle

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
        binding = DialogSelectCollegeBinding.inflate(layoutInflater) //viewBinding


        //뷰모델 장착
        moreInformationActivity3ViewModel =
            ViewModelProvider(requireActivity()).get(MoreInformationActivity3ViewModel::class.java)

        //x모양 취소 버튼 클릭 리스너
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }


        //리사이클러뷰 어댑터에 들어가는 리스트를 컨트롤하기 위해 뷰모델에 있는 라이브 데이터를 옵저버 패턴으로 관찰해서 어댑터 생성 후 장착
        moreInformationActivity3ViewModel.currentCollegeNameList.observe(this, Observer {
            binding.rcDo.layoutManager = GridLayoutManager(context, 1)
            adapter = SelectCollegeDialogRCAdapter(it)
            binding.rcDo.adapter = adapter
            adapter.notifyDataSetChanged()

            //리사이클러뷰 어댑터에내에서 선택되어진 동작을 다이얼로그에서 구현하기 위해 리스너 생성 후 장착
            adapter.setOnItemClickListener(object : SelectDoDialogRCAdapter.OnItemClickListener {
                override fun onItemClick(value: String) {
                    moreInformationActivity3ViewModel.currentSelectTxCollegeName.value =
                        value  //리사이클러뷰에서 선택되어진 데이터를 뷰모델의 currentTxSi에 저장

                    dialog?.dismiss()
                }
            })
        })


        //search_edt를 누를 때 나오는 키패드가 다음이 아닌 완료 버튼이 나오도록 설정
        binding.edtSearch.imeOptions= EditorInfo.IME_ACTION_DONE

        //edtSearch에서 키보드 완료를 누르면 실행되는 리스너
        binding.edtSearch.setOnEditorActionListener { textView, i, keyEvent ->
            if (i === EditorInfo.IME_ACTION_DONE) {
                //검색한 데이터로 대학교 이름 검색하는 api통신 실행
                moreInformationActivity3ViewModel.tryGetCollege(
                    ApplicationClass.COLLEGE_API_KEY,"api","SCHOOL","univ_list",binding.edtSearch.text.toString(),
                    "json")

                //완료 버튼 누르면 키보드가 내려가게 하기 위한 코드
                val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtSearch.windowToken, 0)
            }
            true
        }

        return binding.root
    }
}