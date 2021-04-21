package com.example.kaumobile.ui.home

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kaumobile.R
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation

class HomeFragment : Fragment() {
    private var year = 2021;
    private var semester = 1;
    private val weekList = arrayOf("월요일","화요일","수요일","목요일","금요일")
    private val timeList = arrayOf("9시","10시","11시","12시","13시","14시","15시","16시","17시","18시")

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        root.findViewById<TextView>(R.id.text_main_semester).setText(year.toString() + "년도 " + semester.toString() + "학기")

        root.findViewById<View>(R.id.button_main_notifications).setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_notifications)
        }

        root.findViewById<View>(R.id.button_class1).setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_classnote)
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
            var builder = AlertDialog.Builder(requireContext())
            builder.setTitle("강의 추가")

            var v1 = layoutInflater.inflate(R.layout.add_dialog, null)
            builder.setView(v1)

            var listener = DialogInterface.OnClickListener { p0, p1 ->
                var alert = p0 as AlertDialog
                var edit1: EditText? = alert.findViewById<EditText>(R.id.edit_classname_add)
                var edit2: EditText? = alert.findViewById<EditText>(R.id.edit_professor_add)
                var edit3: EditText? = alert.findViewById<EditText>(R.id.edit_classroom_add)
            }

            val weekSpinner = root.findViewById<Spinner>(R.id.spinner_week_add)
            weekSpinner?.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, weekList)
            weekSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when(position) {
                        0   ->  {
                        }
                        1   ->  {
                        }
                        2   ->  {
                        }
                        3   ->  {
                        }
                        else -> {
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            val startTimeSpinner = root.findViewById<Spinner>(R.id.spinner_starttime_add)
            startTimeSpinner?.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeList)
            startTimeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            val endTimeSpinner = root.findViewById<Spinner>(R.id.spinner_endtime_add)
            endTimeSpinner?.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeList)
            endTimeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            builder.setPositiveButton("입력", listener)
            builder.setNegativeButton("취소", null)

            builder.show()
        }

        root.findViewById<View>(R.id.button_class1).setOnLongClickListener {
            var builder = AlertDialog.Builder(requireContext())
            builder.setTitle("강의 삭제")

            var v1 = layoutInflater.inflate(R.layout.delete_dialog, null)
            builder.setView(v1)

            var listener = DialogInterface.OnClickListener { p0, p1 -> }

            builder.setPositiveButton("삭제", listener)
            builder.setNegativeButton("취소", null)

            builder.show()
            true
        }

        root.findViewById<View>(R.id.button_class2).setOnLongClickListener {
            var builder = AlertDialog.Builder(requireContext())
            builder.setTitle("강의 삭제")

            var v1 = layoutInflater.inflate(R.layout.delete_dialog, null)
            builder.setView(v1)

            var listener = DialogInterface.OnClickListener { p0, p1 -> }

            builder.setPositiveButton("삭제", listener)
            builder.setNegativeButton("취소", null)

            builder.show()
            true
        }

        root.findViewById<View>(R.id.button_class3).setOnLongClickListener {
            var builder = AlertDialog.Builder(requireContext())
            builder.setTitle("강의 삭제")

            var v1 = layoutInflater.inflate(R.layout.delete_dialog, null)
            builder.setView(v1)

            var listener = DialogInterface.OnClickListener { p0, p1 -> }

            builder.setPositiveButton("삭제", listener)
            builder.setNegativeButton("취소", null)

            builder.show()
            true
        }

        return root
    }
}