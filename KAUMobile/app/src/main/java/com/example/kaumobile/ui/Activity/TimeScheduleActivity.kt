package com.example.kaumobile.ui.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.kaumobile.R
import com.example.kaumobile.ui.home.Subject
import com.example.kaumobile.user.UserData


class TimeScheduleActivity : AppCompatActivity() {
    var Monday = Array<String>(18,{i ->""})
    var Tuesday = Array<String>(18,{i ->""})
    var Wednesday = Array<String>(18,{i ->""})
    var Thursday = Array<String>(18,{i ->""})
    var Friday = Array<String>(18,{i ->""})

    var mondayTextViews = Array<TextView?>(18, {i->null})
    var tuesdayTextViews = Array<TextView?>(18, {i->null})
    var wednesdayTextViews = Array<TextView?>(18, {i->null})
    var thursdayTextViews = Array<TextView?>(18, {i->null})
    var fridayTextViews = Array<TextView?>(18, {i->null})


    val cellIDs = listOf(R.id.monday1,R.id.monday1_5,R.id.monday2,R.id.monday2_5,R.id.monday3,R.id.monday3_5,
        R.id.monday4, R.id.monday4_5, R.id.monday5, R.id.monday5_5, R.id.monday6, R.id.monday6_5, R.id.monday7,
        R.id.monday7_5, R.id.monday8, R.id.monday8_5, R.id.monday9, R.id.monday9_5,
        R.id.tuesday1,R.id.tuesday1_5,R.id.tuesday2,R.id.tuesday2_5,R.id.tuesday3,R.id.tuesday3_5,
        R.id.tuesday4, R.id.tuesday4_5, R.id.tuesday5, R.id.tuesday5_5, R.id.tuesday6, R.id.tuesday6_5, R.id.tuesday7,
        R.id.tuesday7_5, R.id.tuesday8, R.id.tuesday8_5, R.id.tuesday9, R.id.tuesday9_5,
        R.id.wednesday1,R.id.wednesday1_5,R.id.wednesday2,R.id.wednesday2_5,R.id.wednesday3,R.id.wednesday3_5,
        R.id.wednesday4, R.id.wednesday4_5, R.id.wednesday5, R.id.wednesday5_5, R.id.wednesday6, R.id.wednesday6_5, R.id.wednesday7,
        R.id.wednesday7_5, R.id.wednesday8, R.id.wednesday8_5, R.id.wednesday9, R.id.wednesday9_5,
        R.id.thursday1,R.id.thursday1_5,R.id.thursday2,R.id.thursday2_5,R.id.thursday3,R.id.thursday3_5,
        R.id.thursday4, R.id.thursday4_5, R.id.thursday5, R.id.thursday5_5, R.id.thursday6, R.id.thursday6_5, R.id.thursday7,
        R.id.thursday7_5, R.id.thursday8, R.id.thursday8_5, R.id.thursday9, R.id.thursday9_5,
        R.id.friday1,R.id.friday1_5,R.id.friday2,R.id.friday2_5,R.id.friday3,R.id.friday3_5,
        R.id.friday4, R.id.friday4_5, R.id.friday5, R.id.friday5_5, R.id.friday6, R.id.friday6_5, R.id.friday7,
        R.id.friday7_5, R.id.friday8, R.id.friday8_5, R.id.friday9, R.id.friday9_5)

    init{
        for(i in 0..cellIDs.size){
            when(i/18){
                0->{
                    for(j in 0..18) mondayTextViews[j] = findViewById<TextView>(i)
                }
                1->{
                    for(j in 0..18) tuesdayTextViews[j] = findViewById<TextView>(i)
                }
                2->{
                    for(j in 0..18) wednesdayTextViews[j] = findViewById<TextView>(i)
                }
                3->{
                    for(j in 0..18) thursdayTextViews[j] = findViewById<TextView>(i)
                }
                4->{
                    for(j in 0..18) fridayTextViews[j] = findViewById<TextView>(i)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_schedule)
        setSchedule(UserData.subjectList)
    }

    fun setSchedule(subjectList: ArrayList<Subject>){

        fun setTimeArray(start: Int, end: Int, time: Array<String>, cells: Array<TextView?>, subject: String): Array<String>{
            var idxStart = start-900
            var idxEnd = end-900
            val calculate = { x: Int -> when(x%100){
                0 -> x/100
                30 -> x/100 + 1
                else -> Log.e("Schedule", "Wrong Time ${start}-${end}")
            }
            }

            idxStart = calculate(idxStart)
            idxEnd = calculate(idxEnd)

            for(i in idxStart..idxEnd){
                time[i] = subject
                cells[i]!!.setText(subject)
            }

            return time
        }


        for(i in 0..subjectList.size){
            var time = subjectList[i].classTime.split("#")
            var data = subjectList[i].className
            for(j in 0..time.size){
                var day = time[j][0]
                var start = time[j].slice(IntRange(1,4)).toInt()-900
                var end = time[j].slice(IntRange(5,8)).toInt()-900
                when(day){
                    '월' -> {
                        Monday = setTimeArray(start, end, Monday, mondayTextViews, data)
                    }
                    '화' -> {
                        Tuesday = setTimeArray(start, end, Tuesday, tuesdayTextViews, data)
                    }
                    '수'-> {
                        Wednesday = setTimeArray(start, end, Wednesday, wednesdayTextViews, data)
                    }
                    '목' -> {
                        Thursday = setTimeArray(start, end, Thursday, thursdayTextViews, data)
                    }
                    '금' -> {
                        Friday = setTimeArray(start, end, Friday, fridayTextViews, data)
                    }
                    else -> {
                        Log.e("Schedule","Wrong Input: Incorrect day name${day}")
                    }

                }
            }
        }
    }
}