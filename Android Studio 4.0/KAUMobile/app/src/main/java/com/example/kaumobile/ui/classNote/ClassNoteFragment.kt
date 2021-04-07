package com.example.kaumobile.ui.classNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaumobile.R
import com.example.sswolf.kausugang.Seong.TemplateAdapter
import com.example.sswolf.kausugang.Seong.TemplateItem

class ClassNoteFragment : Fragment() {

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


        return root
    }


    fun loadTemplate(): MutableList<TemplateItem> {
        val data:MutableList<TemplateItem> = mutableListOf()
        for (no in 1..16){
            data.add(TemplateItem(no, true, false, false))
        }

        return data
    }


}