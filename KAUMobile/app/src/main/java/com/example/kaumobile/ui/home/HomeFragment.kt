package com.example.kaumobile.ui.home

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.kaumobile.firebase.Database
import com.example.kaumobile.ui.Activity.TimeScheduleActivity
import com.example.kaumobile.ui.home.calendar.CalendarActivity

class HomeFragment : Fragment() {
    private var year = 2021
    private var semester = 1
    private var classNum = 0
    private val weekList = arrayOf("월요일","화요일","수요일","목요일","금요일")
    private val weekList2 = arrayOf("월","화","수","목","금")
    private val timeList = arrayOf("09시","10시","11시","12시","13시","14시","15시","16시","17시","18시")
    private val timeList2 = arrayOf("0900","1000","1100","1200","1300","1400","1500","1600","1700","1800")
    private val colorList = arrayOf("#481677", "#7410d0", "#a648ff", "#115586", "#4a7eb2", "#0080ff", "#8977ad", "#de00e0", "#f34e00", "#cc4600")
    private lateinit var homeViewModel: HomeViewModel
    private var init = 2
    private val TAG = "Home"

    var classList : ArrayList<String> = arrayListOf()
    var subjectInfoList : ArrayList<Subject> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        // 동적 버튼
        val buttonView = root.findViewById<LinearLayout>(R.id.button_view)
        val dynamicClass = Array(20){Button(requireContext())}
        // 빈 버튼 생성
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(changeDP(10), 0, changeDP(10), 0)
        addEmptyButton(buttonView, requireContext())
        // 데이터베이스에서 과목 리스트 받기
      //  val subjectList = arguments?.getStringArrayList("subjects")
        // val bundle = bundleOf("subjects" to subjectList)


