package com.android.example.projecthackathon.job

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.JOB_IMAGE
import com.bumptech.glide.Glide

class JobAdapter(var jobModelArrayList: ArrayList<JobModel>): RecyclerView.Adapter<JobAdapter.JobModelViewHolder>() {
    inner class JobModelViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var username: TextView = itemView.findViewById(R.id.user_name)
        var jobImage : ImageView = itemView.findViewById(R.id.job_img)
        var companyName : TextView = itemView.findViewById(R.id.company_name)
        var companyDesc : TextView = itemView.findViewById(R.id.company_desc)
        var skillsRequirement : TextView = itemView.findViewById(R.id.requirement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.jobfragment_post_layout,parent,false)
        return  JobModelViewHolder(view)
    }


    override fun onBindViewHolder(holder: JobModelViewHolder, position: Int) {
        holder.companyName.text = jobModelArrayList[position].companyName
        holder.companyDesc.text = jobModelArrayList[position].jobDesc
        holder.skillsRequirement.text = jobModelArrayList[position].jobName
        holder.username.text = jobModelArrayList[position].uname
        val imageLink = JOB_IMAGE+jobModelArrayList[position].jobImg
        Glide.with(holder.itemView.context).load(imageLink).placeholder(R.drawable.logo).into(holder.jobImage)
    }


    override fun getItemCount(): Int {
        return jobModelArrayList.size
    }
}