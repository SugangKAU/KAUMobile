package com.example.kaumobile.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kaumobile.firebase.Database

class HomeViewModel : ViewModel() {
    private val TAG = "HomeVM"
    val subjectList : MutableLiveData<ArrayList<Subject>> by lazy {
        MutableLiveData<ArrayList<Subject>>(arrayListOf())
    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        Log.d(TAG,"Hi")
        Database().getSemester("2021년 1학기").addSnapshotListener { snapshot, error ->
            val _new:ArrayList<Subject> = arrayListOf()//subjectList.value
            for (i in snapshot!!.documents){
                Log.d(TAG,"Add ${i.data}")
                _new!!.add(Subject(i.data!!.get("강의명") as String, i.data!!.get("강의시간") as String, i.data!!.get("강의실") as String, i.data!!.get("교수명") as String))
                //_new!!.add(i.toObject(Subject::class.java)!!)
                Log.d(TAG,"Add ${subjectList.value}")
            }
            subjectList.value = _new
            Log.d(TAG,"Add ${subjectList.value}")
        }
    }
}