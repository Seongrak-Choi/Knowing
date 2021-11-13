package com.example.knowing.ui.view.more_information

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.databinding.ActivitySelectCategoryBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.SelectCategoryActivityViewModel

class SelectCategoryActivity : BaseActivity<ActivitySelectCategoryBinding>(ActivitySelectCategoryBinding::inflate) {
    private lateinit var selectCategoryActivityViewModel:SelectCategoryActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뷰모델 장착
        selectCategoryActivityViewModel=ViewModelProvider(this).get(SelectCategoryActivityViewModel::class.java)

        //학생지원-전체 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectStudentBtnAll.observe(this, Observer {
            if (it){
                binding.btnStudent1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnStudent1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnStudent1.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnStudent1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnStudent1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnStudent1.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //학생지원-교내장학금 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectStudentBtnInSchoolTuition.observe(this, Observer {
            if (it){
                binding.btnStudent2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnStudent2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnStudent2.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnStudent2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnStudent2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnStudent2.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //학생지원-교외장학금 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectStudentBtnOutSchoolTuition.observe(this, Observer {
            if (it){
                binding.btnStudent3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnStudent3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnStudent3.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnStudent3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnStudent3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnStudent3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })


        //취업지원-전체체 상태 변경하 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectJobBtnAll.observe(this, Observer {
            if (it){
                binding.btnJob1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnJob1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnJob1.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnJob1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnJob1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnJob1.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //취업지원-구직활동지원인턴 상태 변경하 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectJobBtnJobSearch.observe(this, Observer {
            if (it){
                binding.btnJob2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnJob2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnJob2.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnJob2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnJob2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnJob2.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //취업지원-중소중견기업취업지원 상태 변경하 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectJobBtnSmallBusiness.observe(this, Observer {
            if (it){
                binding.btnJob3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnJob3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnJob3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnJob3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnJob3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnJob3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //취업지원-특수분야취업지원 상태 변경하 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectJobBtnSpecialField.observe(this, Observer {
            if (it){
                binding.btnJob4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnJob4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnJob4.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnJob4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnJob4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnJob4.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //취업지원-해외 취업 및 진출 지원 상태 변경하 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectJobBtnOverSeas.observe(this, Observer {
            if (it){
                binding.btnJob5.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnJob5.setTextColor(Color.parseColor("#ffffff"))
                binding.btnJob5.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnJob5.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnJob5.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnJob5.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })



        //창업지원-전체 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectFoundationBtnAll.observe(this, Observer {
            if (it){
                binding.btnFoundation1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnFoundation1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnFoundation1.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnFoundation1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnFoundation1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnFoundation1.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //창업지원-창업운영 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectFoundationBtnFoundationOperate.observe(this, Observer {
            if (it){
                binding.btnFoundation2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnFoundation2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnFoundation2.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnFoundation2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnFoundation2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnFoundation2.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //창업지원-경영지원 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectFoundationBtnManagement.observe(this, Observer {
            if (it){
                binding.btnFoundation3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnFoundation3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnFoundation3.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnFoundation3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnFoundation3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnFoundation3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //창업지원-학자금지원 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectFoundationBtnCapital.observe(this, Observer {
            if (it){
                binding.btnFoundation4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnFoundation4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnFoundation4.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnFoundation4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnFoundation4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnFoundation4.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })


        //주거금융지원-전체 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectDwellingBtnAll.observe(this, Observer {
            if (it){
                binding.btnDwelling1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnDwelling1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnDwelling1.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnDwelling1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnDwelling1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnDwelling1.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //주거금융지원-전체 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectDwellingBtnLivingExpense.observe(this, Observer {
            if (it){
                binding.btnDwelling2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnDwelling2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnDwelling2.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnDwelling2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnDwelling2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnDwelling2.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //주거금융지원-주거지원 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectDwellingBtnDwelling.observe(this, Observer {
            if (it){
                binding.btnDwelling3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnDwelling3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnDwelling3.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnDwelling3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnDwelling3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnDwelling3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //주거금융지원-학자금 지원 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectDwellingBtnSchoolExpense.observe(this, Observer {
            if (it){
                binding.btnDwelling4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnDwelling4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnDwelling4.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnDwelling4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnDwelling4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnDwelling4.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })



        //생활복지지원-전체 지원 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectLifeBtnAll.observe(this, Observer {
            if (it){
                binding.btnLife1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLife1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLife1.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnLife1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLife1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLife1.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //생활복지지원-건강 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectLifeBtnHealth.observe(this, Observer {
            if (it){
                binding.btnLife2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLife2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLife2.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnLife2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLife2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLife2.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //생활복지지원-문화 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectLifeBtnCulture.observe(this, Observer {
            if (it){
                binding.btnLife3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnLife3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnLife3.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnLife3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnLife3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnLife3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })


        //코로나19-전체 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectCoronaBtnAll.observe(this, Observer {
            if (it){
                binding.btnCorona1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnCorona1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnCorona1.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnCorona1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnCorona1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnCorona1.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //코로나19-기본소득 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectCoronaBtnBasicIncome.observe(this, Observer {
            if (it){
                binding.btnCorona2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnCorona2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnCorona2.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnCorona2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnCorona2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnCorona2.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        }) //코로나19-저소득층지원 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectCoronaBtnLowIncome.observe(this, Observer {
            if (it){
                binding.btnCorona3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnCorona3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnCorona3.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnCorona3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnCorona3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnCorona3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        }) //코로나19-재난피해지원 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectCoronaBtnDisasterDamage.observe(this, Observer {
            if (it){
                binding.btnCorona4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnCorona4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnCorona4.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnCorona4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnCorona4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnCorona4.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        }) //코로나19-소득일자리보전 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectCoronaBtnJobPreservation.observe(this, Observer {
            if (it){
                binding.btnCorona5.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnCorona5.setTextColor(Color.parseColor("#ffffff"))
                binding.btnCorona5.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnCorona5.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnCorona5.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnCorona5.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        }) //코로나19-기타인센티브 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectCoronaBtnIncentive.observe(this, Observer {
            if (it){
                binding.btnCorona6.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnCorona6.setTextColor(Color.parseColor("#ffffff"))
                binding.btnCorona6.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnCorona6.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnCorona6.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnCorona6.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        }) //코로나19-전체 상태 변경하기 위해 라이브데이터 관찰
        selectCategoryActivityViewModel.isSelectCoronaBtnPsychologicalSupport.observe(this, Observer {
            if (it){
                binding.btnCorona7.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnCorona7.setTextColor(Color.parseColor("#ffffff"))
                binding.btnCorona7.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnCorona7.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnCorona7.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnCorona7.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })




        //학생 지원-전체 클릭 리스너
        binding.btnStudent1.setOnClickListener {
            selectCategoryActivityViewModel.changeStudentBtnAll()
        }
        //학생 지원-교내 장학금 클릭 리스너
        binding.btnStudent2.setOnClickListener {
            selectCategoryActivityViewModel.changeStudentBtnInSchoolTuition()
        }
        //학생 지원-교외 장학금 클릭 리스너
        binding.btnStudent3.setOnClickListener {
            selectCategoryActivityViewModel.changeStudentBtnOutSchoolTuition()
        }


        //취업 지원-전체 클릭 리스너
        binding.btnJob1.setOnClickListener {
            selectCategoryActivityViewModel.changeJobBtnAll()
        }
        //취업 지원-구직활동지원인턴 클릭 리스너
        binding.btnJob2.setOnClickListener {
            selectCategoryActivityViewModel.changeJobBtnJobSearch()
        }
        //취업 지원-중소중견기업취업지원 클릭 리스너
        binding.btnJob3.setOnClickListener {
            selectCategoryActivityViewModel.changeJobBtnJobSmallBusiness()
        }
        //취업 지원-특수분야취업지원 클릭 리스너
        binding.btnJob4.setOnClickListener {
            selectCategoryActivityViewModel.changeJobBtnJobSpecialField()
        }
        //취업 지원-해외취업및진출지원 클릭 리스너
        binding.btnJob5.setOnClickListener {
            selectCategoryActivityViewModel.changeJobBtnJobOverSeas()
        }



        //창업 지원-전체 클릭 리스너
        binding.btnFoundation1.setOnClickListener {
            selectCategoryActivityViewModel.changeFoundationBtnAll()
        }
        //창업 지원-창업운영지원 클릭 리스너
        binding.btnFoundation2.setOnClickListener {
            selectCategoryActivityViewModel.changeFoundationBtnFoundationOperate()
        }//창업 지원-경영지원 클릭 리스너
        binding.btnFoundation3.setOnClickListener {
            selectCategoryActivityViewModel.changeFoundationBtnManagement()
        }//창업 지원-자본금지원 클릭 리스너
        binding.btnFoundation4.setOnClickListener {
            selectCategoryActivityViewModel.changeFoundationBtnCapital()
        }


        //주거금융 지원-전체 클릭 리스너
        binding.btnDwelling1.setOnClickListener {
            selectCategoryActivityViewModel.changeDwellingBtnAll()
        }
        //주거금융 지원-생활비지원 클릭 리스너
        binding.btnDwelling2.setOnClickListener {
            selectCategoryActivityViewModel.changeDwellingBtnLivingExpense()
        }
        //주거금융 지원-주거지원 클릭 리스너
        binding.btnDwelling3.setOnClickListener {
            selectCategoryActivityViewModel.changeDwellingBtnDwelling()
        }
        //주거금융 지원-학자금지원 클릭 리스너
        binding.btnDwelling4.setOnClickListener {
            selectCategoryActivityViewModel.changeDwellingBtnSchoolExpense()
        }



        //생활복지 지원-전체 클릭 리스너
        binding.btnLife1.setOnClickListener {
            selectCategoryActivityViewModel.changeLifeBtnAll()
        }
        //생활복지 지원-건강 클릭 리스너
        binding.btnLife2.setOnClickListener {
            selectCategoryActivityViewModel.changeLifeBtnHealth()
        }
        //생활복지 지원-문화 클릭 리스너
        binding.btnLife3.setOnClickListener {
            selectCategoryActivityViewModel.changeLifeBtnCulture()
        }



        //코로나19지원-전체 클릭 리스너
        binding.btnCorona1.setOnClickListener {
            selectCategoryActivityViewModel.changeCoronaBtnAll()
        }
        //코로나19지원-기본소득지원 클릭 리스너
        binding.btnCorona2.setOnClickListener {
            selectCategoryActivityViewModel.changeCoronaBtnBasicIncome()
        }//코로나19지원-저소득층지원 클릭 리스너
        binding.btnCorona3.setOnClickListener {
            selectCategoryActivityViewModel.changeCoronaBtnLowIncome()
        }//코로나19지원-재난피해지원 클릭 리스너
        binding.btnCorona4.setOnClickListener {
            selectCategoryActivityViewModel.changeCoronaBtnDisasterDamage()
        }//코로나19지원-소득일자리보전 클릭 리스너
        binding.btnCorona5.setOnClickListener {
            selectCategoryActivityViewModel.changeCoronaBtnJobPreservation()
        }//코로나19지원-기타 인센티브 클릭 리스너
        binding.btnCorona6.setOnClickListener {
            selectCategoryActivityViewModel.changeCoronaBtnIncentive()
        }//코로나19지원-심리지원 클릭 리스너
        binding.btnCorona7.setOnClickListener {
            selectCategoryActivityViewModel.changeCoronaBtnPsychologicalSupport()
        }
    }
}