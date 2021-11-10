package com.example.knowing.ui.view.more_information

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.knowing.R
import com.example.knowing.databinding.DialogSelectDoBinding
import com.example.knowing.databinding.DialogSelectSiBinding
import com.example.knowing.ui.adapter.SelectDoDialogRCAdapter
import com.example.knowing.ui.viewmodel.MoreInformationActivity1ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectSiDialog: DialogFragment(){
    private lateinit var binding : DialogSelectSiBinding
    private lateinit var moreInformationActivity1ViewModel: MoreInformationActivity1ViewModel
    private lateinit var adapter : SelectDoDialogRCAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //다이얼로그가 키패드가 올라와도 밀리지 않게 하기위해 theme에서 스타일을 정의하고 그 스타일을 onCreate에서 설정
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.theme_popupdialog_stlye)
    }

    override fun onResume() {
        super.onResume()

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
            dialog?.window?.attributes = params
            dialog?.window?.setGravity(Gravity.BOTTOM) //다이얼로그를 바텀에 붙히기 위함

            //다이얼로그 팝업 애니메이션 효과 주기
            params.windowAnimations=R.style.AnimationPopupStyle

            val window = dialog?.window
            //다이얼로그를 띄우는 백그라운드를 투명하게하여 둥근 커스텀이 보이고 꽉차도록 만듬
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogSelectSiBinding.inflate(layoutInflater) //viewBinding

        //뷰모델 장착
        moreInformationActivity1ViewModel=ViewModelProvider(requireActivity()).get(MoreInformationActivity1ViewModel::class.java)

        //x모양 취소 버튼 클릭 리스너
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        //리사이클러뷰 어댑터에 들어가는 리스트를 컨트롤하기 위해 뷰모델에 있는 라이브 데이터를 옵저버 패턴으로 관찰해서 어댑터 생성 후 장착
        moreInformationActivity1ViewModel.siList.observe(this, Observer {
            binding.rcDo.layoutManager=GridLayoutManager(context,2)
            adapter=SelectDoDialogRCAdapter(it)
            binding.rcDo.adapter=adapter
            adapter.notifyDataSetChanged()

            //리사이클러뷰 어댑터에내에서 선택되어진 동작을 다이얼로그에서 구현하기 위해 리스너 생성 후 장착
            adapter.setOnItemClickListener(object:SelectDoDialogRCAdapter.OnItemClickListener{
                override fun onItemClick(value: String) {
                    moreInformationActivity1ViewModel.currentTxSi.value=value //리사이클러뷰에서 선택되어진 데이터를 뷰모델의 currentTxSi에 저장
                    moreInformationActivity1ViewModel.isSelectSi.value=true //리사이클러뷰에서 시/군/구가 선택되어지면 선택이 되었다고 뷰모델의 isSelectSi에 true저장

                    //specialDialog가 닫힐 때 다음 버튼의 활성화 여부를 체크하기 위한 메소드 실행
                    moreInformationActivity1ViewModel.checkIsAllCorrect()

                    dialog?.dismiss() //다이얼로그 안보이게
                }
            })
        })

        //search_edt를 누를 때 나오는 키패드가 다음이 아닌 완료 버튼이 나오도록 설정
        binding.edtSearch.imeOptions=EditorInfo.IME_ACTION_DONE

        binding.edtSearch.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        return binding.root
    }
}