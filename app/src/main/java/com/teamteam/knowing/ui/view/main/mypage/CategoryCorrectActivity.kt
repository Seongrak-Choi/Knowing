package com.teamteam.knowing.ui.view.main.mypage

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.databinding.ActivitySelectCategoryBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.viewmodel.CategoryCorrectActivityViewModel

class CategoryCorrectActivity : BaseActivity<ActivitySelectCategoryBinding>(ActivitySelectCategoryBinding::inflate) {
    private lateinit var categoryCorrectActivityViewModel:CategoryCorrectActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //뷰모델 장착
        categoryCorrectActivityViewModel=ViewModelProvider(this).get(CategoryCorrectActivityViewModel::class.java)

        //학생지원-전체 상태 변경하기 위해 라이브데이터 관찰
        categoryCorrectActivityViewModel.isSelectStudentBtnAll.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectStudentBtnInSchoolTuition.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectStudentBtnOutSchoolTuition.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectJobBtnAll.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectJobBtnJobSearch.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectJobBtnSmallBusiness.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectJobBtnSpecialField.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectJobBtnOverSeas.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectFoundationBtnAll.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectFoundationBtnFoundationOperate.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectFoundationBtnManagement.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectFoundationBtnCapital.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectDwellingBtnAll.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectDwellingBtnLivingExpense.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectDwellingBtnDwelling.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectDwellingBtnSchoolExpense.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectLifeBtnAll.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectLifeBtnHealth.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectLifeBtnCulture.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectCoronaBtnAll.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectCoronaBtnBasicIncome.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectCoronaBtnLowIncome.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectCoronaBtnDisasterDamage.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectCoronaBtnJobPreservation.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectCoronaBtnIncentive.observe(this, Observer {
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
        categoryCorrectActivityViewModel.isSelectCoronaBtnPsychologicalSupport.observe(this, Observer {
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

        //적용하기 버튼 상태 변경하기 위해 라이브데이터 관찰
        categoryCorrectActivityViewModel.isCorrectBtnNext.observe(this, Observer {
            binding.btnNext.isEnabled=it
        })


        //api 성공 여부 보기 위해 라이브데이터 관찰
        categoryCorrectActivityViewModel.isSuccessApi.observe(this, Observer {
            if (it){
                Toast.makeText(this,"카테고리가 수정 되었습니다.",Toast.LENGTH_SHORT).show()
                this.finish()
            }

            else
                Toast.makeText(this,"카테고리 수정이 실패하였습니다.",Toast.LENGTH_SHORT).show()
        })




        //학생 지원-전체 클릭 리스너
        binding.btnStudent1.setOnClickListener {
            categoryCorrectActivityViewModel.changeStudentBtnAll()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //학생 지원-교내 장학금 클릭 리스너
        binding.btnStudent2.setOnClickListener {
            categoryCorrectActivityViewModel.changeStudentBtnInSchoolTuition()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //학생 지원-교외 장학금 클릭 리스너
        binding.btnStudent3.setOnClickListener {
            categoryCorrectActivityViewModel.changeStudentBtnOutSchoolTuition()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }


        //취업 지원-전체 클릭 리스너
        binding.btnJob1.setOnClickListener {
            categoryCorrectActivityViewModel.changeJobBtnAll()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //취업 지원-구직활동지원인턴 클릭 리스너
        binding.btnJob2.setOnClickListener {
            categoryCorrectActivityViewModel.changeJobBtnJobSearch()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //취업 지원-중소중견기업취업지원 클릭 리스너
        binding.btnJob3.setOnClickListener {
            categoryCorrectActivityViewModel.changeJobBtnJobSmallBusiness()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //취업 지원-특수분야취업지원 클릭 리스너
        binding.btnJob4.setOnClickListener {
            categoryCorrectActivityViewModel.changeJobBtnJobSpecialField()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //취업 지원-해외취업및진출지원 클릭 리스너
        binding.btnJob5.setOnClickListener {
            categoryCorrectActivityViewModel.changeJobBtnJobOverSeas()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }



        //창업 지원-전체 클릭 리스너
        binding.btnFoundation1.setOnClickListener {
            categoryCorrectActivityViewModel.changeFoundationBtnAll()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //창업 지원-창업운영지원 클릭 리스너
        binding.btnFoundation2.setOnClickListener {
            categoryCorrectActivityViewModel.changeFoundationBtnFoundationOperate()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }//창업 지원-경영지원 클릭 리스너
        binding.btnFoundation3.setOnClickListener {
            categoryCorrectActivityViewModel.changeFoundationBtnManagement()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }//창업 지원-자본금지원 클릭 리스너
        binding.btnFoundation4.setOnClickListener {
            categoryCorrectActivityViewModel.changeFoundationBtnCapital()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }


        //주거금융 지원-전체 클릭 리스너
        binding.btnDwelling1.setOnClickListener {
            categoryCorrectActivityViewModel.changeDwellingBtnAll()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //주거금융 지원-생활비지원 클릭 리스너
        binding.btnDwelling2.setOnClickListener {
            categoryCorrectActivityViewModel.changeDwellingBtnLivingExpense()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //주거금융 지원-주거지원 클릭 리스너
        binding.btnDwelling3.setOnClickListener {
            categoryCorrectActivityViewModel.changeDwellingBtnDwelling()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //주거금융 지원-학자금지원 클릭 리스너
        binding.btnDwelling4.setOnClickListener {
            categoryCorrectActivityViewModel.changeDwellingBtnSchoolExpense()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }



        //생활복지 지원-전체 클릭 리스너
        binding.btnLife1.setOnClickListener {
            categoryCorrectActivityViewModel.changeLifeBtnAll()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //생활복지 지원-건강 클릭 리스너
        binding.btnLife2.setOnClickListener {
            categoryCorrectActivityViewModel.changeLifeBtnHealth()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //생활복지 지원-문화 클릭 리스너
        binding.btnLife3.setOnClickListener {
            categoryCorrectActivityViewModel.changeLifeBtnCulture()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }



        //코로나19지원-전체 클릭 리스너
        binding.btnCorona1.setOnClickListener {
            categoryCorrectActivityViewModel.changeCoronaBtnAll()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }
        //코로나19지원-기본소득지원 클릭 리스너
        binding.btnCorona2.setOnClickListener {
            categoryCorrectActivityViewModel.changeCoronaBtnBasicIncome()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }//코로나19지원-저소득층지원 클릭 리스너
        binding.btnCorona3.setOnClickListener {
            categoryCorrectActivityViewModel.changeCoronaBtnLowIncome()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }//코로나19지원-재난피해지원 클릭 리스너
        binding.btnCorona4.setOnClickListener {
            categoryCorrectActivityViewModel.changeCoronaBtnDisasterDamage()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }//코로나19지원-소득일자리보전 클릭 리스너
        binding.btnCorona5.setOnClickListener {
            categoryCorrectActivityViewModel.changeCoronaBtnJobPreservation()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }//코로나19지원-기타 인센티브 클릭 리스너
        binding.btnCorona6.setOnClickListener {
            categoryCorrectActivityViewModel.changeCoronaBtnIncentive()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }//코로나19지원-심리지원 클릭 리스너
        binding.btnCorona7.setOnClickListener {
            categoryCorrectActivityViewModel.changeCoronaBtnPsychologicalSupport()
            //적용하기 버튼 상태 업데이트 하기 위한 메소드 호출
            categoryCorrectActivityViewModel.checkIsCorrectBtnNext()
        }


        //뒤로가기 아이콘 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }

        //적용하기 버튼 클릭 리스너
        binding.btnNext.setOnClickListener {
            categoryCorrectActivityViewModel.tryPostCategoryCorrect(ApplicationClass.USER_UID)
        }
    }
}