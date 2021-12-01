package com.teamteam.knowing.ui.view.main.home

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.databinding.FragmentMainHomeCalenedarBinding
import com.teamteam.knowing.ui.adapter.MainHomeCalendarRcAdapter
import com.teamteam.knowing.ui.base.BaseFragment
import com.teamteam.knowing.ui.decorator.CalendarEventDayDecorator
import com.teamteam.knowing.ui.decorator.CalendarTodyDecorator
import com.teamteam.knowing.ui.viewmodel.CalendarFragmentViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class CalendarFragment : BaseFragment<FragmentMainHomeCalenedarBinding>(
    FragmentMainHomeCalenedarBinding::bind,
    R.layout.fragment_main_home_calenedar
) {

    //달력에 점을 찍어야 하는 날짜들 모음 리스트
    private var eventDayList = ArrayList<CalendarDay>()

    //뷰모델 객체 저장할 변수
    private lateinit var calendarFragmentViewModel: CalendarFragmentViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //statusbar 높이 구하는 방법
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }

        //statusbar 투명하게 해버리고 올라간 만큼 appbar를 내리기 위해 marinTop을 줌
        var params = binding.appBar.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, result + 30, 0, 0)
        binding.appBar.layoutParams = params

        //statusbar 투명하게 해버리고 appbar 마진탑을 주니까 coolapsing에서 애먹어서 걍 statusbar 높이 만큼 임의의 뷰를 넓혀서 덮어버림
        var params2 = binding.imgTopRectangle.layoutParams as CoordinatorLayout.LayoutParams
        params2.height = result * 2
        binding.imgTopRectangle.layoutParams = params2


        //뷰모델 장착
        calendarFragmentViewModel =
            ViewModelProvider(this).get(CalendarFragmentViewModel::class.java)

        //달력 월마다 4~5주에 맞게 다이나믹하게 높이 지정
        binding.calendar.isDynamicHeightEnabled=true

        //리사이클러뷰 장착을 위해 라이브데이터 관찰
        calendarFragmentViewModel.currentRcWelfareList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if(it.size!=0){//북마크 정보가 있으면 일러스트 없애고 리사이클러뷰 보이게
                binding.rcMyCalendar.visibility=View.VISIBLE
                binding.constraintNoData.visibility=View.INVISIBLE

                binding.rcMyCalendar.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                val adapter = MainHomeCalendarRcAdapter(requireContext(), it)
                binding.rcMyCalendar.adapter = adapter
                adapter.notifyDataSetChanged()
                //스와이프 삭제 버튼 클릭 리스너
                adapter.setOnItemClickListener(object : MainHomeCalendarRcAdapter.OnItemClickListener {
                    override fun onItemClick(uid: String) { //삭제했을 경우 점 찍을 날짜 리스트를 초기화
                        eventDayList = ArrayList<CalendarDay>()

                        //스와이프 삭제 버튼을 클릭 했을 경우 해당 복지 북마크에서 삭제 api호출
                        calendarFragmentViewModel.tryDeleteBookmarkList(ApplicationClass.USER_UID, uid)
                    }
                })
            }else{ //북마크 정보가 없으면 일러스트 보여주기 위함
                binding.rcMyCalendar.visibility=View.INVISIBLE
                binding.constraintNoData.visibility=View.VISIBLE
            }
    })


        //특정 날짜에 dot찍기 위한 코드들임 근데 특정 날짜는 되지만 각 범위에 다 찍어주려면 다른 계산이 필요할 듯
        //val simple_format = SimpleDateFormat("yyyy.MM.dd")
        //ClaendarDay형을 저장하는 리스트에 임의의 5개 날짜를 추가한다. 단, 추가할 때 CalendarDay.from(이 부분에 date형식 또는 Calendar 형식으로 넣어줘야함.)


        //달력에 점을 찍을 데이터들을 모아두는 라이브데이터 관찰
        calendarFragmentViewModel.welfareApplyDateList.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                //다른 프래그먼트 갔다 오면 달력에 점 찍어줄 리스트를 초기화
                eventDayList = ArrayList<CalendarDay>()

                if (it.isNotEmpty()) {
                    for (i in it) {
                        if (i != "연중상시" && i != "별도공지") {
                            val applyMining = i.split("~")
                            //달력에 출력할 날짜를 시작과 마감일을 넣으면 자동으로 생성해서 eventDayList리스트에 담아줌
                            getDates(applyMining[0], applyMining[1])
                        }
                    }
                }

                //데코 전부 삭제
                binding.calendar.removeDecorators()

                //오늘 날짜의 글씨색을 다르게 하기 위해 데코클래스 적용
                binding.calendar.addDecorators(CalendarTodyDecorator())

                //for문으로 특정 날짜에 해당하는 아이들에 dot찍히는 데코 설정
                binding.calendar.addDecorator(CalendarEventDayDecorator(eventDayList))
            })


        //달력 클릭 리스너 장착
        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            //마지막으로 클릭된 데이터를 저장하는 라이브데이터에 값을 저장
            calendarFragmentViewModel.finalSelectedDate.value="${date.year}${date.month+1}${date.day}"//월은 0부터 시작해서 1 추가해줌
            //클릭한 날짜에 해당하는 북마크 중 복지 정보들을 리사이클러뷰 리스트에 저장
            calendarFragmentViewModel.setRcList()
        }

    }

    /*
    날짜 사이의 날짜를 생성해주는 메소드
     */
    private fun getDates(dateString1: String, dateString2: String): List<Date>? {
        val dates = ArrayList<Date>()
        val df1: DateFormat = SimpleDateFormat("yyyy.MM.dd")
        var date1: Date? = null
        var date2: Date? = null
        try {
            date1 = df1.parse(dateString1)
            date2 = df1.parse(dateString2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        while (!cal1.after(cal2)) {
            dates.add(cal1.time)
            eventDayList.add(CalendarDay.from(cal1.time))
            cal1.add(Calendar.DATE, 1)
        }
        return dates
    }

    //모든복지 또는 맞춤 복지 갔다가 왔을 때 북마크 셋팅을 불러오기 위해 onResume에서 api 호출
    override fun onResume() {
        super.onResume()

         //다른 프래그먼트 갔다 오면 달력에 점 찍어줄 리스트를 초기화
        eventDayList = ArrayList<CalendarDay>()

        //북마크에 저장된 복지 데이터들 api호출해서 불러옴
        calendarFragmentViewModel.tryGetBookmarkList(ApplicationClass.USER_UID)
    }
}