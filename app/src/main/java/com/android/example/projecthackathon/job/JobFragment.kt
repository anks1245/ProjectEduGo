package com.android.example.projecthackathon.job

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.GET_COURSE_IMAGE
import com.android.example.projecthackathon.helper.GET_JOB_POST
import com.android.example.projecthackathon.helper.GET_MENTOR
import com.android.example.projecthackathon.helper.toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class JobFragment : Fragment() {
    lateinit var root: View
    private lateinit var recyclerView : RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var requestQueue: RequestQueue
    private var jobArrayList : ArrayList<JobModel> = ArrayList()
    private lateinit var jobAdapter: JobAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_job, container, false)
        recyclerView = root.findViewById(R.id.recyclerview_jobpost)
        swipeRefreshLayout = root.findViewById(R.id.swipetorefresh_job)
        requestQueue = Volley.newRequestQueue(root?.context)
        getJobPost()
        swipeRefreshLayout.setOnRefreshListener {
            jobArrayList.clear()
            getJobPost()
            swipeRefreshLayout.isRefreshing = false
        }
        return root
    }

    private fun getJobPost() {
        val stringRequest = StringRequest(
            Request.Method.POST, GET_JOB_POST ,
            Response.Listener { response->
                convertJson(response)
            },
            Response.ErrorListener { error->
                context?.toast(error.message.toString())
            })
        requestQueue?.add(stringRequest)
    }

    private fun convertJson(response : String?) {
        val jsonArray = JSONArray(response)
        context?.toast(jsonArray.length().toString())
        for( i in 0 until jsonArray.length()){
            jsonArray.getJSONObject(i).apply {
                val companyName = getString("company_name")
                val jobName = getString("job_name")
                Log.d("JobFrsagment","on Response $i : $jobName")
                val uName = getString("job_uploaded_by")
                val jobImage = getString("job_image")
                val jobDesc = getString("job_desc")
                val jobValid = getString("job_valid")
                val jobType = getString("job_type")
                val jobLike = getString("job_like")
                val jobApplied = getString("job_applied")
                val jobUploadedAt = getString("job_uploaded_at")
                val mJob = JobModel(
                    companyName,
                    jobName,
                    uName,
                    jobImage,
                    jobDesc,
                    jobValid,
                    jobType,
                    jobLike,
                    jobApplied,
                    jobUploadedAt
                )
                jobArrayList.add(mJob)
            }
            jobAdapter = JobAdapter(jobArrayList)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                adapter = jobAdapter
            }
        }
    }
}