package com.example.knowing.ui.view.main.home

import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knowing.R
import com.example.knowing.databinding.FragmentMainHomeCalenedarBinding
import com.example.knowing.ui.adapter.MainHomeCalendarRcAdapter
import com.example.knowing.ui.adapter.MainHomeCustomWelfareRCAdapter
import com.example.knowing.ui.base.BaseFragment
import com.example.knowing.ui.decorator.CalendarClickDecorator
import com.example.knowing.ui.decorator.CalendarEventDayDecorator
import com.example.knowing.ui.decorator.CalendarTodyDecorator
import com.example.knowing.ui.helper.SwipeHelper
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : BaseFragment<FragmentMainHomeCalenedarBinding>(FragmentMainHomeCalenedarBinding::bind, R.layout.fragment_main_home_calenedar){

    private var dumyData = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dumyData.add("청년고용지원")
        dumyData.add("청년월세지원")
        dumyData.add("국민취업지원제도")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //오늘 날짜의 글씨색을 다르게 하기 위해 데코클래스 적용
        binding.calendar.addDecorators(CalendarTodyDecorator())


        //특정 날짜에 dot찍기 위한 코드들임 근데 특정 날짜는 되지만 각 범위에 다 찍어주려면 다른 계산이 필요할 듯
        val eventDayList = ArrayList<CalendarDay>()
        val simple_format = SimpleDateFormat("yyyy.MM.dd")
        //ClaendarDay형을 저장하는 리스트에 임의의 5개 날짜를 추가한다. 단, 추가할 때 CaalendarDay.from(이 부분에 date형식 또는 Calendar 형식으로 넣어줘야함.)
        eventDayList.add(CalendarDay.from(simple_format.parse("2021.11.25")))
        eventDayList.add(CalendarDay.from(simple_format.parse("2021.11.05")))
        eventDayList.add(CalendarDay.from(simple_format.parse("2021.11.15")))
        eventDayList.add(CalendarDay.from(simple_format.parse("2021.11.01")))
        eventDayList.add(CalendarDay.from(simple_format.parse("2021.11.28")))

        //for문으로 특정 날짜에 해당하는 아이들에 dot찍히는 데코 설정
        binding.calendar.addDecorator(CalendarEventDayDecorator(eventDayList))

        //리사이클러뷰 어댑터 장착
        binding.rcMyCalendar.layoutManager= LinearLayoutManager(requireContext())
        val adapter =  MainHomeCalendarRcAdapter(requireContext(),dumyData)
        binding.rcMyCalendar.adapter = adapter
    }


//    fun setUpRecyclerView() {
//
//        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.rcMyCalendar) {
//            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
//                var buttons = listOf<UnderlayButton>()
//                val deleteButton = deleteButton(position)
//                buttons = listOf(deleteButton)
////                val markAsUnreadButton = markAsUnreadButton(position)
////                val archiveButton = archiveButton(position)
////                when (position) {
////                    1 -> buttons = listOf(deleteButton)
////                    2 -> buttons = listOf(deleteButton, markAsUnreadButton)
////                    3 -> buttons = listOf(deleteButton, markAsUnreadButton, archiveButton)
////                    else -> Unit
////                }
//                return buttons
//            }
//        })
//
//        itemTouchHelper.attachToRecyclerView(binding.rcMyCalendar)
//    }

//        fun toast(text: String) {
//            toast?.cancel()
//            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
//            toast?.show()
//        }

    fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "삭제",
            14.0f,
            android.R.color.holo_orange_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    Toast.makeText(requireContext(),"Deleted item $position",Toast.LENGTH_SHORT).show()
                }
            })
    }

//    fun markAsUnreadButton(position: Int) : SwipeHelper.UnderlayButton {
//        return SwipeHelper.UnderlayButton(
//            requireContext(),
//            "Mark as unread",
//            14.0f,
//            android.R.color.holo_green_light,
//            object : SwipeHelper.UnderlayButtonClickListener {
//                override fun onClick() {
//                    toast("Marked as unread item $position")
//                }
//            })
//    }
//
//    fun archiveButton(position: Int) : SwipeHelper.UnderlayButton {
//        return SwipeHelper.UnderlayButton(
//            requireContext(),
//            "Archive",
//            14.0f,
//            android.R.color.holo_blue_light,
//            object : SwipeHelper.UnderlayButtonClickListener {
//                override fun onClick() {
//                    toast("Archived item $position")
//                }
//            })
//    }
}