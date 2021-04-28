package com.example.kaumobile.ui.home

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kaumobile.R
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.example.kaumobile.firebase.Database

class HomeFragment : Fragment() {
    private var year = 2021
    private var semester = 1
    private var classNum = 0
    private val weekList = arrayOf("월요일","화요일","수요일","목요일","금요일")
    private val weekList2 = arrayOf("월","화","수","목","금")
    private val timeList = arrayOf("9시","10시","11시","12시","13시","14시","15시","16시","17시","18시")
    private val timeList2 = arrayOf("0900","1000","1100","1200","1300","1400","1500","1600","1700","1800")
    private val colorList = arrayOf("#481677", "#7410d0", "#a648ff", "#115586", "#4a7eb2", "#0080ff", "#8977ad", "#de00e0", "#f34e00", "#cc4600")

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val buttonView = root.findViewById<LinearLayout>(R.id.button_view)
        val dynamicClass = Array(20){Button(requireContext())}
        val addRequest = Button(requireContext())
        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(changeDP(10), 0, changeDP(10), 0)

        addRequest.layoutParams = layoutParams
        addRequest.setText("강의를 추가해주세요")
        addRequest.width = changeDP(170)
        addRequest.setBackgroundColor(Color.parseColor("#808080"))
        addRequest.setTextColor(Color.parseColor("#FFFFFF"))
        addRequest.gravity = Gravity.CENTER
        buttonView.addView(addRequest)

        root.findViewById<TextView>(R.id.text_main_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")

        root.findViewById<View>(R.id.button_main_notifications).setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_notifications)
        }

        root.findViewById<View>(R.id.button_main_grade).setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_grade)
        }

        root.findViewById<View>(R.id.button_main_prevsemester).setOnClickListener {
            year -= (2-semester)
            semester = 3-semester
            root.findViewById<TextView>(R.id.text_main_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")
        }

        root.findViewById<View>(R.id.button_main_nextsemester).setOnClickListener {
            year += (semester-1)
            semester = 3-semester
            root.findViewById<TextView>(R.id.text_main_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")
        }

        root.findViewById<View>(R.id.button_main_addclass).setOnClickListener {
            var weekNum = 0
            var startTimeNum = 0
            var endTimeNum = 0

            var builder = AlertDialog.Builder(requireContext())
            builder.setTitle("강의 추가")

            var v1 = layoutInflater.inflate(R.layout.add_dialog, null)
            builder.setView(v1)

            var listener = DialogInterface.OnClickListener { p0, p1 ->
                var alert = p0 as AlertDialog
                var edit1: EditText? = alert.findViewById<EditText>(R.id.edit_classname_add)
                var edit2: EditText? = alert.findViewById<EditText>(R.id.edit_professor_add)
                var edit3: EditText? = alert.findViewById<EditText>(R.id.edit_classroom_add)

                Database().addNewSubject("${edit1?.text}", "${edit2?.text}", "${edit3?.text}",
                    weekList2[weekNum]+timeList2[startTimeNum]+timeList2[endTimeNum])

                if(classNum == 0)
                    buttonView.removeView(addRequest)

                dynamicClass[classNum].layoutParams = layoutParams
                dynamicClass[classNum].id = classNum
                dynamicClass[classNum].setText("${edit1?.text}\n\n${edit2?.text}\n\n${edit3?.text}\n\n" +
                        "${weekList[weekNum]} ${timeList[startTimeNum]} ~ ${timeList[endTimeNum]}")
                dynamicClass[classNum].width = changeDP(170)
                dynamicClass[classNum].setBackgroundColor(Color.parseColor(colorList[classNum]))
                dynamicClass[classNum].setTextColor(Color.parseColor("#FFFFFF"))
                buttonView.addView(dynamicClass[classNum])

                dynamicClass[classNum].setOnClickListener {
                    Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_classnote)
                }
                dynamicClass[classNum].setOnLongClickListener {
                    var builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("강의 삭제")

                    var v1 = layoutInflater.inflate(R.layout.delete_dialog, null)
                    builder.setView(v1)

                    var listener2 = DialogInterface.OnClickListener { p0, p1 ->
                        val num = it.id
                        buttonView.removeView(it)
                        for(i in num+1..classNum-1) {
                            dynamicClass[i - 1] = dynamicClass[i]
                            dynamicClass[i - 1].setBackgroundColor(Color.parseColor(colorList[i - 1]))
                        }
                        dynamicClass[classNum-1] = Button(requireContext())
                        classNum--
                        //Log.d("태그", "내용 : "+ num + " " + classNum)
                        if(classNum == 0){
                            addRequest.layoutParams = layoutParams
                            addRequest.setText("강의를 추가해주세요")
                            addRequest.width = changeDP(170)
                            addRequest.setBackgroundColor(Color.parseColor("#808080"))
                            addRequest.setTextColor(Color.parseColor("#FFFFFF"))
                            addRequest.gravity = Gravity.CENTER
                            buttonView.addView(addRequest)
                        }
                    }

                    builder.setPositiveButton("삭제", listener2)
                    builder.setNegativeButton("취소", null)

                    builder.show()
                    true
                }
                classNum++
            }

            val weekSpinner = v1.findViewById<Spinner>(R.id.spinner_week_add)
            weekSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, weekList)
            weekSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    weekNum = position
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            val startTimeSpinner = v1.findViewById<Spinner>(R.id.spinner_starttime_add)
            startTimeSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeList)
            startTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    startTimeNum = position
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            val endTimeSpinner = v1.findViewById<Spinner>(R.id.spinner_endtime_add)
            endTimeSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeList)
            endTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    endTimeNum = position
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            builder.setPositiveButton("입력", listener)
            builder.setNegativeButton("취소", null)

            builder.show()
        }

        return root
    }

    private fun changeDP(value : Int) : Int{
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }
}