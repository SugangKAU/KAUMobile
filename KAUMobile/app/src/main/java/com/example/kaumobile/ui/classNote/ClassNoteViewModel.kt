package com.example.kaumobile.ui.classNote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kaumobile.firebase.Database
import com.example.kaumobile.ui.classNote.classNoteAdapter.TemplateItem
import com.example.kaumobile.ui.home.Subject

class ClassNoteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is classnote Fragment"
    }
    val text: LiveData<String> = _text
    val subject: MutableLiveData<Subject> by lazy {
        MutableLiveData<Subject>()
    }
    val template: MutableLiveData<ArrayList<TemplateItem>> by lazy {
        MutableLiveData<ArrayList<TemplateItem>>()
    }
    val db:Database = Database()



    fun loadTemplate(): ArrayList<TemplateItem> {
        val data:ArrayList<TemplateItem> = arrayListOf()
        for (no in 1..16){
            data.add(TemplateItem(no, 0, 0))
        }

        return data
    }
}