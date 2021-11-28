package com.example.knowing.ui.decorator

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarEventDayDecorator(eventDay:Collection<CalendarDay>):DayViewDecorator {
    //받아온 ArrayList를 HashSet형식으로 변경
    private var eventDay : HashSet<CalendarDay> = HashSet<CalendarDay>(eventDay)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        //받아온 날짜랑 겹치는 부분만 true로 반환해서 점 찍어주기 위함.
        return eventDay.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        //날짜 밑에 점 찍어줌
        view?.addSpan(DotSpan(7f,Color.parseColor("#ffa166")))
    }
}