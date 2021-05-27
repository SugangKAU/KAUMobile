package com.example.sswolf.kausugang.Seong

import androidx.lifecycle.MutableLiveData

enum class NoteType {
    PREVIEW, REVIEW
}

class Note( var type: NoteType, var num: Int, var content: String) {

}