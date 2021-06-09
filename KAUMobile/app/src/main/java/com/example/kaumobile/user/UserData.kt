package com.example.kaumobile.user

import android.util.Log
import com.example.kaumobile.firebase.Database
import com.example.kaumobile.ui.home.HomeViewModel
import com.example.kaumobile.ui.home.Subject

object UserData {
    private val TAG = "User"
    var subjectList :ArrayList<Subject> = arrayListOf()

    init {
        Log.d(TAG, "Hi")
        Database().getSemester("2021년 1학기").addSnapshotListener { snapshot, error ->
            //subjectList.value
            for (i in snapshot!!.documents) {
                subjectList.add(
                    Subject(
                        i.data!!.get("강의명") as String,
                        i.data!!.get("강의시간") as String,
                        i.data!!.get("강의실") as String,
                        i.data!!.get("교수명") as String,
                        i.reference.parent.id as String
                    )
                )
                Log.d(TAG, "Add ${i.data}")
                //_new!!.add(i.toObject(Subject::class.java)!!)
            }
        }
    }
}