package com.example.kaumobile.ui.classNote.assign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.kaumobile.R

class AssignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign)

        val subject = intent.extras!!.getString("subject")
        val n = intent.extras!!.getInt("no")

        findViewById<TextView>(R.id.textClass).text = "과제 ${n}주차"
    }
}
