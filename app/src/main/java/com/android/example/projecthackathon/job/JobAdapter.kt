package com.android.example.projecthackathon.job

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.JOB_IMAGE
import com.android.example.projecthackathon.helper.JOB_LIKE
import com.android.example.projecthackathon.helper.toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class JobAdapter(var jobModelArrayList: ArrayList<JobModel>): RecyclerView.Adapter<JobAdapter.JobModelViewHolder>() {

    inner class JobModelViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var jobLikeCount : TextView = itemView.findViewById(R.id.job_like_count)
        var username: TextView = itemView.findViewById(R.id.user_name)
        var jobImage : ImageView = itemView.findViewById(R.id.job_img)
        var companyName : TextView = itemView.findViewById(R.id.company_name)
        var companyDesc : TextView = itemView.findViewById(R.id.company_desc)
        var skillsRequirement : TextView = itemView.findViewById(R.id.requirement)
        var jobLike : ToggleButton = itemView.findViewById(R.id.job_like)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.jobfragment_post_layout,parent,false)
        return  JobModelViewHolder(view)
    }


    override fun onBindViewHolder(holder: JobModelViewHolder, position: Int) {
        var requestQueue : RequestQueue = Volley.newRequestQueue(holder.itemView.context)
        holder.companyName.text = jobModelArrayList[position].companyName
        holder.companyDesc.text = jobModelArrayList[position].jobDesc
        holder.skillsRequirement.text = jobModelArrayList[position].jobName
        holder.username.text = jobModelArrayList[position].uname
        holder.jobLikeCount.text = jobModelArrayList[position].jobLike
        val imageLink = JOB_IMAGE+jobModelArrayList[position].jobImg
        Glide.with(holder.itemView.context).load(imageLink).placeholder(R.drawable.logo).into(holder.jobImage)
        holder.jobLike.text = null
        holder.jobLike.textOn = null
        holder.jobLike.textOff = null
        holder.jobLike.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                 val stringRequest= object :StringRequest(Method.POST, JOB_LIKE ,Response.Listener { response ->
                     var jsonResponse = JSONObject(response)
                     var like = jsonResponse.getString("like_count")
                     holder.jobLikeCount.text = like
//                        holder.itemView.context.toast(response)
                    },Response.ErrorListener { error ->
                        holder.itemView.context.toast(error.message.toString())
                    }){
                    override fun getParams(): MutableMap<String, String> {
                        val hashMap = HashMap<String,String>()
                        hashMap["job_upload_id"] = jobModelArrayList[position].jobid.toString();
                        hashMap["post_like"] = "true";
                        return hashMap
                    }
                }
                requestQueue.add(stringRequest)

            }else{
                val stringRequest= object :StringRequest(Method.POST, JOB_LIKE ,Response.Listener { response ->
                    var jsonResponse = JSONObject(response)
                    var like = jsonResponse.getString("like_count")
                    holder.jobLikeCount.text = like
//                    holder.itemView.context.toast(response)
                },Response.ErrorListener { error ->
                    holder.itemView.context.toast(error.message.toString())
                }){
                    override fun getParams(): MutableMap<String, String> {
                        val hashMap = HashMap<String,String>()
                        hashMap["job_upload_id"] = jobModelArrayList[position].jobid.toString();
                        hashMap["post_like"] = "false";
                        return hashMap
                    }
                }
                requestQueue.add(stringRequest)
            }

        }

    }


    override fun getItemCount(): Int {
        return jobModelArrayList.size
    }
}