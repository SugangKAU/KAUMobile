package com.example.kaumobile.ui.classNote.note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.kaumobile.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class NoteActivity : AppCompatActivity(), View.OnClickListener {

    val subject = Subject("안드로이드","김철기","과학관 212","목요일 9시~13시")
    val noteType: String = "예습"
    val num: Int = 1
    lateinit var storage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        // subject = intent.getextra

        findViewById<TextView>(R.id.textClass).text = subject.className
        findViewById<TextView>(R.id.textNoteTitle).text = noteType + "노트 - " + num + "주차"
//        findViewById<Button>(R.id.okButton).setOnClickListener()
//        findViewById<Button>(R.id.cancelButton).setOnClickListener()

        storage = Firebase.storage

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.okButton->{
                //파이어베이스에 전송
            }
            R.id.cancelButton->{
                //뒤로가기
            }

        }
    }

    fun firebaseSave(){
        val fbHandler = Firebase.storage
    }
}
