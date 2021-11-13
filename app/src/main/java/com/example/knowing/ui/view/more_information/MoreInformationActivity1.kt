package com.example.knowing.ui.view.more_information

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.fonts.Font
import android.graphics.fonts.FontFamily
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.databinding.ActivityMoreInformation1Binding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.MoreInformationActivity1ViewModel

class MoreInformationActivity1 :
    BaseActivity<ActivityMoreInformation1Binding>(ActivityMoreInformation1Binding::inflate) {
    private lateinit var moreInformationActivity1ViewModel: MoreInformationActivity1ViewModel
    private var isSelectDo = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뷰모델 장착
        moreInformationActivity1ViewModel =
            ViewModelProvider(this).get(MoreInformationActivity1ViewModel::class.java)


        // 시/도에서 선택할 경우 선택된 데이터로 텍스트를 변경해주기 위해 옵저버 패턴을 이용해 뷰모델의 _currentTxDo를 관찰한다.
        moreInformationActivity1ViewModel.currentTxDo.observe(this, Observer {
            println(it)
            binding.txDo.text = it.toString()
        })


        // 시/도가 선택이 되었을 때 텍스트 색상을 변경하기 위해 옵저버 패턴을 이용해 isSelectDo 관찰
        moreInformationActivity1ViewModel.isSelectDo.observe(this, Observer {
            if (it){
                binding.txDo.setTextColor(Color.parseColor("#414141")) //바텀시트 다이얼로그에서 값이 선택되어 지면 색상을 변경
                val typeFace = Typeface.createFromAsset(assets,"roboto_regular.ttf") //'시/도 선택' 폰트 변경하기 위해 에셋에서 불러옴
                binding.txDo.typeface=typeFace //폰트 적용
                binding.txNoChoiceDo.visibility= View.GONE
            }
        })


        // 시/군/구 에서 선택할 경우 선택된 데이터로 텍스트를 변경해주기 위해 옵저버 패턴을 이용해 뷰모델의 _currentTxDo를 관찰한다.
        moreInformationActivity1ViewModel.currentTxSi.observe(this, Observer {
            println(it)
            binding.txSi.text = it.toString()
        })


        // 시/군/군에서 선택이 되었을 때 텍스트 색상을 변경하기 위해 옵저버 패턴을 이용해 isSelectDo 관찰
        moreInformationActivity1ViewModel.isSelectSi.observe(this, Observer {
            if (it){
                binding.txSi.setTextColor(Color.parseColor("#414141")) //바텀시트 다이얼로그에서 값이 선택되어 지면 색상을 변경
            }
        })

        //모든 거주지, 특별사항 다이얼로그에서 선택이 완료 되었을 경우 다음 버튼을 활성화 시키기 위해 isAllCorrect 관찰
        moreInformationActivity1ViewModel.isAllCorrect.observe(this, Observer {
            binding.btnNext.isEnabled=it
        })

        //특별사항 선택 다이얼로그에서 선택된 버튼들의 정보를 관찰
        moreInformationActivity1ViewModel.checkedBtnInfo.observe(this, Observer {
            //선택되어진 후 텍스트 색상을 변경
            binding.txSpecial.setTextColor(Color.parseColor("#414141"))

            //받아온 라이브데이터를 - 기준으로 나눈다.
            val str = it.split("-")
            if (str.size==2){//나눈 값이 2개이면(처음 초기화하는 것 때문에 사이즈가2로 찍힘)
                binding.txSpecial.text=str[0]//사이즈는2이지만 실제 데이터는 인덱스0번에 저장되어 있기 때문에 str[0]을 저장
            }else{
                //사이즈가 처음 checkedBtnInfo를 초기화하는 과정에서 1개가 추가되기 때문에 실제 개수를 위해 str.size에서 -1을 하였다.
                binding.txSpecial.text="선택사항 ${str.size-1}개"
            }
        })



        //시/도 선택 클릭 리스너
        binding.btnSelectDo.setOnClickListener {
            val bottomSheet = SelectDoDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }


        // 시/군/구 선택 클릭 리스너
        binding.btnSelectSi.setOnClickListener {
            if (moreInformationActivity1ViewModel.isSelectDo.value==true) { //시/도 가 선택되어있으면....
                val bottomSheet = SelectSiDialog()
                // 시/도 선택을 통해 저장된 시/도의 데이터를 이용해 알맞는 시/군/구 리스트를 Dummydata클래스에서 가져오는 메소드 실행
                moreInformationActivity1ViewModel.getSiList()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag) //시/군/구 선택하는 다이얼로그 출력
            } else { //시/도가 선택되어 있지 않으면...
                binding.txDo.setTextColor(Color.parseColor("#ff8854")) //'시/도 선택' 색상 변경
                val typeFace = Typeface.createFromAsset(assets,"roboto_bold.ttf") //'시/도 선택' 폰트 변경하기 위해 에셋에서 불러옴
                binding.txDo.typeface=typeFace //폰트 적용
                binding.txNoChoiceDo.visibility= View.VISIBLE
            }
        }

        //특별사항 클릭 리스너
        binding.btnSelectSpecial.setOnClickListener {
            val bottomSheet = SelectSpecialDialog()
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }


        //다음 버튼 클릭 리스너
        binding.btnNext.setOnClickListener {
            var intent  = Intent(this,MoreInformationActivity2::class.java)
            startActivity(intent)
        }
    }
}