package com.example.kaumobile.ui.grade

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.kaumobile.R
import com.example.kaumobile.firebase.Database
import com.example.kaumobile.ui.home.Subject
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class GradeFragment : Fragment() {
    private var year = 2021
    private var semester = 1
    private var pos = 0
    var gradeTmp = listOf<String>( "B+", "A", "A", "B", "B", "A", "B", "C", "B", "C+", "B+", "B", "A+", "A+", "A+", "A" )
    var classList : MutableList<String> = mutableListOf()
    var countList : MutableList<Int> = mutableListOf()

    private lateinit var gradeViewModel: GradeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gradeViewModel =
            ViewModelProvider(this).get(GradeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_grade, container, false)

        val searchAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, classList!!)

        val subjectObserver : Observer<ArrayList<Subject>> by lazy {
            Observer<ArrayList<Subject>>{ _subjectList->
                Log.d("???","${_subjectList}")
                classList.clear()
                for (i in _subjectList) {
                    classList.add(i.name)
                }
                if(classList!!.size != 0) {
                    root.findViewById<TextView>(R.id.text_grade_subject).setText("과목명 : " + "${classList!![0]}")
                    root.findViewById<View>(R.id.text_grade_full).setVisibility(View.VISIBLE)
                    root.findViewById<View>(R.id.text_grade_prepare).setVisibility(View.VISIBLE)
                    root.findViewById<View>(R.id.text_grade_review).setVisibility(View.VISIBLE)
                    root.findViewById<View>(R.id.text_grade_assign).setVisibility(View.VISIBLE)
                    root.findViewById<TextView>(R.id.text_grade_full).setText("${gradeTmp[(pos*4)%16]}")
                    root.findViewById<TextView>(R.id.text_grade_prepare).setText("${gradeTmp[(pos*4)%16+1]}")
                    root.findViewById<TextView>(R.id.text_grade_review).setText("${gradeTmp[(pos*4)%16+2]}")
                    root.findViewById<TextView>(R.id.text_grade_assign).setText("${gradeTmp[(pos*4)%16+3]}")
                    //root.findViewById<TextView>(R.id.text_grade_full).setText("${grading((countList[pos*3]+countList[pos*3+1]+countList[pos*3+2]).toDouble()/3.0)}")
                    //root.findViewById<TextView>(R.id.text_grade_prepare).setText("${grading(countList[pos*3].toDouble())}")
                    //root.findViewById<TextView>(R.id.text_grade_review).setText("${grading(countList[pos*3+1].toDouble())}")
                    //root.findViewById<TextView>(R.id.text_grade_assign).setText("${grading(countList[pos*3+2].toDouble())}")
                }
                else {
                    root.findViewById<TextView>(R.id.text_grade_subject).setText("학기 내 강의가 없습니다")
                    root.findViewById<View>(R.id.text_grade_full).setVisibility(View.INVISIBLE)
                    root.findViewById<View>(R.id.text_grade_prepare).setVisibility(View.INVISIBLE)
                    root.findViewById<View>(R.id.text_grade_review).setVisibility(View.INVISIBLE)
                    root.findViewById<View>(R.id.text_grade_assign).setVisibility(View.INVISIBLE)
                }
                searchAdapter.notifyDataSetChanged()
            }
        }

        val gradeObserver : Observer<ArrayList<Subject>> by lazy {
            Observer<ArrayList<Subject>> { _subjectList ->
            }
        }
        gradeViewModel.subjectList.observe(viewLifecycleOwner,subjectObserver)

        root.findViewById<TextView>(R.id.text_grade_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")

        root.findViewById<View>(R.id.button_grade_prevsemester).setOnClickListener {
            year -= (2-semester)
            semester = 3-semester
            root.findViewById<TextView>(R.id.text_grade_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")
        }

        root.findViewById<View>(R.id.button_grade_nextsemester).setOnClickListener {
            year += (semester-1)
            semester = 3-semester
            root.findViewById<TextView>(R.id.text_grade_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")
        }

        root.findViewById<View>(R.id.button_grade_prevsubject).setOnClickListener {
            if(classList!!.size != 0) {
                if (pos == 0)
                    pos = classList!!.size - 1
                else pos--
                root.findViewById<TextView>(R.id.text_grade_subject).setText("과목명 : " + "${classList!![pos]}")
                root.findViewById<TextView>(R.id.text_grade_full).setText("${gradeTmp[(pos*4)%16]}")
                root.findViewById<TextView>(R.id.text_grade_prepare).setText("${gradeTmp[(pos*4)%16+1]}")
                root.findViewById<TextView>(R.id.text_grade_review).setText("${gradeTmp[(pos*4)%16+2]}")
                root.findViewById<TextView>(R.id.text_grade_assign).setText("${gradeTmp[(pos*4)%16+3]}")
            }
        }
        root.findViewById<View>(R.id.button_grade_nextsubject).setOnClickListener {
            if(classList!!.size != 0) {
                if (pos == classList!!.size - 1)
                    pos = 0
                else pos++
                root.findViewById<TextView>(R.id.text_grade_subject).setText("과목명 : " + "${classList!![pos]}")
                root.findViewById<TextView>(R.id.text_grade_full).setText("${gradeTmp[(pos*4)%16]}")
                root.findViewById<TextView>(R.id.text_grade_prepare).setText("${gradeTmp[(pos*4)%16+1]}")
                root.findViewById<TextView>(R.id.text_grade_review).setText("${gradeTmp[(pos*4)%16+2]}")
                root.findViewById<TextView>(R.id.text_grade_assign).setText("${gradeTmp[(pos*4)%16+3]}")
            }
        }

        root.findViewById<View>(R.id.button_grade_graph1).setOnClickListener {
            if(classList!!.size != 0) {
                var builder = AlertDialog.Builder(requireContext())
                builder.setTitle("과목별 수행 그래프")

                var v1 = layoutInflater.inflate(R.layout.graph_dialog, null)
                builder.setView(v1)
                v1.findViewById<TextView>(R.id.text_graph_class).setText("과목명 : " + "${classList!![pos]}")

                val values = mutableListOf<BarEntry>()
                values.add(BarEntry(1f, 10f))
                values.add(BarEntry(2f, 14f))
                values.add(BarEntry(3f, 8f))
                //values.add(BarEntry(1f, countList[pos*3].toFloat()))
                //values.add(BarEntry(1f, countList[pos*3+1].toFloat()))
                //values.add(BarEntry(1f, countList[pos*3+2].toFloat()))

                val set = BarDataSet(values, "없음").apply {
                    setDrawIcons(false)
                    setDrawValues(true)
                    colors = listOf(
                        Color.rgb(79, 208, 45),
                        Color.rgb(140, 234, 255),
                        Color.rgb(255, 140, 157)
                    )
                    valueFormatter = BarScoreFormatter()
                    valueTextColor = Color.DKGRAY
                }

                val dataSets = mutableListOf<IBarDataSet>()
                dataSets.add(set)

                val data = BarData(dataSets)
                    .apply {
                        setValueTextSize(10f)
                        barWidth = 0.5f
                    }

                var chart = v1.findViewById<BarChart>(R.id.barChart)
                chart.run{
                    setDrawBarShadow(true)
                    setTouchEnabled(false)
                    setDrawValueAboveBar(true)
                    setPinchZoom(false)
                    setDrawGridBackground(false)
                    description.isEnabled = false
                    legend.isEnabled = false

                    xAxis.run {
                        isEnabled = true
                        valueFormatter = BarLabelFormatter()
                        granularity = 1f
                        setDrawGridLines(false)
                        setDrawAxisLine(false)
                        textColor = Color.BLACK
                    }
                    axisLeft.run {
                        isEnabled = false
                        setDrawLabels(false)
                        axisMinimum = 0f // 최소값
                        axisMaximum = 16f // 최대값
                    }
                    axisRight.run {
                        isEnabled = false
                    }
                }
                chart.data = data

                builder.setNegativeButton("닫기", null)
                builder.show()
            }
        }

        root.findViewById<View>(R.id.button_grade_graph2).setOnClickListener {
            if(classList!!.size != 0) {
                var year2 = year
                var semester2 = semester
                var builder = AlertDialog.Builder(requireContext())
                builder.setTitle("학기별 성적 그래프")

                var v1 = layoutInflater.inflate(R.layout.graph2_dialog, null)
                builder.setView(v1)
                v1.findViewById<TextView>(R.id.text_graph2_semester).setText("${year2}년도 ${semester2}학기")

                v1.findViewById<View>(R.id.button_graph2_prevclass).setOnClickListener {
                    year2 -= (2-semester2)
                    semester2 = 3-semester2
                    v1.findViewById<TextView>(R.id.text_graph2_semester).setText("${year2}년도 ${semester2}학기")
                }

                v1.findViewById<View>(R.id.button_graph2_nextclass).setOnClickListener {
                    year2 += (semester2-1)
                    semester2 = 3-semester2
                    v1.findViewById<TextView>(R.id.text_graph2_semester).setText("${year2}년도 ${semester2}학기")
                }

                val values = mutableListOf<Entry>()
                for (i in 0 until classList!!.size) {
                    values.add(BarEntry((i+1).toFloat(), gradeToScore(gradeTmp[(i*4)%16])))
                    //values.add(BarEntry((i+1).toFloat(), gradeToScore(grading((countList[pos*3]+countList[pos*3+1]+countList[pos*3+2]).toDouble()/3.0))))
                }

                val set = LineDataSet(values, "없음").apply {
                    setDrawIcons(false)
                    setDrawValues(true)
                    valueFormatter = LineScoreFormatter()
                    valueTextColor = Color.DKGRAY
                }

                val dataSets = mutableListOf<ILineDataSet>()
                dataSets.add(set)

                val data = LineData(dataSets)
                    .apply {
                        setValueTextSize(10f)
                    }

                var chart = v1.findViewById<LineChart>(R.id.lineChart)

                chart.run{
                    setTouchEnabled(false)
                    setPinchZoom(false)
                    setDrawGridBackground(false)
                    description.isEnabled = false
                    legend.isEnabled = false

                    xAxis.run {
                        spaceMax = 0.5f
                        spaceMin = 0.5f
                        isEnabled = true
                        valueFormatter = LineLabelFormatter()
                        granularity = 1f
                        setDrawGridLines(false)
                        setDrawAxisLine(false)
                        textColor = Color.BLACK
                    }
                    axisLeft.run {
                        isEnabled = false
                        setDrawLabels(false)
                        axisMinimum = 0f // 최소값
                        axisMaximum = 5f // 최대값
                    }
                    axisRight.run {
                        isEnabled = false
                    }
                }
                chart.data = data

                builder.setNegativeButton("닫기", null)
                builder.show()
            }
        }

        return root
    }

    class BarLabelFormatter : ValueFormatter() {
        private var index = 0

        override fun getFormattedValue(value: Float): String {
            index = value.toInt()
            return when (index) {
                1 -> "예습"
                2 -> "복습"
                3 -> "과제"

                else -> throw IndexOutOfBoundsException("index out ${index}")
            }
        }

        override fun getBarStackedLabel(value: Float, stackedEntry: BarEntry?): String {
            return super.getBarStackedLabel(value, stackedEntry)
        }
    }

    inner class LineLabelFormatter : ValueFormatter() {
        private var index = 0

        override fun getFormattedValue(value: Float): String {
            index = value.toInt()
            return when (index) {
                1 -> cutSubject(classList!![0])
                2 -> cutSubject(classList!![1])
                3 -> cutSubject(classList!![2])
                4 -> cutSubject(classList!![3])
                5 -> cutSubject(classList!![4])
                6 -> cutSubject(classList!![5])
                7 -> cutSubject(classList!![6])
                8 -> cutSubject(classList!![7])
                9 -> cutSubject(classList!![8])
                10 -> cutSubject(classList!![9])
                else -> ""
            }
        }

        override fun getBarStackedLabel(value: Float, stackedEntry: BarEntry?): String {
            return super.getBarStackedLabel(value, stackedEntry)
        }
    }

    class BarScoreFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val score = value.toString().split(".")
            return score[0] + "주"
        }
    }

    class LineScoreFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return GradeFragment().scoreToGrade(value) + "(${"%.1f".format(value)})"

        }
    }

    fun cutSubject(subject: String): String {
        if(subject.length <= 4)
            return subject
        else
            return subject.slice(IntRange(0,3))
    }

    fun scoreToGrade(value: Float): String {
        var grade = ""
        if(value < 0.75)
            grade = "F"
        else if(value < 1.25)
            grade = "D"
        else if(value < 1.75)
            grade = "D+"
        else if(value < 2.25)
            grade = "C"
        else if(value < 2.75)
            grade = "C+"
        else if(value < 3.25)
            grade = "B"
        else if(value < 3.75)
            grade = "B+"
        else if(value < 4.25)
            grade = "A"
        else
            grade = "A+"
        return grade
    }

    fun gradeToScore(grade: String): Float {
        if(grade == "A+")
            return 4.5f
        else if(grade == "A")
            return 4.0f
        else if(grade == "B+")
            return 3.5f
        else if(grade == "B")
            return 3.0f
        else if(grade == "C+")
            return 2.5f
        else if(grade == "C")
            return 2.0f
        else if(grade == "D+")
            return 1.5f
        else if(grade == "D")
            return 1.0f
        else
            return 0.0f
    }

    fun grading(count: Double): String {
        if(count < 1.0)
            return "F"
        else if(count < 3.0)
            return "D"
        else if(count < 5.0)
            return "D+"
        else if(count < 7.0)
            return "C"
        else if(count < 9.0)
            return "C+"
        else if(count < 11.0)
            return "B"
        else if(count < 13.0)
            return "B+"
        else if(count < 15.0)
            return "A"
        else
            return "A+"
    }
}