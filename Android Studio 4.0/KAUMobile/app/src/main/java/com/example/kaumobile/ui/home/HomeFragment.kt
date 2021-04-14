package com.example.kaumobile.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kaumobile.R
import android.graphics.Color
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.findFragment
import com.example.kaumobile.ui.classNote.ClassNoteFragment
import com.example.kaumobile.ui.grade.GradeFragment
import com.example.kaumobile.ui.notifications.NotificationsFragment
import com.example.kaumobile.ui.notifications.NotificationsViewModel

class HomeFragment : Fragment() {
    var year = 2021;
    var semester = 1;
    var list_of_week = arrayOf("월요일","화요일","수요일","목요일","금요일")
    var list_of_time = arrayOf("9시","10시","11시","12시","13시","14시","15시","16시","17시","18시")

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
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, NotificationsFragment()).commit()
        }

        root.findViewById<View>(R.id.button_class1).setOnClickListener {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, ClassNoteFragment()).commit()
        }

        root.findViewById<View>(R.id.button_main_grade).setOnClickListener {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, GradeFragment()).commit()
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

            /*root.findViewById<Spinner>(R.id.spinner_week_add).adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, list_of_week)

            root.findViewById<Spinner>(R.id.spinner_week_add).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }*/

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