package com.teamteam.knowing.ui.view.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.net.ParseException
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_UID
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import com.teamteam.knowing.databinding.ActivityWelfareDetailBinding
import com.teamteam.knowing.ui.adapter.WelfareDetailBenefitsRCAdapter
import com.teamteam.knowing.ui.adapter.WelfareDetailCategoryRCAdapter
import com.teamteam.knowing.ui.adapter.WelfareDetailDocumentRCAdapter
import com.teamteam.knowing.ui.adapter.WelfareDetailJudgeRCAdapter
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.viewmodel.WelfareDetailActivityViewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class WelfareDetailActivity :
    BaseActivity<ActivityWelfareDetailBinding>(ActivityWelfareDetailBinding::inflate) {

    //뷰모델 저장할 변수
    private lateinit var welfareDetailActivityViewModel: WelfareDetailActivityViewModel

    //tabLayout을 눌러서 스크롤 되는 동안에는 스크롤뷰 리스너가 동작하지 않도록 상태를 저장하는 변수
    private var isUserScrolling = false

    //스크롤의 마지막 부분에 닿을 때 계속 '기타' 메뉴가 선택되는걸 막기 위함함
   private var endPosition = false


    //appBar높이 저장할 변수
    private var appBarHeight: Int = 0

    //최하단 기타 정보까지의 높이를 저장할 변수
    private var etcPartHeight = 0

    //dp를 px로 계산하기 위해 디스플에이의 dip를 저장할 변수
    var density = 0f

    //dp48의 px값을 저장할 변수
    var dp48 = 0

    //display 메트릭스 인스턴스
    private val displayMetrics = DisplayMetrics()


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //전달 받은 welfareInfo 데이터 저장
        val welfareInfo = intent.getSerializableExtra("welfareInfo") as WelfareInfo

        //뷰모델 장착
        welfareDetailActivityViewModel =
            ViewModelProvider(this).get(WelfareDetailActivityViewModel::class.java)


        //해당 복지가 북마크에 등록이 되어 있는지 아닌지 확인하는 api 호출
        welfareDetailActivityViewModel.tryGetIsWelfareApplyToBookmark(
            USER_UID,
            welfareInfo.uid
        )

        //해당 복지가 알림에 등록 되어 있는지 아닌지 확인하는 api 호출
        welfareDetailActivityViewModel.tryGetIsWelfareApplyToAlarm(USER_UID,welfareInfo.uid)


        //디스플레의 dip를 계산하기위해 메트릭스 객체에 디바이스 메트릭스 저장. dp를 px로 계산하기 위해 필요
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        //dp를 px로 계산하기 위한 변수
        density = displayMetrics.density
        //dp48을 px로 변환해서 저장
        dp48 = (48 * density + 0.5).toInt()



        //이 복지가 북마크에 추가 되어 있는지 확인과 북마크 추가,삭제 api 호출 시 버튼의 색상을 변경하기 위해 라이브데이터 관찰
        welfareDetailActivityViewModel.currentBookmarkWhether.observe(
            this,
            androidx.lifecycle.Observer {
                if (it) {//북마크가 추가 되어 있을 경우 오랜지 색
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_orange_s)
                } else {//북마크가 삭제 되었을 경우 회색
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_grey_s)
                }
            })


        //이 복지가 알림 설정 되어 있는지 확인과 알림 설정,해제 api 호출 시 버튼의 색상을 변경하기 위해 라이브데이터 관찰
        welfareDetailActivityViewModel.currentAlarmWhether.observe(this, androidx.lifecycle.Observer {
            if (it){//알림 설정 되어 있는 경우 오렌지 색
                binding.btnAlarm.setImageResource(R.drawable.ic_notice_color)
            }else{//알림 해제 되었을 경우 회색
                binding.btnAlarm.setImageResource(R.drawable.ic_notice)
            }
        })


        //주소지 설정
        binding.txAddress.text = welfareInfo.address

        //복지 이름 설정
        binding.txName.text = welfareInfo.name

        //복지 요약 설정
        binding.txTitle.text = welfareInfo.title

        //지원형태 설정
        binding.txServiceType.text = welfareInfo.serviceType

        //d_day계산
        if (welfareInfo.applyDate != "연중상시" && welfareInfo.applyDate != "별도공지" && welfareInfo.applyDate.isNotEmpty()) {
            val deadLineYearMonthDay = welfareInfo.applyDate.split("~")[1] //년,월,일을 쪼갬
            val deadLine = deadLineYearMonthDay.split(".")
            //디데이 설정
            binding.txDDay.text =
                getDDay(deadLine[0].toInt(), deadLine[1].toInt(), deadLine[2].toInt())
        } else if (welfareInfo.applyDate == "연중상시") {
            binding.txDDay.text = "연중 상시"
        } else {
            binding.txDDay.text = "별도 공지"
        }

        //선정인원 설정
        binding.txPeople.text = welfareInfo.scale


        //최대 금액 설정
        if (welfareInfo.maxMoney != "0") {
            //string형을 숫자처럼 ,찍는다.
            val myFormatter = DecimalFormat("###,###")
            binding.txMaxMoney.text = myFormatter.format(welfareInfo.maxMoney.toInt())
            binding.txMaxMoneyWon.visibility = View.VISIBLE
        } else {
            binding.txMaxMoney.text = "-"
            binding.txMaxMoneyWon.visibility = View.INVISIBLE
        }


        //최소 금액 설정
        if (welfareInfo.minMoney == "0") {
            binding.txMinMoney.text = "-"
            binding.txMinMoneyWon.visibility = View.INVISIBLE
        } else if (welfareInfo.minMoney == "조건별상이") {
            binding.txMinMoney.text = "조건별 상이"
        } else {
            //string형을 숫자처럼 ,찍는다.
            val myFormatter = DecimalFormat("###,###")
            binding.txMinMoney.text = myFormatter.format(welfareInfo.minMoney.toInt())
            binding.txMinMoneyWon.visibility = View.VISIBLE
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

        //신청 자격 요약 중 나이 설정
        var ageFinal = ""
        //형식이 제한없음인지 아닌지 판단
        if (welfareInfo.age != "제한없음") {
            val ageMining = welfareInfo.age.split("~")//제한없음이 아니라면 스플릿
            if (ageMining[0] == "0") { //첫 요소가 0이면 미만이라는 뜻
                binding.txAge.text = "만${ageMining[1]}세 미만"
            } else if (ageMining[1] == "200") { //두번째 요소가 200이면 미만이라는 뜻
                binding.txAge.text = "만${ageMining[0]}세 이상"
            } else {//둘다 아니라면 범위라는 뜻
                binding.txAge.text = "만${ageMining[0]}세~만${ageMining[1]}세"
            }
        } else {
            binding.txAge.text = "제한없음"
        }

        //신청 자격 요약 중 거주지 설정
        val addressMining = welfareInfo.address.split(" ")
        if (addressMining[0] in "서울 부산 대구 인천 광주 대전 울산 경기 강원 충북 충남 전북 전남 경북 경남 제주 세종") {
            binding.txFilterAddress.text = welfareInfo.address
        } else {
            binding.txFilterAddress.text = "제한없음"
        }

        //신청 자격 요약 중 소득 설정
        if (welfareInfo.incomeLevel != "제한없음") {
            if (welfareInfo.incomeLevel.toInt() > 9) {
                binding.txIncome.text = "중위소득${welfareInfo.incomeLevel}% 이하"
            } else
                binding.txIncome.text = "소득분위 ${welfareInfo.incomeLevel}구간 이하"
        } else
            binding.txIncome.text = "제한없음"

        //신청 자격 요약 중 학력 설정
        binding.txSchoolRecords.text = welfareInfo.schoolRecords

        //신청 자격 요약 중 전공 설정
        binding.txMajor.text = "제한없음"

        //신청 자격 요약 중 취업 상태 설정
        binding.txEmployState.text = welfareInfo.employmentState

        //신청 자격 요약 중 특화 분야 설정
        binding.txSpecialStatus.text = welfareInfo.specialStatus

        //신청 시작일 설정
        if (welfareInfo.applyDate != "연중상시" && welfareInfo.applyDate != "별도공지" && welfareInfo.applyDate.isNotEmpty()) {
            val startYearMonthDay = welfareInfo.applyDate.split("~")[0] //년,월,일을 쪼갬
            binding.txApplicationStart.text = startYearMonthDay
        } else if (welfareInfo.applyDate == "연중상시") {
            binding.txApplicationStart.text = "-"
        } else {
            binding.txApplicationStart.text = "-"
        }


        //신청 마감일 설정
        if (welfareInfo.applyDate != "연중상시" && welfareInfo.applyDate != "별도공지" && welfareInfo.applyDate.isNotEmpty()) {
            val deadLineYearMonthDay = welfareInfo.applyDate.split("~")[1] //년,월,일을 쪼갬
            binding.txApplicationEnd.text = deadLineYearMonthDay
        } else if (welfareInfo.applyDate == "연중상시") {
            binding.txApplicationEnd.text = "연중 상시"
        } else {
            binding.txApplicationEnd.text = "별도 공지"
        }


        //복지 운영 기간
        if (welfareInfo.runDate != "연중상시" && welfareInfo.runDate != "별도공지" && welfareInfo.runDate.isNotEmpty()) {
            binding.txApplicationRunDay.text = welfareInfo.runDate
        } else if (welfareInfo.applyDate == "연중상시") {
            binding.txApplicationRunDay.text = "연중 상시"
        } else {
            binding.txApplicationRunDay.text = "별도 공지"
        }

        //신청 방법 설정
        if (welfareInfo.applyMethod.split("/").size > 1) {
            binding.linearVisitApplication.visibility = View.VISIBLE
        } else if (welfareInfo.applyMethod == "방문신청") {
            binding.linearVisitApplication.visibility = View.VISIBLE
            binding.linearOnlineApplication.visibility = View.INVISIBLE
        } else {
            binding.linearVisitApplication.visibility = View.GONE
            binding.linearOnlineApplication.visibility = View.VISIBLE
        }


        //자격 상세 조건 리사이클러뷰 데이터 셋팅을 위해 split으로 데이터 마이닝
        val detailTermsList = welfareInfo.detailTerms.split("@")
        //자격 상세 조건 리사이클러뷰 데이터 셋팅
        binding.rcDetailTerms.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcDetailTerms.adapter = WelfareDetailBenefitsRCAdapter(detailTermsList, this)


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


        //심사 및 발표
        val judgeBigSubjectList = welfareInfo.judge.split("@")
        binding.rcJudge.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcJudge.adapter = WelfareDetailJudgeRCAdapter(judgeBigSubjectList)


        //제출 서류
        val documentBigSubjectList = welfareInfo.document.split("@")
        binding.rcDocument.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcDocument.adapter = WelfareDetailDocumentRCAdapter(documentBigSubjectList)


        //문의 전화 설정
        if (welfareInfo.phNum != "없음")
            binding.txEtcPhNum.text = welfareInfo.phNum
        else
            binding.txEtcPhNum.text = "-"


        //운영 기관 설정
        if (welfareInfo.manageOffice != "없음")
            binding.txEtcManageOffice.text = welfareInfo.manageOffice
        else
            binding.txEtcManageOffice.text = "-"


        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.finish()
        }

        //stickscrollview
        binding.stickyScroll.run {
            //tab Layout이 붙도록 설정
            header = binding.tabLayout
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
                binding.stickyScroll.elevation = 11f
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }

        //북마크 버튼 클릭 리스너
        binding.btnBookmark.setOnClickListener {
            //버튼을 눌러 해당 복지를 서버 북마크에 추가하고 삭제하기 위한 api 호출
            welfareDetailActivityViewModel.tryPostBookmark(
                USER_UID,
                welfareInfo.uid
            )
        }

        //알림 버튼 클릭 리스너
        binding.btnAlarm.setOnClickListener {
            //버튼을 눌러 해당 복지 알림 설정,해제 api 호출
            welfareDetailActivityViewModel.tryPostChangeAlarm(USER_UID,welfareInfo.uid)
        }


        //tabLayout의 tab들에게 클릭 리스너 설정하는 코드
        for (i in 0 until binding.tabLayout.getTabCount()) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            tab.setOnClickListener {
                //tablayout을 클릭해서 scroll이 되는 경우 nestedScrollview의 scroll 리스너가 동작되지 않게 함
                isUserScrolling=false
            }
        }

        //헤더에 붙는 tabLayout 아이템 클릭 리스너
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.stickyScroll.elevation = 15f

                //tabLayout이 선택되면 무조건 appbar는 축소되도록 설정
                binding.appBar.setExpanded(false)

                //tabLayout 높이 만큼 빼고(54dp) bar의 높이(6dp)를 빼서 48dp만큼 y값을 더 추가해 정확한 위치를 맞추기 위함
                if (!isUserScrolling){
                    when (tab!!.position) {
                        0 -> {
                            binding.stickyScroll.smoothScrollTo(binding.stickyScroll.scrollX, 0) //48dp를 픽셀로 계산해서 y값을 조정해줌
                        }
                        1 -> {
                            binding.stickyScroll.smoothScrollTo(binding.stickyScroll.scrollX, binding.line3.y.toInt() - dp48)
                        }
                        2 -> {
                            binding.stickyScroll.smoothScrollTo(binding.stickyScroll.scrollX, binding.line6.y.toInt() - dp48)
                        }
                        3 -> {
                            binding.stickyScroll.smoothScrollTo(binding.stickyScroll.scrollX, binding.line10.y.toInt() - dp48)
                        }
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        //스크롤뷰 리스너
        binding.stickyScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val totalHeigt = binding.stickyScroll.getChildAt(0).height

            if (isUserScrolling){
                //스크롤뷰를 터치해서 스크롤 되는건지 아니면 tabLayout을 눌러서 스크롤 되는건지 파악해서 움직이기 위해 확인
                //스크롤 위치가 고정된 tabLayout밑에 부터 시작하는데 line들은 tabLayout을 포함하지 않고 최상단 부터 y값을 계산해서 tabLayout높이 만큼 스크롤의 위치를 더해서 line의 y값과 맞춰줌
                if (scrollY + dp48 >= dp48 && scrollY + dp48 < binding.line3.y.toInt()) {
                    binding.tabLayout.getTabAt(0)?.select()
                    //binding.tabLayout.setScrollPosition(0, 0f, true)
                } else if (scrollY + dp48 >= binding.line3.y.toInt() && scrollY + dp48 < binding.line6.y.toInt()) {
                    binding.tabLayout.getTabAt(1)?.select()
                    //binding.tabLayout.setScrollPosition(1, 0f, true)

                    //최하단일 경우 기타 메뉴가 선택 되도록 설정
                } else if(!binding.stickyScroll.canScrollVertically(1)) {
                    binding.tabLayout.getTabAt(3)?.select()
                    //binding.tabLayout.setScrollPosition(3, 0f, true)

                } else if (scrollY + dp48 >= binding.line6.y.toInt() && scrollY + dp48 < binding.line10.y.toInt()) {//마지막 부분에 기타 부분 때문에 리사이클러뷰가 잘 안맞아서
                    //임의적으로 툴바 크기만큼 범위를 줄여준다.
                    binding.tabLayout.getTabAt(2)?.select()
                    //binding.tabLayout.setScrollPosition(2, 0f, true)
                }
            }
        }

        //nestedScroll을 터치 즉, tabLayout이 아닌 드래그해서 스크롤을 움직일 경우를 감지하기 위한 터치 리스너
        binding.stickyScroll.setOnTouchListener { v, event ->
            when (event.actionMasked){
                MotionEvent.ACTION_DOWN->{
                    isUserScrolling=true
                }
                MotionEvent.ACTION_MOVE->{
                    isUserScrolling=true
                }
            }
            return@setOnTouchListener false
        }


        //만약 온라인 신청이 신청 방법 중 포함되어 있지 않으면 신청하러가기 버튼 비활성화 하면서 텍스트도 방문 신청으로 변경
        if ("온라인신청" in welfareInfo.applyMethod) {
            binding.btnGoUrl.isEnabled = true
        } else {
            binding.btnGoUrl.isEnabled = false
            binding.btnGoUrl.text = "방문 신청"
        }

        //신청하러가기 버튼 클릭 리스너
        binding.btnGoUrl.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(welfareInfo.url))
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        binding.appBar.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                appBarHeight = binding.appBar.height

                binding.appBar.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    /*
   dday 구하는 메소드
    */
    private fun getDDay(dayYear: Int, dayMonth: Int, dayDay: Int): String {
        Locale.setDefault(Locale.KOREAN);

        var welfareMonth = dayMonth - 1
        var welfareYear = dayYear
        var welfareDay = dayDay

        try {
            val format = SimpleDateFormat("yyyy-mm-dd")
            //지금 시간 가져오기
            val currentDate = Calendar.getInstance()
            val cYear = currentDate.get(Calendar.YEAR)
            val cMonth = currentDate.get(Calendar.MONTH)
            val cDay = currentDate.get(Calendar.DAY_OF_MONTH)
            currentDate.set(cYear, cMonth, cDay) //오늘 날짜에 세팅

            val welfareDate = Calendar.getInstance()
            welfareDate.set(welfareYear, welfareMonth, welfareDay)//복지 날짜 형식에 맞게 세팅

            val currentDay = currentDate.timeInMillis / 86400000 //계산을 위해 밀리세컨즈로 변경 후 나누기
            val dday = welfareDate.timeInMillis / 86400000 //계산을 위해 밀리세컨즈로 변경 후 나누기
            val count = dday - currentDay //오늘 날짜에서 dday날짜를 빼준다.

            if (count.toInt() > 0)
                return "D-${count.toInt()}"
            else if (count.toInt() < 0)
                return "D+${count.toInt() * -1}"
            else
                return "D-Day"
        } catch (e: ParseException) {
            Log.e("ERROR", "날짜 변화 중 형식이 맞지 않아 오류 발생")
            return "날짜 형식이 올바르지 않습니다"
        }
    }
}