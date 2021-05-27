package com.example.kaumobile.ui.classNote.assign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.kaumobile.R

class AssignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign)

        findViewById<TextView>(R.id.textClass).text = "과제 1주차"
    }
}
