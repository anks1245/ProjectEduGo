package com.android.example.projecthackathon.course

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.GET_COURSE_IMAGE
import com.bumptech.glide.Glide

class CourseAdapter(private val courses : ArrayList<CourseModel>) : RecyclerView.Adapter<CourseAdapter.RecyclerViewCourseAdapter>(){
    inner class RecyclerViewCourseAdapter(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView : ImageView
        val courseNametv : TextView
        val coursePricetv : TextView
        init {
            imageView = itemView.findViewById(R.id.course_image)
            courseNametv = itemView.findViewById(R.id.course_name)
            coursePricetv = itemView.findViewById(R.id.course_price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewCourseAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_item_layout,parent,false)
        return RecyclerViewCourseAdapter(view)
    }


    override fun onBindViewHolder(holder: RecyclerViewCourseAdapter, position: Int) {
        holder.courseNametv.text = courses[position].courseName
        holder.coursePricetv.text = courses[position].coursePrice
        val imgLink = GET_COURSE_IMAGE + courses[position].courseImage
        Glide.with(holder.itemView.context).load(imgLink).placeholder(R.drawable.placeholder).into(holder.imageView)


        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, courses[position].courseName , Toast.LENGTH_SHORT).show()
            val intent = Intent(it.context,CourseFullActivity::class.java)
            intent.putExtra("cName",courses[position].courseName)
            intent.putExtra("cPrice",courses[position].coursePrice)
            intent.putExtra("cImage",courses[position].courseImage)
            intent.putExtra("cDesc",courses[position].courseDesc)

            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}