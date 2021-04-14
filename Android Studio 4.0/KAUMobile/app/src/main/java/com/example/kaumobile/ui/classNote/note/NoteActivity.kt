package com.example.sswolf.kausugang.Seong

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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
                //알림창
                val infalter = ContextCompat.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popup = infalter.inflate(R.layout.alert_dialog, null)

                val popup = AlertDialog.Builder(this)
                    .setTitle("AlertDialog CreateNote")
                    .setPositiveButton("확인"){
                        //노트 클래스 생성
                    }
                    .setNeutralButton("취소",null)
                    .create()

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
