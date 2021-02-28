package com.android.example.projecthackathon.mentor

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.GET_MENTORS_IMAGE
import com.android.example.projecthackathon.helper.toast
import com.bumptech.glide.Glide

class MentorAdapter(var mentorArrayList: ArrayList<MentorModel>) : RecyclerView.Adapter<MentorAdapter.MentorAdapterRecyclerView>() {
    inner class MentorAdapterRecyclerView(itemView : View):RecyclerView.ViewHolder(itemView){
        val nameTextView : TextView = itemView.findViewById(R.id.mentorNameView)
        val mentorImageView : ImageView = itemView.findViewById(R.id.mentorImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorAdapterRecyclerView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mentor_item_layout,parent,false)
        return MentorAdapterRecyclerView(view)
    }
    override fun onBindViewHolder(holder: MentorAdapterRecyclerView, position: Int) {
        holder.nameTextView.text = mentorArrayList[position].mentorName
        val imgLink = GET_MENTORS_IMAGE+mentorArrayList[position].mentorImage
        Glide.with(holder.itemView.context).load(imgLink).placeholder(R.drawable.placeholder).into(holder.mentorImageView)
        holder.itemView.setOnClickListener {
            it.context.toast(mentorArrayList[position].mentorName.toString())
        }
    }
    override fun getItemCount(): Int {
        return mentorArrayList.size
    }
}