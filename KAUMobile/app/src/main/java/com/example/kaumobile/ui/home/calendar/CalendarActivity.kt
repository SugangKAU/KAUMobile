package com.example.kaumobile.ui.home.calendar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.kaumobile.R
import sun.bob.mcalendarview.MarkStyle
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.listeners.OnMonthChangeListener
import sun.bob.mcalendarview.views.ExpCalendarView
import sun.bob.mcalendarview.vo.DateData
import java.util.*

var nowYear = Calendar.getInstance().get(Calendar.YEAR)
var nowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
var nowDay = Calendar.getInstance().get(Calendar.DATE)
var classNameList : ArrayList<String> = arrayListOf()
var classTimeList : ArrayList<String> = arrayListOf()
var calendarList : Array<String?> = arrayOfNulls(32)
var year = 2021
var semester = 1
private val colorList = arrayOf("#481677", "#7410d0", "#a648ff", "#115586", "#4a7eb2", "#0080ff", "#8977ad", "#de00e0", "#f34e00", "#cc4600")

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        var intent = getIntent()
        classNameList = intent.getStringArrayListExtra("name")!!
        classTimeList = intent.getStringArrayListExtra("time")!!
        year = intent.getStringExtra("year")!!.toInt()
        semester = intent.getStringExtra("semester")!!.toInt()

        Log.d("ff", "${classNameList}")

        findViewById<TextView>(R.id.text_calendar_month).setText(nowYear.toString() + "년 " + nowMonth.toString() + "월")

        findViewById<View>(R.id.button_calendar_prevmonth).setOnClickListener {
            unMarkCalendar(findViewById(R.id.calendarView))
            nowMonth--
            if(nowMonth < 1){
                nowMonth = 12
                nowYear--
            }
            findViewById<TextView>(R.id.text_calendar_month).setText(nowYear.toString() + "년 " + nowMonth.toString() + "월")
            calendarList = arrayOfNulls(32)
            findViewById<ExpCalendarView>(R.id.calendarView).travelTo(DateData(nowYear, nowMonth, 0))
            if(nowYear == year && nowMonth >= semester*6-3 && nowMonth <= semester*6)
                markCalendar(findViewById(R.id.calendarView))
            findViewById<TextView>(R.id.text_calendar_content).setText("")
        }
        findViewById<View>(R.id.button_calendar_nextmonth).setOnClickListener {
            unMarkCalendar(findViewById(R.id.calendarView))
            nowMonth++
            if(nowMonth > 12){
                nowMonth = 1
                nowYear++
            }
            findViewById<TextView>(R.id.text_calendar_month).setText(nowYear.toString() + "년 " + nowMonth.toString() + "월")
            calendarList = arrayOfNulls(32)
            findViewById<ExpCalendarView>(R.id.calendarView).travelTo(DateData(nowYear, nowMonth, 0))
            if(nowYear == year && nowMonth >= semester*6-3 && nowMonth <= semester*6)
                markCalendar(findViewById(R.id.calendarView))
            findViewById<TextView>(R.id.text_calendar_content).setText("")
        }

        findViewById<ExpCalendarView>(R.id.calendarView).setOnMonthChangeListener(object : OnMonthChangeListener() {
            override fun onMonthChange(year: Int, month: Int) {
                findViewById<ExpCalendarView>(R.id.calendarView).travelTo(DateData(nowYear, nowMonth, 0))
            }
        })

        findViewById<ExpCalendarView>(R.id.calendarView).setOnDateClickListener(object : OnDateClickListener() {
            override fun onDateClick(view: View, date: DateData) {
                Log.d("f", "${date.month} ${date.day}")
                findViewById<TextView>(R.id.text_calendar_content).setText(date.year.toString() + "년 " + date.month.toString() + "월" + date.day.toString() + "일")
                if(calendarList[date.day] != null && date.year == nowYear && date.month == nowMonth)
                    findViewById<TextView>(R.id.text_calendar_content).append("\n${calendarList[date.day]}")
            }
        })

        findViewById<ExpCalendarView>(R.id.calendarView).travelTo(DateData(nowYear, nowMonth, 0))
        if(nowYear == year && nowMonth >= semester*6-3 && nowMonth <= semester*6)
            markCalendar(findViewById(R.id.calendarView))
    }

    fun markCalendar(calendar: ExpCalendarView) {
        var num = 0
        var cal = Calendar.getInstance()
        cal.set(nowYear, nowMonth-1, 1)
        var last = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendarList = arrayOfNulls(32)

        for (i in classTimeList!!.size-1 downTo 0){
            for(j in 0 until classTimeList[i].length){
                num = weekToNum(classTimeList[i][j])
                if(num != 1){
                    for(k in 1 .. last){
                        cal.set(nowYear, nowMonth-1, k)

                        if(cal.get(Calendar.DAY_OF_WEEK) == num) {
                            if(calendarList[k] == null)
                                calendarList[k] = "${classNameList[i]} - 수업"
                            else
                                calendarList[k] = calendarList[k].plus("\n${classNameList[i]} - 수업")
                            calendar.markedDates.remove(DateData(nowYear, nowMonth, k))
                            calendar.markDate(DateData(nowYear, nowMonth, k).setMarkStyle(MarkStyle(MarkStyle.BACKGROUND, Color.parseColor(colorList[i]))))
                        }
                    }
                }
            }
        }
    }

    fun unMarkCalendar(calendar: ExpCalendarView) {
        var cal = Calendar.getInstance()
        cal.set(nowYear, nowMonth-1, 1)
        var last = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 1 .. last)
            calendar.markedDates.remove(DateData(nowYear, nowMonth, i))
    }

    fun weekToNum(day : Char) : Int {
        if(day == '월')
            return 2
        else if(day == '화')
            return 3
        else if(day == '수')
            return 4
        else if(day == '목')
            return 5
        else if(day == '금')
            return 6
        else
            return 1
    }
}