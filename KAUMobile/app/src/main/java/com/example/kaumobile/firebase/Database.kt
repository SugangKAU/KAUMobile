package com.example.kaumobile.firebase

import android.util.Log
import com.google.firebase.firestore.*
import java.lang.Exception
import java.lang.NullPointerException
import java.text.DecimalFormat
import java.util.*


class Database {
     var name = "KAU" // 앱 시작시 지정
     var now_semester = "2021년 1학기"
     val dbHandler: FirebaseFirestore = FirebaseFirestore.getInstance()
    val TAG = "DB"

     fun addNewUser(name: String) {
          //어플 처음 실행시 Document 생성
         this.name = name
          val default_setting = hashMapOf(
              "현재수강학기" to now_semester,
              //listOf<String>("") to "지난수강학기"
          )
          dbHandler.collection("User")
                  .document(name).set(default_setting)
          Log.d(TAG,"successfully add new user ${name}.")
     }

     fun getUser(name:String ="KAU"):DocumentReference {
          //유저 이름 참조 받아오기
         Log.d(TAG+":USER",dbHandler.collection("User").document(name).id)
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
                 Log.d(TAG+"getCurrentSemester",current.toString())
             }.addOnFailureListener { exception ->
                 Log.d("ERR", "Load failed", exception)
             }
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
                    Log.d(TAG, "${document.data}")
                }else{
                    Log.d(TAG,"No Such Name")
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

     fun getListSubject(semester: String): MutableList<String>{
          //학기를 입력으로 받아 과목 리스트 호출
         var listSubject = mutableListOf<String>()
         val term = getSemester(semester)

          term
                  .get()
                  .addOnSuccessListener { documents ->
                       for (document in documents) {
                           listSubject.add(document.id)
                       }
                       Log.d("TAG","Success load:: ${listSubject}")
                  }
                  .addOnFailureListener {
                       Log.w("Error", "Load subject failed ")
                  }
         return listSubject

     }

    fun getSubject(semester: String, name: String) : DocumentReference{
        Log.d(TAG,getSemester(semester).document(name).id)
        return getSemester(semester).document(name)
    }

     fun loadSubject(semester: String, name: String): Map<String, Any>?{
          //과목에 대한 정보 호출
         var ret: Map<String, Any>? = null
         val term = getSemester(semester)

         term.document(name)
             .get()
             .addOnSuccessListener { document->
                 ret = document.data
                 Log.d(TAG,"${ret}")
                 Log.d(TAG,"${ret!!["강의명"]}")
             }.addOnFailureListener { exception ->
                 Log.d("ERR", "Load failed", exception)
             }

         return ret

     }

    fun addNewSubject(className:String, profName:String, classRoom:String, time:String) {
        //현재 학기에 새로운 과목 추가
        val data = hashMapOf(
                "강의명" to className,
                "교수명" to profName,
                "강의실" to classRoom,
                "강의시간" to time
        )
        val currentSemester = getSemester(now_semester)
        currentSemester.document(className).set(data)

        val classNoteRef = getSubject(now_semester, className).collection("수강노트")


        fun init(no: Int): HashMap<String, Int> {
            val item = hashMapOf(
                    "no" to no,
                    "hasPreview" to 0,
                    "hasReview" to 0
            )

            return item
        }
        for (no in 1..16) {
            classNoteRef.document(DecimalFormat("000").format(no) + "주차").set(init(no))
        }

        initGrade(now_semester, className)
    }

     fun deleteSubject(){
          //현재 학기에서 기존 과목 제거
     }

     //성적 관련
    fun initGrade(semester: String, subject: String){
         val gradeRef = getSubject(semester, subject).collection("성적")
                 .document("학습관리")
         Log.d("grade","init")
         val grade = hashMapOf(
                 "총 예습수행" to 0,
                 "총 복습수행" to 0,
                 "총 과제수행" to 0
         )

         gradeRef.set(grade, SetOptions.merge())
     }

    fun getGradeRef(semester: String, subject: String): DocumentReference{

        return getSubject(semester, subject).collection("성적")
                .document("학습관리")
    }

    fun doAssign(semester: String, subject: String, type: String){
        var assign_type = ""
        if (type=="예습") assign_type = "총 예습수행"
        else if (type=="복습") assign_type = "총 복습수행"
        else if (type=="과제") assign_type = "총 과제수행"
        else Log.d("grade", "wrong type")

        val gradeRef = getGradeRef(semester, subject)

        var value: Long? = null
        gradeRef.get().addOnSuccessListener { documentSnapshot ->
            value = documentSnapshot
                .get(assign_type) as Long
            Log.d("assign", "value before: ${assign_type}: ${value}")
            gradeRef.update(assign_type, value!! + 1)

            gradeRef.get().addOnSuccessListener {
                Log.d("assign", "value after: ${assign_type}: ${it.get(assign_type)}")
            }

        }
    }

     //노트 관련

     fun createNote(semester: String, subject: String, type: String, no:Int, text: String){
          //노트 추가
         var noteRef =  getSubject(semester, subject).collection("수강노트")
                 .document(DecimalFormat("000").format(no) + "주차")
         Log.d("Note", "${noteRef.id}")
         Log.d("Note", "${text}")
         noteRef.set(hashMapOf("${type}" to text), SetOptions.merge())
         if(type == "예습") noteRef.update("hasPreview",1)
         else if (type == "복습") noteRef.update("hasReview",1)
         Log.d("Note", "Done")
     }

     fun getNoteRef(semester: String, subject: String, type: String, no:Int): DocumentReference{

         return getSubject(semester, subject).collection("수강노트")
                 .document(DecimalFormat("000").format(no) + "주차")
     }

     fun getNote(semester: String, subject: String, type: String, no:Int): String{
          //노트 가져오기
         var noteRef =  getSubject(semester, subject).collection("수강노트")
                 .document(DecimalFormat("000").format(no) + "주차")

         var note_txt = ""
         noteRef.get().addOnSuccessListener {
             try{
                 note_txt = it.get("${type}") as String
                 Log.d("note", "get_note: ${note_txt}")
             }catch (e:NullPointerException){
                 Log.d("note", "no field")
             }

         }
         Log.d("note", "data returned: ${note_txt}")
         return note_txt
     }

     fun delNote(semester: String, subject: String, type: String, no:Int){
          //노트 제거
         var noteRef =  getSubject(semester, subject).collection("수강노트")
                 .document(DecimalFormat("000").format(no) + "주차")
         var noteRef2 = noteRef.collection("텍스트").document(DecimalFormat("000").format(no))
         if(type == "예습") noteRef.update("hasPreview",0)
         else if (type == "복습") noteRef.update("hasReview",0)
     }

}