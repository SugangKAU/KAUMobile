package com.example.sswolf.kausugang.Seong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kaumobile.R
import kotlinx.android.synthetic.main.activity_assign.*

class AssignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign)

        textTitle.text = "과제 1주차"
    }
}
