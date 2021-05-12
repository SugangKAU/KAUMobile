package com.example.kaumobile.ui.grade

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kaumobile.R

class GradeFragment : Fragment() {
    private var year = 2021
    private var semester = 1
    private var pos = 0
    var subjectTmp = listOf<String>( "안드로이드", "요하문명의 이해", "모바일SW스튜디오" )
    var gradeTmp = listOf<String>( "B+", "A", "A", "B", "B", "A", "B", "C", "B", "C+", "B+", "B", "A+", "A+", "A+", "A" )

    private lateinit var gradeViewModel: GradeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        gradeViewModel =
                ViewModelProvider(this).get(GradeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_grade, container, false)

        //val subjectList = arguments?.getStringArrayList("subjects")

        root.findViewById<TextView>(R.id.text_grade_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")

        root.findViewById<View>(R.id.button_grade_prevsemester).setOnClickListener {
            year -= (2-semester)
            semester = 3-semester
            root.findViewById<TextView>(R.id.text_grade_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")
            root.findViewById<TextView>(R.id.text_grade_full).setText("${gradeTmp[((year * 2 + semester + 1) % 4) * 4]}")
            root.findViewById<TextView>(R.id.text_grade_prepare).setText("${gradeTmp[((year * 2 + semester + 1) % 4) * 4 + 1]}")
            root.findViewById<TextView>(R.id.text_grade_review).setText("${gradeTmp[((year * 2 + semester + 1) % 4) * 4 + 2]}")
            root.findViewById<TextView>(R.id.text_grade_assign).setText("${gradeTmp[((year * 2 + semester + 1) % 4) * 4 + 3]}")
        }

        root.findViewById<View>(R.id.button_grade_nextsemester).setOnClickListener {
            year += (semester-1)
            semester = 3-semester
            root.findViewById<TextView>(R.id.text_grade_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")
            root.findViewById<TextView>(R.id.text_grade_full).setText("${gradeTmp[((year * 2 + semester + 1) % 4) * 4]}")
            root.findViewById<TextView>(R.id.text_grade_prepare).setText("${gradeTmp[((year * 2 + semester + 1) % 4) * 4 + 1]}")
            root.findViewById<TextView>(R.id.text_grade_review).setText("${gradeTmp[((year * 2 + semester + 1) % 4) * 4 + 2]}")
            root.findViewById<TextView>(R.id.text_grade_assign).setText("${gradeTmp[((year * 2 + semester + 1) % 4) * 4 + 3]}")
        }

        root.findViewById<View>(R.id.button_grade_prevsubject).setOnClickListener {
            if(pos == 0)
                pos = subjectTmp!!.size - 1
            else pos--
            root.findViewById<TextView>(R.id.text_grade_subject).setText("과목명 : " + "${subjectTmp!![pos]}")
        }
        root.findViewById<View>(R.id.button_grade_nextsubject).setOnClickListener {
            if(pos == subjectTmp!!.size - 1)
                pos = 0
            else pos++
            root.findViewById<TextView>(R.id.text_grade_subject).setText("과목명 : " + "${subjectTmp!![pos]}")
        }

        return root
    }
}