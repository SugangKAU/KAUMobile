package com.example.sswolf.kausugang.Seong

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.kaumobile.R
import com.example.kaumobile.firebase.Database
import com.google.firebase.FirebaseApp
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException



class NoteActivity : AppCompatActivity(){
    private val TAG = "Note"
    var semester: String = ""
    var className: String = ""
    var noteType: String = ""
    var no: Int = 0
    var newNote = false
    lateinit var note: EditText
    //lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        get_from_intent()

        note = findViewById<EditText>(R.id.note)

        findViewById<TextView>(R.id.textClass).text = className
        findViewById<TextView>(R.id.textNoteTitle).text = noteType + "노트 - " + no + "주차"

        val noteRef = Database().getNoteRef(semester, className, noteType, no)
        noteRef.get().addOnSuccessListener {
            try{
                var note_txt = it.get("${noteType}") as String
                note.setText(note_txt)
                Log.d("note", "note success load")
            }catch (e: NullPointerException){
                Log.d("note", "no field")
                newNote = true
            }
        }

        findViewById<Button>(R.id.okButton).setOnClickListener{
            showDialog()
        }
        findViewById<Button>(R.id.cancelButton).setOnClickListener{
            onBackPressed()
        }

        FirebaseApp.initializeApp(this)
      //  database = Firebase.database.reference

    }

    fun get_from_intent(){
        semester = intent.getStringExtra("Semester")!!
        className = intent.getStringExtra("Subject")!!
        noteType = intent.getStringExtra("Type")!!
        no = intent.getIntExtra("Week", 0)
    }


    fun showDialog(){
        val infalter = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = infalter.inflate(R.layout.alert_dialog, null)
        val text = view.findViewById<TextView>(R.id.describeText)

        text.text = "노트를 저장하시겠습니까?"

        val popup = AlertDialog.Builder(this)
            .setTitle("노트 저장")
            .setPositiveButton("확인"){ dialog, which->
                val db = Database()
                if (newNote) {
                    Log.d(TAG,"Create Note")
                    db.createNote(semester, className, noteType, no, note.text.toString())
                }
                else {
                    Log.d(TAG,"Edit Note")
                    db.editNote(semester, className, noteType, no, note.text.toString())
                }
                Log.d(TAG,"Done")
                onBackPressed()
            }
            .setNeutralButton("취소",null)
            .create()
        popup.setView(view)
        popup.show()
    }

//    fun saveFirebase(){
//        var firebaseRef = database
//        val text = findViewById<EditText>(R.id.editText).text.toString()
//        firebaseRef.child(num.toString()+"주차"+noteType).setValue(text)
//    }

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
