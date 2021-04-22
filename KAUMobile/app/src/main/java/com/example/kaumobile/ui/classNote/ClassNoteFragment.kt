package com.example.kaumobile.ui.classNote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaumobile.R
import com.example.kaumobile.firebase.Database
import com.example.sswolf.kausugang.Seong.NoteActivity
import com.example.sswolf.kausugang.Seong.TemplateAdapter
import com.example.sswolf.kausugang.Seong.TemplateItem


class ClassNoteFragment : Fragment() {
    lateinit var subjectList: MutableList<String>
    var pos = 0
    var subjectName = "안드로이드"
    lateinit var root: View
    lateinit var ctx:Context
    private lateinit var classNoteViewModel: ClassNoteViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        root = inflater.inflate(R.layout.fragment_class_note, container, false)

        val db = Database()
        subjectList = db.getListSubject()

        var template:MutableList<TemplateItem> = loadTemplate()
        var adapter = TemplateAdapter()
        adapter.listData = template

        root.findViewById<RecyclerView>(R.id.templateView).adapter = adapter
        root.findViewById<RecyclerView>(R.id.templateView).layoutManager = LinearLayoutManager(context)

        root.findViewById<ImageView>(R.id.btnPrev).setOnClickListener{ getPrevClass() }
        root.findViewById<ImageView>(R.id.btnNext).setOnClickListener { getNextClass() }

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

//    override fun onClick(v: View) {
//        when(v.id){
//            R.id.previewButton->{
//                //알림창
//
//                val view = layoutInflater.inflate(R.layout.alert_dialog, null)
//
//                val popup = AlertDialog.Builder(ctx)
//                    .setTitle("AlertDialog CreateNote")
//                    .setPositiveButton("확인"){ dialog, which ->
//                        val intent = Intent(ctx, NoteActivity::class.java)
//                        startActivity(intent)
//                    }
//                    .setNeutralButton("취소",null)
//                    .create()
//
//                popup.setView(view)
//                popup.show()
//
//            }
//            R.id.reviewButton->{
//                //preview와 동일
//            }
//            R.id.uploadButton->{
//                //과제 업로드로 연결
//            }
//
//        }
//    }


    fun getPrevClass(){
        if(pos-1 < 0) pos = subjectList.size
        pos = (pos-1)%subjectList.size
        subjectName = subjectList[pos]
        view?.findViewById<TextView>(R.id.textClass)?.text = "과목명: " + "${subjectList[pos]}"

        var template:MutableList<TemplateItem> = loadTemplate()
        var adapter = TemplateAdapter()
        adapter.listData = template

        root!!.findViewById<RecyclerView>(R.id.templateView).adapter = adapter
        root!!.findViewById<RecyclerView>(R.id.templateView).layoutManager = LinearLayoutManager(context)
        root!!.findViewById<ImageView>(R.id.btnPrev).setOnClickListener{ getPrevClass() }
        root!!.findViewById<ImageView>(R.id.btnNext).setOnClickListener { getNextClass() }
        //Log.d("test","Prev+${SubjectTmp[pos]}")
    }

    fun getNextClass(){
        pos = (pos+1)%subjectList.size
        subjectName = subjectList[pos]
        view?.findViewById<TextView>(R.id.textClass)?.text = "과목명: " + "${subjectList[pos]}"

        var template:MutableList<TemplateItem> = loadTemplate()
        var adapter = TemplateAdapter()
        adapter.listData = template

        root!!.findViewById<RecyclerView>(R.id.templateView).adapter = adapter
        root!!.findViewById<RecyclerView>(R.id.templateView).layoutManager = LinearLayoutManager(context)
        root!!.findViewById<ImageView>(R.id.btnPrev).setOnClickListener{ getPrevClass() }
        root!!.findViewById<ImageView>(R.id.btnNext).setOnClickListener { getNextClass() }
    }

    fun loadTemplate(): MutableList<TemplateItem> {
        val data:MutableList<TemplateItem> = mutableListOf()
        for (no in 1..16){
            data.add( setTemplateItem(no) )
        }

        return data
    }

    fun setTemplateItem(no:Int): TemplateItem{
        val db = Database()



        return TemplateItem(no, subjectName, true, false, true)
    }


}