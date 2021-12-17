package com.teamteam.knowing.ui.view.main.mypage.user_correct

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_UID
import com.teamteam.knowing.data.model.network.request.UserAddCorrectRequest
import com.teamteam.knowing.databinding.ActivityMoreInformationCorrectBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.viewmodel.MoreInformationCorrectActivityViewModel
import java.text.DecimalFormat

class MoreInformationCorrectActivity :
    BaseActivity<ActivityMoreInformationCorrectBinding>(ActivityMoreInformationCorrectBinding::inflate) {
    private lateinit var moreInformationCorrectActivityViewModel: MoreInformationCorrectActivityViewModel

    private var result = "" //edtIncome의 데이터를 저장하고 있을 변수
    private val decimalFormat: DecimalFormat =
        DecimalFormat("#,###") //edtIncome에서 3번째 마다 ,를 찍어주기 위한 포맷팅 설정

    private lateinit var majorBtnMap: MutableMap<Button, Boolean>

    private lateinit var lastSemesterScore: MutableMap<Button, Boolean>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뷰모델 장착
        moreInformationCorrectActivityViewModel =
            ViewModelProvider(this).get(MoreInformationCorrectActivityViewModel::class.java)



        /*
        MoreInformation1Activity
         */
        // 시/도에서 선택할 경우 선택된 데이터로 텍스트를 변경해주기 위해 옵저버 패턴을 이용해 뷰모델의 _currentTxDo를 관찰한다.
        moreInformationCorrectActivityViewModel.currentTxDo.observe(this, Observer {
            binding.txDo.text = it.toString()
        })


        // 시/도가 선택이 되었을 때 텍스트 색상을 변경하기 위해 옵저버 패턴을 이용해 isSelectDo 관찰
        moreInformationCorrectActivityViewModel.isSelectDo.observe(this, Observer {
            if (it) {
                binding.txDo.setTextColor(Color.parseColor("#414141")) //바텀시트 다이얼로그에서 값이 선택되어 지면 색상을 변경
                val typeFace = Typeface.createFromAsset(
                    assets,
                    "roboto_regular.ttf"
                ) //'시/도 선택' 폰트 변경하기 위해 에셋에서 불러옴
                binding.txDo.typeface = typeFace //폰트 적용
                binding.txNoChoiceDo.visibility = View.GONE
            }
        })


        // 시/군/구 에서 선택할 경우 선택된 데이터로 텍스트를 변경해주기 위해 옵저버 패턴을 이용해 뷰모델의 _currentTxDo를 관찰한다.
        moreInformationCorrectActivityViewModel.currentTxSi.observe(this, Observer {
            binding.txSi.text = it.toString()
        })


        // 시/군/군에서 선택이 되었을 때 텍스트 색상을 변경하기 위해 옵저버 패턴을 이용해 isSelectDo 관찰
        moreInformationCorrectActivityViewModel.isSelectSi.observe(this, Observer {
            if (it) {
                binding.txSi.setTextColor(Color.parseColor("#414141")) //바텀시트 다이얼로그에서 값이 선택되어 지면 색상을 변경
            } else {
                binding.txSi.setTextColor(Color.parseColor("#c2c2c2")) //바텀시트 다이얼로그에서 값이 선택되어 지면 색상을 변경
            }
        })

        //특별사항 선택 다이얼로그에서 선택된 버튼들의 정보를 관찰
        moreInformationCorrectActivityViewModel.checkedBtnInfo.observe(this, Observer {
            //선택되어진 후 텍스트 색상을 변경
            binding.txSpecial.setTextColor(Color.parseColor("#414141"))

            //받아온 라이브데이터를 - 기준으로 나눈다.
            val str = it.split("-")
            if (str.size == 2) {//나눈 값이 2개이면(처음 초기화하는 것 때문에 사이즈가2로 찍힘)
                binding.txSpecial.text = str[0]//사이즈는2이지만 실제 데이터는 인덱스0번에 저장되어 있기 때문에 str[0]을 저장
            } else {
                //사이즈가 처음 checkedBtnInfo를 초기화하는 과정에서 1개가 추가되기 때문에 실제 개수를 위해 str.size에서 -1을 하였다.
                binding.txSpecial.text = "선택사항 ${str.size - 1}개"
            }
        })

        //거주지 시/도 클릭 리스너
        binding.btnSelectDo.setOnClickListener {
            val bottomSheet = CorrectSelectDoDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        //거주지 시/군/구 클릭 리스너
        binding.btnSelectSi.setOnClickListener {
            val bottomSheet = CorrectSelectSiDialog()
            moreInformationCorrectActivityViewModel.getSiList()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag) //시/군/구 선택하는 다이얼로그 출력
        }

        //특별사항 클릭 리스너
        binding.btnSelectSpecial.setOnClickListener {
            val bottomSheet = CorrectSelectSpecialDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }


        /*
        MoreInformation2Activity
         */
        //edtIncome의 포커싱 리스너
        binding.edtIncome.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {//포커싱이 생길 때....
            } else { //포커싱이 사라질 때...
                //뷰모델의 라이브데이터로 edtIncome의 데이터 저장
                moreInformationCorrectActivityViewModel.currentEdtIncome.value =
                    binding.edtIncome.text.toString()
            }
        }

        //edtEmail의 키보드를 완료로 바꿔주기 위한 코드
        binding.edtIncome.imeOptions = EditorInfo.IME_ACTION_DONE

        //edtIncome 완료 버튼 클릭 이벤트. 완료 버튼을 누르면 포커싱을 없애주기 위한 코드
        binding.edtIncome.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) { //액션 iD가 완료이면
                binding.edtIncome.clearFocus() //edtPwd 포커싱 없애기

                //키보드 밑으로 내리는 코드
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtIncome.windowToken, 0);

                handled = true
            }
            handled
        }


        //edtIncome 3자리마다 콤마 자동으로 찍어주기 위한 코드
        binding.edtIncome.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                //월 소득이 입력되어져 있는지 아닌지 판단하기 위한 라이브데이터 변경
                moreInformationCorrectActivityViewModel.isSelectEdtIncome.value =
                    binding.edtIncome.text.toString().isNotEmpty()


                if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(result)) {
                    result = decimalFormat.format((s.toString().replace(",", "")).toDouble())
                    binding.edtIncome.setText(result)
                    binding.edtIncome.setSelection(result.length)

                    //월 소득이 입력되어져 있는지 아닌지 판단하기 위한 라이브데이터 변경
                    moreInformationCorrectActivityViewModel.isSelectEdtIncome.value =
                        binding.edtIncome.text.toString().isNotEmpty()
                    //'다음'버튼 활성화를 확인하는 메소드 출력
                    moreInformationCorrectActivityViewModel.checkIsCorrect()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


        //취업상태-전체 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnAll.observe(this, Observer {
            if (it) {
                binding.btnGetJob1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob1.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnGetJob1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob1.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-미취업자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnUnEmployment.observe(this, Observer {
            if (it) {
                binding.btnGetJob2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob2.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnGetJob2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob2.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-재직자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnEmployment.observe(this, Observer {
            if (it) {
                binding.btnGetJob3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob3.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnGetJob3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob3.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-프리랜서 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnFreelancer.observe(this, Observer {
            if (it) {
                binding.btnGetJob4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob4.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnGetJob4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob4.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-일용근로자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnDailyWorker.observe(this, Observer {
            if (it) {
                binding.btnGetJob5.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob5.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob5.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnGetJob5.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob5.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob5.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-단기근로자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnShortWorker.observe(this, Observer {
            if (it) {
                binding.btnGetJob6.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob6.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob6.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnGetJob6.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob6.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob6.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-예비창업자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnPrepEntrepreneur.observe(this, Observer {
            if (it) {
                binding.btnGetJob7.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob7.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob7.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnGetJob7.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob7.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob7.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-자영업자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnSelfOwnership.observe(this, Observer {
            if (it) {
                binding.btnGetJob8.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob8.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob8.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnGetJob8.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob8.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob8.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-영농종사자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnFarming.observe(this, Observer {
            if (it) {
                binding.btnGetJob9.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob9.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob9.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnGetJob9.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob9.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob9.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })


        //수정하기 버튼 활성화 여부 판단 하는 곳
        moreInformationCorrectActivityViewModel.isCorrect.observe(this, Observer {
            if (it)
                binding.btnCorrect.isEnabled=it
        })



        //취업상태-전체 버튼 클릭 리스너
        binding.btnGetJob1.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnAll()
        }
        //취업상태-미취업자 버튼 클릭 리스너
        binding.btnGetJob2.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnUnEmployment()

        }
        //취업상태-재직자 버튼 클릭 리스너
        binding.btnGetJob3.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnEmployment()

        }
        //취업상태-프리랜서 버튼 클릭 리스너
        binding.btnGetJob4.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnFreelancer()

        }
        //취업상태-일용근로자 버튼 클릭 리스너
        binding.btnGetJob5.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnDailyWorker()

        }
        //취업상태-단기근로자 버튼 클릭 리스너
        binding.btnGetJob6.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnShortWorker()

        }
        //취업상태-예비창업자 버튼 클릭 리스너
        binding.btnGetJob7.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnPrepEntrepreneur()

        }
        //취업상태-자영업자 버튼 클릭 리스너
        binding.btnGetJob8.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnSelfOwnership()
        }
        //취업상태-영농종사자 버튼 클릭 리스너
        binding.btnGetJob9.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnFarming()
        }


        /*
        MoreInformation3Activity
         */

        //전체 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectSchoolRecordsBtnAll.observe(this, Observer {
            if (it) {
                binding.btnRegister1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister1.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnRegister1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister1.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //고졸미만 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnUnderHighSchool.observe(this, Observer {
            if (it) {
                binding.btnRegister2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister2.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnRegister2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister2.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //고교졸업 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnGraduateHighSchool.observe(
            this,
            Observer {
                if (it) {
                    binding.btnRegister3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                    binding.btnRegister3.setTextColor(Color.parseColor("#ffffff"))
                    binding.btnRegister3.typeface = Typeface.createFromAsset(
                        assets,
                        "roboto_bold.ttf"
                    ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
                } else {
                    binding.btnRegister3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                    binding.btnRegister3.setTextColor(Color.parseColor("#6c6c6c"))
                    binding.btnRegister3.typeface = Typeface.createFromAsset(
                        assets,
                        "roboto_medium.ttf"
                    ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
                }
            })
        //대학재학 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnBeingCollege.observe(this, Observer {
            if (it) {
                binding.btnRegister4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister4.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnRegister4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister4.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //대학졸업 의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnGraduateCollege.observe(this, Observer {
            if (it) {
                binding.btnRegister5.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister5.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister5.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnRegister5.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister5.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister5.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //석박사 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectBtnDoctor.observe(this, Observer {
            if (it) {
                binding.btnRegister6.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister6.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister6.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnRegister6.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister6.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister6.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })


        //다이얼로그에서 선택한 대학교 이름이 TextView에 보이기 위해 선택한 학교 이름을 저장하고 있는 라이브데이터 관찰
        moreInformationCorrectActivityViewModel.currentSelectTxCollegeName.observe(this, Observer {
            binding.txCollegeName.text = it
        })


        //전체 버튼 클릭 리스너
        binding.btnRegister1.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeSchoolRecordsBtnAll()
        }
        //고졸 미만 버튼 클릭 리스너
        binding.btnRegister2.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnUnderHighSchool()
        }
        //고교졸업 버튼 클릭 리스너
        binding.btnRegister3.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnGraduateHighSchool()
        }
        //대학재학 버튼 클릭 리스너
        binding.btnRegister4.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnAllBeingCollege()
        }
        //대학졸업 버튼 클릭 리스너
        binding.btnRegister5.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnGraudateCollege()
        }
        //석박사 버튼 클릭 리스너
        binding.btnRegister6.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeBtnDoctor()
        }


        //학교 선택 버튼 클릭 리스너
        binding.btnSelectSchool.setOnClickListener {
            val bottomSheet = CorrectSelectCollegeDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }


        /*
        MoreInformation4Activity
         */
        //인문 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectHumanities.observe(this, Observer {
            if (it) {
                binding.btnMajor1.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor1.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor1.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //사회 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectSociety.observe(this, Observer {
            if (it) {
                binding.btnMajor2.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor2.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor2.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //법 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectLaw.observe(this, Observer {
            if (it) {
                binding.btnMajor3.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor3.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor3.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //경영 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectManagement.observe(this, Observer {
            if (it) {
                binding.btnMajor4.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor4.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor4.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })


        //교육 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectEducation.observe(this, Observer {
            if (it) {
                binding.btnMajor5.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor5.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor5.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor5.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //공학 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectEngineering.observe(this, Observer {
            if (it) {
                binding.btnMajor6.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor6.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor6.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor6.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //자연 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectNature.observe(this, Observer {
            if (it) {
                binding.btnMajor7.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor7.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor7.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor7.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //예체능 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectEntertainment.observe(this, Observer {
            if (it) {
                binding.btnMajor8.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor8.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor8.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor8.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })


        //의약 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectMedical.observe(this, Observer {
            if (it) {
                binding.btnMajor9.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor9.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor9.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor9.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //기타 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationCorrectActivityViewModel.isSelectEtc.observe(this, Observer {
            if (it) {
                binding.btnMajor10.background =
                    getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor10.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            } else {
                binding.btnMajor10.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor10.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })


        //majorBtn1~10까지를 Map으로 선언 값이 선택되어져 있는지 확인하기 위함
        majorBtnMap = mutableMapOf<Button, Boolean>(
            binding.btnMajor1 to false,
            binding.btnMajor2 to false,
            binding.btnMajor3 to false,
            binding.btnMajor4 to false,
            binding.btnMajor5 to false,
            binding.btnMajor6 to false,
            binding.btnMajor7 to false,
            binding.btnMajor8 to false,
            binding.btnMajor9 to false,
            binding.btnMajor10 to false
        )

        //majorBtn1~10까지에 클리 리스너 장착
        for (i in majorBtnMap.keys) {
            i.setOnClickListener(majorBtnClickListener)
        }


        /*
        MoreInformation5Activity
         */
        //datePicker에서 선택시 라이브 데이터가 변경되면 옵저버해서 txGrade를 변경함
        moreInformationCorrectActivityViewModel.currentTxGrade.observe(this, Observer {
            binding.txGrade.text = it.toString()
            binding.txGrade.setTextColor(Color.parseColor("#414141"))
        })

        //datePicker에서 선택시 라이브 데이터가 변경되면 옵저버해서 txSemester를 변경함
        moreInformationCorrectActivityViewModel.currentTxSemester.observe(this, Observer {
            binding.txSemester.text = it.toString()
            binding.txSemester.setTextColor(Color.parseColor("#414141"))
        })

        //추가 학기 / 졸업 유예 체크 상태를 변화시키기 위해 뷰모델의 currentCheckState상태를 옵저버로 확인해 백그라운드를 변경시켜 준다.
        moreInformationCorrectActivityViewModel.currentAddCheckState.observe(this, Observer {
            if (it)
                binding.btnCheck.setBackgroundResource(R.drawable.group_checkbox_color)
            else
                binding.btnCheck.setBackgroundResource(R.drawable.group_checkbox)
        })

        //~2.9버튼 체크 상태를 관찰해서 백그라운드 변경
        moreInformationCorrectActivityViewModel.isSelectLow.observe(this, Observer {
            if (it) {
                binding.btnLastSemesterScore1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLastSemesterScore1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLastSemesterScore1.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnLastSemesterScore1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLastSemesterScore1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLastSemesterScore1.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //3.0~3.4버튼 체크 상태를 관찰해서 백그라운드 변경
        moreInformationCorrectActivityViewModel.isSelectMedium.observe(this, Observer {
            if (it) {
                binding.btnLastSemesterScore2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLastSemesterScore2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLastSemesterScore2.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnLastSemesterScore2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLastSemesterScore2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLastSemesterScore2.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        // 3.5~3.9 버튼 체크 상태를 관찰해서 백그라운드 변경
        moreInformationCorrectActivityViewModel.isSelectHigh.observe(this, Observer {
            if (it) {
                binding.btnLastSemesterScore3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLastSemesterScore3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLastSemesterScore3.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnLastSemesterScore3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLastSemesterScore3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLastSemesterScore3.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //해당 없음 버튼 체크 상태를 관찰해서 백그라운드 변경
        moreInformationCorrectActivityViewModel.isSelectNone.observe(this, Observer {
            if (it) {
                binding.btnLastSemesterScore4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLastSemesterScore4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLastSemesterScore4.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_bold.ttf"
                ) //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else {
                binding.btnLastSemesterScore4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLastSemesterScore4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLastSemesterScore4.typeface = Typeface.createFromAsset(
                    assets,
                    "roboto_medium.ttf"
                ) //미선택 시 중간 폰트 변경하기 위해 에셋에서 불러옴
            }
        })


        //지난학기성적 버튼들을 map으로 선언
        lastSemesterScore = mutableMapOf(
            binding.btnLastSemesterScore1 to false,
            binding.btnLastSemesterScore2 to false,
            binding.btnLastSemesterScore3 to false,
            binding.btnLastSemesterScore4 to false
        )


        //지난 학기 학점 버튼들 클릭 리스너 장착
        for (i in lastSemesterScore.keys) {
            i.setOnClickListener(lastSemesterScoreBtnClickListener)
        }

        //추가학기/졸업유예 체크 상태 변화는 하기 위한 리스너
        binding.btnCheck.setOnClickListener {
            moreInformationCorrectActivityViewModel.changeAddBtnBackground()
        }

        //학년/학기 클릭하면 datePicker 출력하기 위한 클릭 리스너
        binding.btnSelectGradeSemester.setOnClickListener {
            showPickerDialog()
        }



        /*
       기존 데이터를 입력하기 위해 api 호출하는 부분
        */
        moreInformationCorrectActivityViewModel.tryGetUserInfo(USER_UID)


        //수정하기 버튼 클릭 리스너
        binding.btnCorrect.setOnClickListener {
            val userData = UserAddCorrectRequest(moreInformationCorrectActivityViewModel.currentTxDo.value.toString(),moreInformationCorrectActivityViewModel.currentTxSi.value.toString(),
                moreInformationCorrectActivityViewModel.getSpecialStatus(),moreInformationCorrectActivityViewModel.getIncomeLevel(binding.edtIncome.text.toString()),
                moreInformationCorrectActivityViewModel.getIncomeAvg(binding.edtIncome.text.toString()),moreInformationCorrectActivityViewModel.getEmployState(),
                moreInformationCorrectActivityViewModel.getSchoolRecords(),moreInformationCorrectActivityViewModel.currentSelectTxCollegeName.value.toString(),
                moreInformationCorrectActivityViewModel.getMainMajor(),binding.edtMajorDetail.text.toString(),"${moreInformationCorrectActivityViewModel.currentTxGrade.value.toString()} ${moreInformationCorrectActivityViewModel.currentTxSemester.value.toString()}",
                moreInformationCorrectActivityViewModel.getLastSemesterScore())

            moreInformationCorrectActivityViewModel.tryPatchUserAddCorrect(USER_UID,userData)
        }

        moreInformationCorrectActivityViewModel.ifSuccessCorrectApi.observe(this, Observer {
            if (it){
                Toast.makeText(this,"추가정보가 변경되었습니다.",Toast.LENGTH_SHORT).show()
                this.finish()
            }else
                Toast.makeText(this,"추가정보 변경이 실패하였습니다.",Toast.LENGTH_SHORT).show()
        })

        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.finish()
        }
    }




    /*
    btnLastSemester1~4버튼들의 클릭 리스너
    */
    private val lastSemesterScoreBtnClickListener: View.OnClickListener =
        object : View.OnClickListener {
            override fun onClick(v: View?) {
                when (v) {
                    binding.btnLastSemesterScore1 -> {
                        moreInformationCorrectActivityViewModel.changeBtnLow()//~2.9버튼의 상태를 반전 시키위해 메소드 호출
                    }
                    binding.btnLastSemesterScore2 -> {
                        moreInformationCorrectActivityViewModel.changeBtnMedium()//3.0~3.4버튼의 상태를 반전 시키위해 메소드 호출
                    }
                    binding.btnLastSemesterScore3 -> {
                        moreInformationCorrectActivityViewModel.changeBtnHigh()//3.5~3.9버튼의 상태를 반전 시키위해 메소드 호출
                    }
                    binding.btnLastSemesterScore4 -> {
                        moreInformationCorrectActivityViewModel.changeBtnNone()//해당 없음 버튼의 상태를 반전 시키위해 메소드 호출
                    }
                }
            }
        }


    /*
    btnMajor1~10버튼들의 클릭 리스너
    */
    private val majorBtnClickListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v) {
                binding.btnMajor1 -> {
                    moreInformationCorrectActivityViewModel.changeBtnHumanities()
                }
                binding.btnMajor2 -> {
                    moreInformationCorrectActivityViewModel.changeBtnSociety()
                }
                binding.btnMajor3 -> {
                    moreInformationCorrectActivityViewModel.changeBtnLaw()
                }
                binding.btnMajor4 -> {
                    moreInformationCorrectActivityViewModel.changeBtnManagement()
                }
                binding.btnMajor5 -> {
                    moreInformationCorrectActivityViewModel.changeBtnEducation()
                }
                binding.btnMajor6 -> {
                    moreInformationCorrectActivityViewModel.changeBtnEngineering()
                }
                binding.btnMajor7 -> {
                    moreInformationCorrectActivityViewModel.changeBtnNature()
                }
                binding.btnMajor8 -> {
                    moreInformationCorrectActivityViewModel.changeBtnEntertainment()
                }
                binding.btnMajor9 -> {
                    moreInformationCorrectActivityViewModel.changeBtnMedical()
                }
                binding.btnMajor10 -> {
                    moreInformationCorrectActivityViewModel.changeBtnEtc()
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

        //확인 버튼 클릭 리스너
        save.setOnClickListener {
            var strGrade = grade.value.toString() //numberpicker에서 선택한 데이터 중 grade 부분을 저장
            var strSemester =
                semester.value.toString() //numberpicker에서 선택한 데이터 중 semester 부분을 저장

            //피커에서 선택한 값을 뷰모델의 있는 라이브데이터를 변경시켜줌
            moreInformationCorrectActivityViewModel.currentTxGrade.value = strGrade
            moreInformationCorrectActivityViewModel.currentTxSemester.value = strSemester

            //피커 다이얼로그 종료
            dialog.dismiss()
        }

        dialog.setView(mView) //dialog의 view를 설정
        dialog.create() //dialog생성
        dialog.show() //dialog 출력
    }
}
