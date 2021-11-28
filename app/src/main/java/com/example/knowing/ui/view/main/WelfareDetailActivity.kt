package com.example.knowing.ui.view.main

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.net.ParseException
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knowing.R
import com.example.knowing.data.model.network.response.WelfareInfo
import com.example.knowing.databinding.ActivityWelfareDetailBinding
import com.example.knowing.ui.adapter.WelfareDetailBenefitsRCAdapter
import com.example.knowing.ui.adapter.WelfareDetailCategoryRCAdapter
import com.example.knowing.ui.adapter.WelfareDetailDocumentRCAdapter
import com.example.knowing.ui.adapter.WelfareDetailJudgeRCAdapter
import com.example.knowing.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class WelfareDetailActivity:BaseActivity<ActivityWelfareDetailBinding>(ActivityWelfareDetailBinding::inflate) {


    //dp를 px로 계산하기 위해 디스플에이의 dip를 저장할 변수
    var density = 0f

    //dp48의 px값을 저장할 변수
    var dp48 = 0
    //display 메트릭스 인스턴스
    private val displayMetrics = DisplayMetrics()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //전달 받은 welfareInfo 데이터 저장
        val welfareInfo = intent.getSerializableExtra("welfareInfo") as WelfareInfo

        //디스플레의 dip를 계산하기위해 메트릭스 객체에 디바이스 메트릭스 저장. dp를 px로 계산하기 위해 필요
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)

        //dp를 px로 계산하기 위한 변수
        density = displayMetrics.density

        //dp48을 px로 변환해서 저장
        dp48 = (48*density+0.5).toInt()



        //주소지 설정
        binding.txAddress.text=welfareInfo.address

        //복지 이름 설정
        binding.txName.text=welfareInfo.name

        //복지 요약 설정
        binding.txTitle.text=welfareInfo.title

        //지원형태 설정
        binding.txServiceType.text=welfareInfo.serviceType

        //d_day계산
        if (welfareInfo.applyDate!="연중상시" && welfareInfo.applyDate.isNotEmpty()){
            val deadLineYearMonthDay = welfareInfo.applyDate.split("~")[1] //년,월,일을 쪼갬
            val deadLine = deadLineYearMonthDay.split(".")
            //디데이 설정
            binding.txDDay.text=getDDay(deadLine[0].toInt(),deadLine[1].toInt(),deadLine[2].toInt())
        }else{
            binding.txDDay.text="연중상시"
        }

        //선정인원 설정
        binding.txPeople.text=welfareInfo.scale


        //최대 금액 설정
        if (welfareInfo.maxMoney!="0"){
            //string형을 숫자처럼 ,찍는다.
            val myFormatter = DecimalFormat("###,###")
            binding.txMaxMoney.text=myFormatter.format(welfareInfo.maxMoney.toInt())
            binding.txMaxMoneyWon.visibility=View.VISIBLE
        }else{
            binding.txMaxMoney.text="-"
            binding.txMaxMoneyWon.visibility=View.INVISIBLE
        }


        //최소 금액 설정
        if (welfareInfo.minMoney=="0"){
            binding.txMinMoney.text="-"
            binding.txMinMoneyWon.visibility=View.INVISIBLE
        }else if(welfareInfo.minMoney=="조건별상이") {
            binding.txMinMoney.text="조건별 상이"
        }else
        {
            //string형을 숫자처럼 ,찍는다.
            val myFormatter = DecimalFormat("###,###")
            binding.txMinMoney.text=myFormatter.format(welfareInfo.minMoney.toInt())
            binding.txMinMoneyWon.visibility=View.VISIBLE
        }


        //카테고리 관련 string 공백으로 나눠서 배열로 저장
        var categoryList = welfareInfo.category.split(" ")
        //카테고리 출력하는 리사이클러뷰 어댑터 장착
        binding.rcCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rcCategory.adapter = WelfareDetailCategoryRCAdapter(categoryList)


        //어떤 헤택을 받을 수 있나요? 리사이클러뷰 데이터 셋팅을 위해 split으로 데이터 마이닝
        val benefitsBigSubjectList = welfareInfo.content.split("@")
        //어떤 혜택을 받을 수 있나요? 어댑터 설정
        binding.rcBenefits.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcBenefits.adapter = WelfareDetailBenefitsRCAdapter(benefitsBigSubjectList, this)



        //참여 제한 대상 리사이클러뷰 데이터 셋팅을 위해 split으로 데이터 마이닝
        val limitBigSubjectList = welfareInfo.joinLimit.split("@")
        //참여 제한 대상 어댑터 설정
        binding.rcLimite.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcLimite.adapter = WelfareDetailBenefitsRCAdapter(limitBigSubjectList, this)


        //신청 자격 요약 더보기 버튼 클릭 리스너
        binding.btnMoreInfo.setOnClickListener {
            if (binding.linearApplication.visibility == View.GONE) { //신청 자격 요약 리니어가 GONE상태라면
                //신청 자격 요약 리니어 보여지게
                binding.linearApplication.visibility = View.VISIBLE
                binding.btnMoreInfo.setImageResource(R.drawable.ic_arrow_close)
            } else {//신청 자격 요약 리니어가 VISIBLE상태라면
                binding.linearApplication.visibility = View.GONE
                binding.btnMoreInfo.setImageResource(R.drawable.ic_arrow_open)
            }
        }

       // binding.txAge.text=

        //심사 및 발표
        val judgeBigSubjectList = welfareInfo.applyMethod.split("@")
        binding.rcJudge.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcJudge.adapter = WelfareDetailJudgeRCAdapter(judgeBigSubjectList)


        //제출 서류
        val documentBigSubjectList = welfareInfo.document.split("@")
        binding.rcDocument.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcDocument.adapter = WelfareDetailDocumentRCAdapter(documentBigSubjectList)

        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.finish()
        }

        //stickscrollview
        binding.stickyScroll.run {
            header = binding.tabLayout
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
                binding.stickyScroll.elevation=11f
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }


        //헤더에 붙는 tabLayout 아이템 클릭 리스너
        binding.tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.stickyScroll.elevation=11f

                //tabLayout이 선택되면 무조건 appbar는 축소되도록 설정
                binding.appBar.setExpanded(false)

                //tabLayout 높이 만큼 빼고(54dp) bar의 높이(6dp)를 빼서 48dp만큼 y값을 더 추가해 정확한 위치를 맞추기 위함
                when(tab!!.position){
                    0-> binding.stickyScroll.smoothScrollTo(binding.stickyScroll.scrollX,0) //48dp를 픽셀로 계산해서 y값을 조정해줌
                    1-> binding.stickyScroll.smoothScrollTo(binding.stickyScroll.scrollX,binding.line3.y.toInt()-dp48)
                    2-> binding.stickyScroll.smoothScrollTo(binding.stickyScroll.scrollX,binding.line6.y.toInt()-dp48)
                    3-> {
                        binding.stickyScroll.smoothScrollTo(binding.stickyScroll.scrollX,binding.line10.y.toInt()-dp48)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        //스크롤뷰 리스너
        binding.stickyScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            //스크롤 위치가 고정된 tabLayout밑에 부터 시작하는데 line들은 tabLayout을 포함하지 않고 최상단 부터 y값을 계산해서 tabLayout높이 만큼 스크롤의 위치를 더해서 line의 y값과 맞춰줌
            if (scrollY>=0 && scrollY+dp48<binding.line3.y.toInt()){
                binding.tabLayout.setScrollPosition(0,0f,true)
            }else if (scrollY+dp48>=binding.line3.y.toInt()&& scrollY+dp48<binding.line6.y.toInt()){
                binding.tabLayout.setScrollPosition(1,0f,true)
            }else if (scrollY+dp48>=binding.line6.y.toInt() && scrollY+dp48<binding.line10.y.toInt()){
                binding.tabLayout.setScrollPosition(2,0f,true)
            }else if (scrollY+dp48>=binding.line10.y.toInt()) {
                binding.tabLayout.setScrollPosition(3, 0f, true)
            }
        }
    }

    /*
   dday 구하는 메소드
    */
    private fun getDDay(dayYear:Int,dayMonth:Int,dayDay:Int):String{
        Locale.setDefault(Locale.KOREAN);

        var welfareMonth = dayMonth-1
        var welfareYear = dayYear
        var welfareDay = dayDay

        try {
            val format = SimpleDateFormat("yyyy-mm-dd")
            //지금 시간 가져오기
            val currentDate = Calendar.getInstance()
            val cYear = currentDate.get(Calendar.YEAR)
            val cMonth = currentDate.get(Calendar.MONTH)
            val cDay = currentDate.get(Calendar.DAY_OF_MONTH)
            currentDate.set(cYear,cMonth,cDay) //오늘 날짜에 세팅

            val welfareDate = Calendar.getInstance()
            welfareDate.set(welfareYear,welfareMonth,welfareDay)//복지 날짜 형식에 맞게 세팅

            val currentDay = currentDate.timeInMillis/86400000 //계산을 위해 밀리세컨즈로 변경 후 나누기
            val dday = welfareDate.timeInMillis/86400000 //계산을 위해 밀리세컨즈로 변경 후 나누기
            val count = dday - currentDay //오늘 날짜에서 dday날짜를 빼준다.

            if (count.toInt()>0)
                return "D-${count.toInt()}"
            else if (count.toInt()<0)
                return "D+${count.toInt()*-1}"
            else
                return "D-Day"
        }catch (e: ParseException){
            Log.e("ERROR","날짜 변화 중 형식이 맞지 않아 오류 발생")
            return "날짜 형식이 올바르지 않습니다"
        }
    }
}