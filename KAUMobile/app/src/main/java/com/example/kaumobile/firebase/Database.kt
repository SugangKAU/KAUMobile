package com.example.kaumobile.firebase

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.util.*


class Database {
     var name = "KAU" // 앱 시작시 지정
     var now_semester = "2021년 1학기"
     val dbHandler: FirebaseFirestore = FirebaseFirestore.getInstance()
    val TAG = "DEBUG"

     fun addNewUser(name: String) {
          //어플 처음 실행시 Document 생성
         this.name = name
          val default_setting = hashMapOf(
              "현재수강학기" to now_semester,
              //listOf<String>("") to "지난수강학기"
          )
          dbHandler.collection("User")
                  .document(name).set(default_setting)
          Log.d("INIT","successfully add new user ${name}.")
     }

     fun getUser():DocumentReference {
          //유저 이름 참조 받아오기
          return dbHandler.collection("User").document(name)
     }

     //학기 관련

     fun getCurrentSemester(): CollectionReference{
          //현재 학기 받아오기
         val user = getUser()
         var current: Any? = null
         user.get()
             .addOnSuccessListener { document ->
                current = document.data!!["현재수강학기"]
                 Log.d(TAG,"Load current Semester: "+current.toString()+"!")
             }.addOnFailureListener { exception ->
                 Log.d("ERR", "Load failed", exception)
             }
//         Log.d(TAG,current.toString()+"!!!")
          return getSemester(current.toString())
     }

     fun newSemester(user: DocumentReference){
          //현재 학기 생성
          //이미 존재하는 내용은 지난학기로 넘어감
          Log.d("TAG","successfully remote last semester")
     }

    fun getLastSemesters(): Any?{
        //지난학기 목록 호출
        val user = getUser()
        var lastSemesterList: Any? = null

        user.get()
            .addOnSuccessListener { document ->
                if (document!=null){
                    lastSemesterList = document["지난수강학기"]
                    Log.d("GET", "${document.data}")
                }else{
                    Log.d("GET","No Such Name")
                }

            }.addOnFailureListener { exception ->
                Log.d("ERR", "Load failed", exception)
            }
        return lastSemesterList
    }

    fun getSemester(name: String): CollectionReference{
        val user = getUser()
        return user.collection(name)
    }

     //수강 과목 관련

     fun getListSubject(semester: String = now_semester): MutableList<String>{
          //학기를 입력으로 받아 과목 리스트 호출
         var listSubject = mutableListOf<String>()
         val term = getSemester(semester)

          term
                  .get()
                  .addOnSuccessListener { documents ->
                       for (document in documents) {
                           listSubject.add(document.id)
                       }
                       Log.d("LIST","Success load:: ${listSubject}")
                  }
                  .addOnFailureListener {
                       Log.w("Error", "Load subject failed ")
                  }
         return listSubject

     }

     fun getSubject(semester: String = now_semester, name: String): DocumentReference{

         return getSemester(semester).document(name)
     }

     fun loadSubject(semester: String = now_semester, name: String): Map<String, Any>?{
          //과목에 대한 정보 호출
         var ret: Map<String, Any>? = null
         val term = getSemester(semester)

         term.document(name)
             .get()
             .addOnSuccessListener { document->
                 ret = document.data
                 Log.d("LOAD_SUBJECT","${ret}")
                 Log.d("LOAD_SUBJECT","${ret!!["강의명"]}")
             }.addOnFailureListener { exception ->
                 Log.d("ERR", "Load failed", exception)
             }

         return ret

     }

     fun addNewSubject(className:String, profName:String, classRoom:String, time:String){
          //현재 학기에 새로운 과목 추가
          val data = hashMapOf(
              "강의명" to className,
              "교수명" to profName,
              "강의실" to classRoom,
              "강의시간" to time
          )
          val currentSemester = getSemester(now_semester)
          currentSemester.document(className).set(data)

          val classNoteRef = getSubject(name = className).collection("수강노트")

          val init = hashMapOf(
                  "예습노트유무" to 0,
                  "복습노트유무" to 0
          )

          for (no in 1..16){
              classNoteRef.document(no.toString()+"주차").set(init)
          }
     }

     fun deleteSubject(){
          //현재 학기에서 기존 과목 제거
     }

     //수강 노트 관련

     fun getItem(subject: String, no: Int){

     }

     //노트 관련

     fun createNote(subject: String, num: String, type: String, text: String){
          //노트 추가
          val note = hashMapOf(
                  type+"노트" to text
          )
          Log.d("CREATENOTE", "semester: ${subject}, type: ${type}, text: ${text}")
          getSubject(name = subject).collection("수강노트")
                  .document(num).set(note)
          getSubject(name = subject).collection("수강노트")
                  .document(num).update(type+"노트유무",1)
     }

     fun editNote(){
          //노트 수정
     }

     fun delNote(){
          //노트 제거
     }

}