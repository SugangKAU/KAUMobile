package com.example.kaumobile.ui.grade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kaumobile.R

class GradeFragment : Fragment() {

    private lateinit var gradeViewModel: GradeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        gradeViewModel =
                ViewModelProvider(this).get(GradeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_grade, container, false)
        val textView: TextView = root.findViewById(R.id.text_grade)
        gradeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}