        // 검색 텍스트뷰, 버튼 기능
        //val classList = mutableListOf<String>("안드로이드 기초", "요하문명의 이해", "모바일SW스튜디오", "동양철학의 이해", "안드로이드 심화")
        //val classList = subjectList?.toMutableList()
        val searchAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, classList!!)

        val subjectObserver : Observer<ArrayList<Subject>> by lazy {
            Observer<ArrayList<Subject>>{ _subjectList->
                classNum = 0
                if(init>0){
                    init --
                    Log.d(TAG, "Load Dynamic Button")
                    Log.d(TAG,"${_subjectList}")
                    for (i in _subjectList) {
                        Log.d("Loop", "${i} classNum: ${classNum}")
                        if (classNum == 0)
                            buttonView.removeAllViews()

                        classList.add(i.className)
                        subjectInfoList.add(i)
                        dynamicClass[classNum].layoutParams = layoutParams
                        dynamicClass[classNum].id = classNum
                        if(i.classTime == "#")
                            dynamicClass[classNum].setText("${i.className}\n\n${i.profName}\n\n${i.classRoom}\n\n")
                        else if(i.classTime.length <= 12)
                            dynamicClass[classNum].setText("${i.className}\n\n${i.profName}\n\n${i.classRoom}\n\n" +
                                "${i.classTime.substring(1..1)}요일 ${i.classTime.substring(2..3)}시 ~ ${i.classTime.substring(6..7)}시")
                        else
                            dynamicClass[classNum].setText("${i.className}\n\n${i.profName}\n\n${i.classRoom}\n\n" +
                                    "${i.classTime.substring(1..1)}요일 ${i.classTime.substring(2..3)}시 ~ ${i.classTime.substring(6..7)}시\n" +
                                    "${i.classTime.substring(11..11)}요일 ${i.classTime.substring(12..13)}시 ~ ${i.classTime.substring(16..17)}시")
                        dynamicClass[classNum].width = changeDP(170)
                        dynamicClass[classNum].setBackgroundColor(Color.parseColor(colorList[classNum]))
                        dynamicClass[classNum].setTextColor(Color.parseColor("#FFFFFF"))
                        buttonView.addView(dynamicClass[classNum])
                        // 동적버튼 리스너
                        dynamicClass[classNum].setOnClickListener {
                            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_classnote, bundleOf("subject" to dynamicClass.indexOf(it)))
                        }

                        searchAdapter.notifyDataSetChanged()
                        classNum++
                    }
                }

                Log.d(TAG, "new ${classList}")
            } }

        HomeViewModel.subjectList.observe(viewLifecycleOwner,subjectObserver)

        Log.d(TAG, "First ${classList}")


        root.findViewById<AutoCompleteTextView>(R.id.search_class).threshold = 1
        root.findViewById<AutoCompleteTextView>(R.id.search_class).setAdapter(searchAdapter)
        root.findViewById<AutoCompleteTextView>(R.id.search_class).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // 이동 버튼 리스너
        root.findViewById<Button>(R.id.button_main_search).setOnClickListener {
            // 검색어를 포함하는 강의가 있는지 검사
            var searchNum = -1
            for (i in 0 until classList!!.size){
                if(classList[i].contains(root.findViewById<AutoCompleteTextView>(R.id.search_class).text)){
                    if(searchNum == -1)
                        searchNum = i
                    else searchNum = -2
                }
            }
            if(searchNum == -1)
                Toast.makeText(requireContext(), "해당 강의를 찾지 못했습니다", Toast.LENGTH_SHORT).show()
            else if(searchNum == -2)
                Toast.makeText(requireContext(), "검색어를 포함한 강의가 2개 이상입니다", Toast.LENGTH_SHORT).show()
            else {
                Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_classnote, bundleOf("subjects" to this.classList))
            }
        }

        // 현재학기 설정, 학기 이동
        root.findViewById<TextView>(R.id.text_main_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")

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

        // 알림 텍스트뷰, 버튼 기능
        root.findViewById<View>(R.id.button_main_notifications).setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_notifications)
        }

        // 강의 추가 버튼
        root.findViewById<View>(R.id.button_main_addclass).setOnClickListener {
            var weekNum = 0
            var startTimeNum = 0
            var endTimeNum = 0
            var weekNum2 = 0
            var startTimeNum2 = 0
            var endTimeNum2 = 0
            var timeCnt = 1

            var builder = AlertDialog.Builder(requireContext())
            builder.setTitle("강의 추가")

            var v1 = layoutInflater.inflate(R.layout.add_dialog, null)
            builder.setView(v1)

            // 강의 추가 다이얼로그
            var listener = DialogInterface.OnClickListener { p0, p1 ->
                var alert = p0 as AlertDialog
                var edit1: EditText? = alert.findViewById<EditText>(R.id.edit_classname_add)
                var edit2: EditText? = alert.findViewById<EditText>(R.id.edit_professor_add)
                var edit3: EditText? = alert.findViewById<EditText>(R.id.edit_classroom_add)

                // 입력 조건을 달성하지 못한 경우 강의 추가 X
                if(edit1?.text?.trim()?.length == 0 || edit2?.text?.trim()?.length == 0 || (timeCnt != 0 && ((startTimeNum >= endTimeNum && timeCnt >= 1) || (startTimeNum2 >= endTimeNum2 && timeCnt == 2)))
                    || (timeCnt == 2 && weekNum == weekNum2 && ((startTimeNum >= startTimeNum2 && startTimeNum < endTimeNum2) || (startTimeNum2 >= startTimeNum && startTimeNum2 < endTimeNum)))) {
                    var toastMessage = "[강의 추가를 실패하였습니다]"

                    if(edit1?.text?.trim()?.length == 0)
                        toastMessage = "$toastMessage\n강의명을 입력해주세요!"
                    if(edit2?.text?.trim()?.length == 0)
                        toastMessage = "$toastMessage\n교수명을 입력해주세요!"
                    if(timeCnt != 0 && ((startTimeNum >= endTimeNum && timeCnt >= 1) || (startTimeNum2 >= endTimeNum2 && timeCnt == 2)))
                        toastMessage = "$toastMessage\n종료시간이 시작시간보다 같거나 빠릅니다!"
                    if(timeCnt == 2 && weekNum == weekNum2 && ((startTimeNum >= startTimeNum2 && startTimeNum < endTimeNum2) || (startTimeNum2 >= startTimeNum && startTimeNum2 < endTimeNum)))
                        toastMessage = "$toastMessage\n강의시간이 서로 겹칩니다!"

                    Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
                }
                else {
                    classList.add(edit1?.text.toString())   //******임시*******
                    searchAdapter.notifyDataSetChanged()
                    // 데이터베이스에 새로운 강의 내용 추가
                    if(timeCnt == 0)
                        Database().addNewSubject("${edit1?.text}", "${edit2?.text}", "${edit3?.text}", "#")
                    else if(timeCnt == 1)
                        Database().addNewSubject("${edit1?.text}", "${edit2?.text}", "${edit3?.text}",
                            "#" + weekList2[weekNum] + timeList2[startTimeNum] + timeList2[endTimeNum])
                    else
                        Database().addNewSubject("${edit1?.text}", "${edit2?.text}", "${edit3?.text}",
                            "#" + weekList2[weekNum] + timeList2[startTimeNum] + timeList2[endTimeNum] + "#" + weekList2[weekNum2] + timeList2[startTimeNum2] + timeList2[endTimeNum2])
                    // 현재 강좌가 없으면 일단 빈 버튼 삭제
                    if (classNum == 0)
                        Database()
                    // 새 강의 동적 버튼 추가
                    dynamicClass[classNum].layoutParams = layoutParams
                    dynamicClass[classNum].id = classNum
                    if(timeCnt == 0)
                        dynamicClass[classNum].setText("${edit1?.text}\n\n${edit2?.text}\n\n${edit3?.text}\n\n")
                    else if(timeCnt == 1)
                        dynamicClass[classNum].setText("${edit1?.text}\n\n${edit2?.text}\n\n${edit3?.text}\n\n" +
                                "${weekList[weekNum]} ${timeList[startTimeNum]} ~ ${timeList[endTimeNum]}")
                    else
                        dynamicClass[classNum].setText("${edit1?.text}\n\n${edit2?.text}\n\n${edit3?.text}\n\n" +
                                "${weekList[weekNum]} ${timeList[startTimeNum]} ~ ${timeList[endTimeNum]}\n${weekList[weekNum2]} ${timeList[startTimeNum2]} ~ ${timeList[endTimeNum2]}")
                    dynamicClass[classNum].width = changeDP(170)
                    dynamicClass[classNum].setBackgroundColor(Color.parseColor(colorList[classNum]))
                    dynamicClass[classNum].setTextColor(Color.parseColor("#FFFFFF"))
                    buttonView.addView(dynamicClass[classNum])
                    // 동적버튼 리스너
                    dynamicClass[classNum].setOnClickListener {
                        Log.d("toClass", "${this.classList}")
                        Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_classnote, bundleOf("subjects" to this.classList))
                    }
                    // 동적버튼 롱클릭 리스너(삭제)
                    dynamicClass[classNum].setOnLongClickListener {
                        var builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("강의 삭제")

                        var v1 = layoutInflater.inflate(R.layout.delete_dialog, null)
                        builder.setView(v1)
                        // 강의 삭제 다이얼로그
                        var listener2 = DialogInterface.OnClickListener { p0, p1 ->
                            val num = it.id
                            classList.removeAt(num)  //******임시*******
                            searchAdapter.notifyDataSetChanged()
                            buttonView.removeView(it)  // 현재 버튼 삭제
                            // 뒤에 있는 동적버튼 한칸씩 이동
                            for (i in num + 1 until classNum) {
                                dynamicClass[i - 1] = dynamicClass[i]
                                dynamicClass[i - 1].setBackgroundColor(Color.parseColor(colorList[i - 1]))
                            }
                            dynamicClass[classNum - 1] = Button(requireContext())
                            classNum--
                            //Log.d("태그", "내용 : "+ num + " " + classNum)
                            if (classNum == 0) {  // 강의수가 0이 되면 빈 버튼 생성
                                addEmptyButton(buttonView, requireContext())
                            }
                        }

                        builder.setPositiveButton("삭제", listener2)
                        builder.setNegativeButton("취소", null)

                        builder.show()
                        true
                    }
                    classNum++
                }
            }

            // 강의 시간 추가 버튼
            v1.findViewById<View>(R.id.button_add_addtime)?.setOnClickListener {
                if(timeCnt == 1){
                    timeCnt++
                    v1.findViewById<View>(R.id.spinner_week_add2).setVisibility(View.VISIBLE)
                    v1.findViewById<View>(R.id.spinner_starttime_add2).setVisibility(View.VISIBLE)
                    v1.findViewById<View>(R.id.spinner_endtime_add2).setVisibility(View.VISIBLE)
                } else if(timeCnt == 0){
                    timeCnt++
                    v1.findViewById<View>(R.id.spinner_week_add).setVisibility(View.VISIBLE)
                    v1.findViewById<View>(R.id.spinner_starttime_add).setVisibility(View.VISIBLE)
                    v1.findViewById<View>(R.id.spinner_endtime_add).setVisibility(View.VISIBLE)
                }
            }
            // 강의 시간 삭제 버튼
            v1.findViewById<View>(R.id.button_add_deletetime).setOnClickListener {
                if(timeCnt == 2){
                    timeCnt--
                    v1.findViewById<View>(R.id.spinner_week_add2).setVisibility(View.INVISIBLE)
                    v1.findViewById<View>(R.id.spinner_starttime_add2).setVisibility(View.INVISIBLE)
                    v1.findViewById<View>(R.id.spinner_endtime_add2).setVisibility(View.INVISIBLE)
                }
                else if(timeCnt == 1){
                    timeCnt--
                    v1.findViewById<View>(R.id.spinner_week_add).setVisibility(View.INVISIBLE)
                    v1.findViewById<View>(R.id.spinner_starttime_add).setVisibility(View.INVISIBLE)
                    v1.findViewById<View>(R.id.spinner_endtime_add).setVisibility(View.INVISIBLE)
                }
            }

            // 강의 요일 스피너
            val weekSpinner = v1.findViewById<Spinner>(R.id.spinner_week_add)
            weekSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, weekList)
            weekSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    weekNum = position
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            // 강의 시작시간 스피너
            val startTimeSpinner = v1.findViewById<Spinner>(R.id.spinner_starttime_add)
            startTimeSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeList)
            startTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    startTimeNum = position
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            // 강의 종료시간 스피너
            val endTimeSpinner = v1.findViewById<Spinner>(R.id.spinner_endtime_add)
            endTimeSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeList)
            endTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    endTimeNum = position
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            // 2번째 강의 요일 스피너
            val weekSpinner2 = v1.findViewById<Spinner>(R.id.spinner_week_add2)
            weekSpinner2.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, weekList)
            weekSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    weekNum2 = position
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            // 2번째 강의 시작시간 스피너
            val startTimeSpinner2 = v1.findViewById<Spinner>(R.id.spinner_starttime_add2)
            startTimeSpinner2.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeList)
            startTimeSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    startTimeNum2 = position
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            // 2번째 강의 종료시간 스피너
            val endTimeSpinner2 = v1.findViewById<Spinner>(R.id.spinner_endtime_add2)
            endTimeSpinner2.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeList)
            endTimeSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    endTimeNum2 = position
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            builder.setPositiveButton("입력", listener)
            builder.setNegativeButton("취소", null)

            builder.show()
        }

        // 성적 버튼 기능
        root.findViewById<View>(R.id.button_main_grade).setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_grade)
        }

        // 캘린더 기능
        root.findViewById<View>(R.id.button_main_calender).setOnClickListener {
            var myIntent = Intent(requireContext(), CalendarActivity::class.java)
            var classTimeList : ArrayList<String> = arrayListOf()

            for (i in 0 until subjectInfoList!!.size)
                classTimeList.add(subjectInfoList[i].classTime)
            myIntent.putExtra("name", classList)
            myIntent.putExtra("time", classTimeList)
            startActivity(myIntent)
        }

        // 시간표 보기
        root.findViewById<View>(R.id.button_main_timetable).setOnClickListener {
            startActivity(Intent(requireContext(), TimeScheduleActivity::class.java))
        }

        return root
    }

    private fun changeDP(value : Int) : Int{      // Int값을 DP값으로 바꿈(컬러)
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }

    private fun addEmptyButton(buttonView : LinearLayout, cont : Context){  // 빈 버튼 생성 함수
        val addRequest = Button(cont)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        addRequest.layoutParams = layoutParams
        addRequest.setText("현재 강의가 없습니다")
        addRequest.width = changeDP(170)
        addRequest.setBackgroundColor(Color.parseColor("#808080"))
        addRequest.setTextColor(Color.parseColor("#FFFFFF"))
        addRequest.gravity = Gravity.CENTER
        buttonView.addView(addRequest)
    }
}