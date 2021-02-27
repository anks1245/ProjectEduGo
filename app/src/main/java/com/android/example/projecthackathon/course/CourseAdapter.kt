package com.android.example.projecthackathon.course

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(private val courses : ArrayList<CourseModel>) : RecyclerView.Adapter<CourseAdapter.RecyclerViewCourseAdapter>(){
    inner class RecyclerViewCourseAdapter(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewCourseAdapter {
        TODO("Not yet implemented")
    }


    override fun onBindViewHolder(holder: RecyclerViewCourseAdapter, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}