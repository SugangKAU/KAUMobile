package com.example.kaumobile.ui.grade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GradeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is grade Fragment"
    }
    val text: LiveData<String> = _text
}