package com.example.sswolf.kausugang.Seong

enum class NoteType {
    PREVIEW, REVIEW
}

class Note( var type: NoteType, var num: Int, var content: String) {

}