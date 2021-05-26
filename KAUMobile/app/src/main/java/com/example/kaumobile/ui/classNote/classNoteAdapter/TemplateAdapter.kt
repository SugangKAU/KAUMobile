package com.example.sswolf.kausugang.Seong

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kaumobile.R
import com.example.kaumobile.firebase.Database
import com.example.kaumobile.ui.classNote.assign.AssignActivity
import com.example.kaumobile.ui.classNote.classNoteAdapter.TemplateItem
import com.google.firebase.firestore.FirebaseFirestore


class TemplateAdapter(val semester: String, val name: String) : RecyclerView.Adapter<Holder>(){


    val subject = name
    var listData = ArrayList<TemplateItem>()

    init {
        val ref = Database().getSubject(semester,name)

        ref.collection("수강노트").addSnapshotListener { querySnapshot, firebaseError ->
            for (snapshot in querySnapshot!!.documents){

                Log.d("DEBUG", "${snapshot.data}")
                var item = snapshot.toObject(TemplateItem::class.java)
                listData.add(item!!)
            }
            notifyDataSetChanged()
        }
        
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_template, parent, false))
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = listData.get(position)
        holder.setItem(item, subject, semester)
    }

}

class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun setItem(item: TemplateItem, subject: String, semester: String) {
        val intent = Intent(itemView.context, NoteActivity::class.java)
        intent.putExtra("Semester", semester)
        intent.putExtra("Subject", subject)
        intent.putExtra("Week", item.no)

        itemView.findViewById<TextView>(R.id.weekNum).text = "${item.no}주차"
        if (item.hasPreview == 1) itemView.findViewById<ImageButton>(R.id.previewButton).setImageResource(R.drawable.ic_is_note_true)
        else {
            itemView.findViewById<ImageButton>(R.id.previewButton).setImageResource(R.drawable.ic_is_note_false)
        }

        itemView.findViewById<ImageButton>(R.id.previewButton).setOnClickListener {
            intent.putExtra("Type","예습")
            startActivity(itemView.context, intent, null)
        }

        if (item.hasReview == 1) itemView.findViewById<ImageButton>(R.id.reviewButton).setImageResource(R.drawable.ic_is_note_true)
        else {
            itemView.findViewById<ImageButton>(R.id.reviewButton).setImageResource(R.drawable.ic_is_note_false)
        }

        itemView.findViewById<ImageButton>(R.id.reviewButton).setOnClickListener {
            intent.putExtra("Type","복습")
            startActivity(itemView.context, intent, null)
        }

        itemView.findViewById<ImageButton>(R.id.uploadButton).setOnClickListener {
            val intent = Intent(itemView.context, AssignActivity::class.java)
            startActivity(itemView.context, intent, null)
        }
    }
}


