package com.example.sswolf.kausugang.Seong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_note.*
import com.example.sswolf.kausugang.Subject

class NoteActivity : AppCompatActivity() {

    val subject = Subject("안드로이드","김철기","과학관 212","목요일 9시~13시")
    val noteType: String = "예습"
    val num: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        // subject = intent.getextra

        textTitle.text = subject.className
        textNoteTitle.text = noteType + "노트 - " + num + "주차"
    }
}
