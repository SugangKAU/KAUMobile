package com.example.kaumobile.firebase

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception


class Database {
     val name = "KAU" // 앱 시작시 지정
     val dbHandler: FirebaseFirestore = FirebaseFirestore.getInstance()

     fun addNewUser(name: String) {
          //어플 처음 실행시 Document(Collection?) 생성
          dbHandler.collection("User")
                  .add(name)
          Log.d("TAG","successfully add new user.")
     }

     fun getUser(name: String):DocumentReference {
          //유저 이름 참조 받아오기
          return dbHandler.collection("User").document(name)
     }

     //학기 관련

     fun getSemester(user: DocumentReference): CollectionReference{
          //현재 학기 받아오기
          return user.collection("현재수강학기")
     }

     fun newSemester(user: DocumentReference){
          //현재 학기 생성
          //이미 존재하는 내용은 지난학기로 넘어감
          Log.d("TAG","successfully add new user.")
     }

     fun getLastSemesters():CollectionReference{
          //지난학기 목록 호출
          return getUser(name).collection("지난수강학기")
     }

     //수강 과목 관련

     fun getListSubject(semester: CollectionReference){
          //학기를 입력으로 받아 과목 리스트 호출
          semester
                  .get()
                  .addOnSuccessListener { result ->

                  }
                  .addOnFailureListener {
                       Log.w("Error", "Load subject failed ")
                  }

          return result
     }

     fun loadSubject(){
          //과목에 대한 정보 호출
     }

     fun addNewSubject(curruntSemester: CollectionReference, subject: dbClass.Subject){
          //현재 학기에 새로운 과목 추가
          curruntSemester.document(subject.className).set(subject)
     }

     fun deleteSubject(){
          //현재 학기에서 기존 과목 제거
     }

     //노트 관련

     fun createNote(){
          //노트 추가
     }

     fun editNote(){
          //노트 수정
     }

     fun delNote(){
          //노트 제거
     }

}