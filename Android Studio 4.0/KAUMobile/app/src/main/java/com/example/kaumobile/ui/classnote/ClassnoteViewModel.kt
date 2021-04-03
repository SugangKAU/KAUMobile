package com.example.kaumobile.ui.classnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClassnoteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is classnote Fragment"
    }
    val text: LiveData<String> = _text
}