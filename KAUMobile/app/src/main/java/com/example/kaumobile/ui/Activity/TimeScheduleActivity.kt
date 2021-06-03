package com.example.kaumobile.ui.Activity

import android.annotation.SuppressLint
import android.graphics.Color
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
    var test = 1

    var mondayTextViews = Array<TextView?>(18, {i->null})
    var tuesdayTextViews = Array<TextView?>(18, {i->null})
    var wednesdayTextViews = Array<TextView?>(18, {i->null})
    var thursdayTextViews = Array<TextView?>(18, {i->null})
    var fridayTextViews = Array<TextView?>(18, {i->null})

    private val colorList = arrayOf("#481677", "#7410d0", "#a648ff", "#115586", "#4a7eb2", "#0080ff", "#8977ad", "#de00e0", "#f34e00", "#cc4600")


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

    fun initiate(){
        for(i in 0..cellIDs.size-1){
            when(i/18){
                0->{
                    mondayTextViews[i%18] = findViewById<TextView>(cellIDs[i])
                }
                1->{
                    tuesdayTextViews[i%18] = findViewById<TextView>(cellIDs[i])
                }
                2->{
                    wednesdayTextViews[i%18] = findViewById<TextView>(cellIDs[i])
                }
                3->{
                    thursdayTextViews[i%18] = findViewById<TextView>(cellIDs[i])
                }
                4->{
                    fridayTextViews[i%18] = findViewById<TextView>(cellIDs[i])
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_schedule)
        initiate()
        setSchedule(UserData.subjectList)
    }

    fun setSchedule(subjectList: ArrayList<Subject>){

        fun setTimeArray(start: Int, end: Int, time: Array<String>, subject: String): Array<String>{
            var idxStart = start
            var idxEnd = end
            Log.e("Schedule", "Wrong Time ${idxStart}-${idxEnd}")
            val calculate = { x: Int -> when(x%100){
                0 -> 2*(x/100)
                30 -> 2*(x/100) + 1
                else -> Log.e("Schedule", "Wrong Time ${start}-${end}")
            }
            }

            idxStart = calculate(idxStart)
            idxEnd = calculate(idxEnd)

            for(i in idxStart..idxEnd){
                time[i] = subject
                Log.e("Schedule", "${i}  ${subject} ${idxStart}-${idxEnd}")
            }

            return time
        }

        @SuppressLint("ResourceAsColor")
        fun drawSubjects(time: Array<String>, cells: Array<TextView?>){
            Log.e("Schedule", "${time}")
            for(i in 0..time.size-1){
                if(time[i]!=""){
                    Log.e("Schedule", "${i} ${time[i]}")
                    cells[i]!!.setBackgroundColor(Color.parseColor(colorList[subjectList.indexOf(time[i])]))
                    cells[i]!!.setText(time[i])
                }
            }
        }


        for(i in 0..subjectList.size-1){
            var time = subjectList[i].classTime.split("#")
            var data = subjectList[i].className
            Log.e("Schedule", "${time}${data}")
            for(j in 1..time.size-1){
                var day = time[j][0]
                var start = time[j].slice(IntRange(1,4)).toInt()-900
                var end = time[j].slice(IntRange(5,8)).toInt()-900
                when(day){
                    '월' -> {
                        Monday = setTimeArray(start, end, Monday, data)
                        drawSubjects(Monday, mondayTextViews)
                    }
                    '화' -> {
                        Tuesday = setTimeArray(start, end, Tuesday,data)
                        drawSubjects(Tuesday, tuesdayTextViews)
                    }
                    '수'-> {
                        Wednesday = setTimeArray(start, end, Wednesday, data)
                        drawSubjects(Wednesday, wednesdayTextViews)
                    }
                    '목' -> {
                        Thursday = setTimeArray(start, end, Thursday, data)
                        drawSubjects(Thursday, thursdayTextViews)
                    }
                    '금' -> {
                        Friday = setTimeArray(start, end, Friday, data)
                        drawSubjects(Friday, fridayTextViews)
                    }
                    else -> {
                        Log.e("Schedule","Wrong Input: Incorrect day name${day}")
                    }

                }
            }
        }
    }
}