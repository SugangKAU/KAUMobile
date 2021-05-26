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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaumobile.R
import com.example.kaumobile.ui.classNote.classNoteAdapter.TemplateItem
import com.example.kaumobile.ui.home.HomeViewModel
import com.example.kaumobile.ui.home.Subject
import com.example.sswolf.kausugang.Seong.NoteActivity
import com.example.sswolf.kausugang.Seong.TemplateAdapter
import java.lang.Exception


class ClassNoteFragment : Fragment() {
    var subjectList = arrayListOf<Subject>()
    var pos = 0
    var max = 0
    lateinit var recycler: RecyclerView
    lateinit var ctx:Context
    private val classNoteViewModel: ClassNoteViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_class_note, container, false)
       recycler = root.findViewById<RecyclerView>(R.id.templateView)

       try{
           pos = requireArguments().get("subject") as Int
           Log.d("ClassNote", "${pos} th subject")
       }catch (e: Exception){
           pos = 0
           Log.d("ERR", "${e}")
       }

        subjectList = HomeViewModel.subjectList.value!!
        classNoteViewModel.subject.value = subjectList[pos]

        root.findViewById<TextView>(R.id.textClass)?.text = "과목명: " + subjectList[pos].name

        var adapter = TemplateAdapter("2021년 1학기",subjectList[pos].name)

        val subjectObserver = Observer<Subject>{
            view?.findViewById<TextView>(R.id.textClass)?.text = "과목명: " + it.name
            adapter = TemplateAdapter(it.semester, it.name)
            recycler.adapter = adapter
        }

        max = subjectList.size

        val templateObserver = Observer<ArrayList<TemplateItem>> { template ->
            adapter.listData = template
            recycler.adapter = adapter
        }

        classNoteViewModel.subject.observe(viewLifecycleOwner, subjectObserver)
        classNoteViewModel.template.observe(viewLifecycleOwner, templateObserver)

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


    fun getPrevClass(){
        if(pos-1 < 0) pos = max
        pos = (pos-1)%max
        classNoteViewModel.subject.value = subjectList[pos]
    }

    fun getNextClass(){
        pos = (pos+1)%max
        classNoteViewModel.subject.value = subjectList[pos]
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



}