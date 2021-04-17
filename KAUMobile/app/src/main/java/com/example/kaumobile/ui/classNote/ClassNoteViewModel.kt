package com.example.kaumobile.ui.classNote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClassNoteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is classnote Fragment"
    }
    val text: LiveData<String> = _text
}