package com.example.kaumobile.ui.classNote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaumobile.R
import com.example.sswolf.kausugang.Seong.TemplateAdapter
import com.example.sswolf.kausugang.Seong.TemplateItem


class ClassNoteFragment : Fragment() {
    var SubjectTmp = listOf<String>( "안드로이드", "요하문명의 이해", "모바일SW스튜디오")
    var pos = 0
    private lateinit var classNoteViewModel: ClassNoteViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_class_note, container, false)

        val template:MutableList<TemplateItem> = loadTemplate()
        var adapter = TemplateAdapter()
        adapter.listData = template

        root.findViewById<RecyclerView>(R.id.templateView).adapter = adapter
        root.findViewById<RecyclerView>(R.id.templateView).layoutManager = LinearLayoutManager(context)

        root.findViewById<ImageView>(R.id.btnPrev).setOnClickListener{ getPrevClass() }
        root.findViewById<ImageView>(R.id.btnNext).setOnClickListener { getNextClass() }

        return root
    }

    fun getPrevClass(){
        if(pos-1 < 0) pos = 3
        pos = (pos-1)%3
        view?.findViewById<TextView>(R.id.textClass)?.text = "과목명: " + "${SubjectTmp[pos]}"
        //Log.d("test","Prev+${SubjectTmp[pos]}")
    }

    fun getNextClass(){
        pos = (pos+1)%3
        view?.findViewById<TextView>(R.id.textClass)?.text = "과목명: " + "${SubjectTmp[pos]}"
        Log.d("test","Next")
    }

    fun loadTemplate(): MutableList<TemplateItem> {
        val data:MutableList<TemplateItem> = mutableListOf()
        for (no in 1..16){
            data.add(TemplateItem(no, true, false, false))
        }

        return data
    }


}