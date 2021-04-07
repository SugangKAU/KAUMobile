package com.example.sswolf.kausugang.Seong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.kaumobile.R

data class TemplateItem(var no: Int, var hasPreview: Boolean, var hasReview: Boolean, var hasAssign: Boolean)

class TemplateAdapter : RecyclerView.Adapter<Holder>(){

    var listData = mutableListOf<TemplateItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_template, parent, false))
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = listData.get(position)
        holder.setItem(item)
    }
}

class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun setItem(item: TemplateItem){
        itemView.findViewById<TextView>(R.id.weekNum).text = "${item.no}주차"
        if (item.hasPreview) itemView.findViewById<ImageButton>(R.id.previewButton).setImageResource(R.drawable.ic_is_note_true)
        if (item.hasReview) itemView.findViewById<ImageButton>(R.id.reviewButton).setImageResource(R.drawable.ic_is_note_true)
    }
}