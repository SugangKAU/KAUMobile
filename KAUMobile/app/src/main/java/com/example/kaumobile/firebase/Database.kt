package com.example.kaumobile.firebase

import com.google.firebase.firestore.FirebaseFirestore

class Database {

     var dbHandler: FirebaseFirestore = FirebaseFirestore.getInstance()

     fun addNewUser(name: String) {
          //어플 처음 실행시 Document(Collection?) 생성
     }

     fun getUser() {
          //유저 이름 받아오기
     }

     fun loadLastSemester(){
          //지난학기 목록 호출
     }

     fun loadListSubject(){
          //학기를 입력으로 받아 과목 리스트 호출
     }

     fun addNewSubject(){
          //현재 학기에 새로운 과목 추가
     }

     fun deleteSubject(){
          //현재 학기에서 기존 과목 제거
     }

     fun createNote(){
          //노트 추가가
     }

}