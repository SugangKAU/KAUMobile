package com.example.kaumobile.firebase

class dbClass {

    data class Subject(var className:String, var profName:String, var classRoom:String, var time:String)
    data class Table(var tableItems: MutableList<TableItem>)
    data class TableItem( var prevNote: Note, var reviewNot: Note, var upload: String)
    data class Note( var type: NoteType, var num: Int, var content: String)

    enum class NoteType {
        PREVIEW, REVIEW
    }

//    class Note( var type: NoteType, var num: Int, var content: String) {
//
//    }
}