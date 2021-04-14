package com.example.sswolf.kausugang.Seong

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.kaumobile.R
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class NoteActivity : AppCompatActivity(){

    val subject = Subject("안드로이드","김철기","과학관 212","목요일 9시~13시")
    val noteType: String = "예습"
    val num: Int = 1
    lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        // subject = intent.getextra

        findViewById<TextView>(R.id.textClass).text = subject.className
        findViewById<TextView>(R.id.textNoteTitle).text = noteType + "노트 - " + num + "주차"
        findViewById<Button>(R.id.okButton).setOnClickListener{
            showDialog()
        }
        findViewById<Button>(R.id.cancelButton).setOnClickListener{}

        FirebaseApp.initializeApp(this)
        database = Firebase.database.reference

    }


    fun showDialog(){
        val infalter = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = infalter.inflate(R.layout.alert_dialog, null)
        val text = view.findViewById<TextView>(R.id.describeText)

        text.text = "노트를 저장하시겠습니까?"

        val popup = AlertDialog.Builder(this)
            .setTitle("노트 저장")
            .setPositiveButton("확인"){ dialog, which->
                saveFirebase()
                //노트 클래스 생성
            }
            .setNeutralButton("취소",null)
            .create()
        popup.setView(view)
        popup.show()
    }

    fun saveFirebase(){
        var firebaseRef = database
        val text = findViewById<EditText>(R.id.editText).text.toString()
        firebaseRef.setValue(text)
    }

//    override fun onClick(v: View) {
//        when(v.id){
//            R.id.okButton->{
//                //알림창
//                val infalter = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//                val view = infalter.inflate(R.layout.alert_dialog, null)
//
//                val popup = AlertDialog.Builder(this)
//                    .setTitle("AlertDialog CreateNote")
//                    .setPositiveButton("확인"){ dialog, which->
//                        //노트 클래스 생성
//                    }
//                    .setNeutralButton("취소",null)
//                    .create()
//                Toast.makeText(applicationContext, "hi?", Toast.LENGTH_LONG).show()
//                popup.setView(view)
//                popup.show()
//            }
//            R.id.cancelButton->{
//                //뒤로가기
//            }
//
//        }
//    }

}
