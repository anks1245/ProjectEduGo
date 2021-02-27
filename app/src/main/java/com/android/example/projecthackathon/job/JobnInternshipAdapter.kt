package com.android.example.projecthackathon.job

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.toast

class JobnInternshipAdapter(val jobArrayList: ArrayList<JobModel>):RecyclerView.Adapter<JobnInternshipAdapter.JobItemViewHolder>() {
    inner class JobItemViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val companyName : TextView = itemView.findViewById(R.id.company_name)
        val jobPostedBy : TextView = itemView.findViewById(R.id.posted_by)
        val requirement : TextView = itemView.findViewById(R.id.requirement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.jobs_item_layout,parent,false)
        return JobItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobItemViewHolder, position: Int) {
        holder.companyName.text = jobArrayList[position].companyName
        holder.jobPostedBy.text = jobArrayList[position].uname
        holder.requirement.text = jobArrayList[position].jobName

        holder.itemView.setOnClickListener {
            it.context.toast(jobArrayList[position].jobName.toString())
        }
    }

    override fun getItemCount(): Int {
        return jobArrayList.size
    }
}