package com.example.sswolf.kausugang.Seong

enum class NoteType {
    PREVIEW, REVIEW
}

class Note( var title: String, var content: String, var time: String, var type: NoteType) {

}