package com.android.example.projecthackathon.job

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.GET_JOB_POST
import com.android.example.projecthackathon.helper.JOB_SEARCH
import com.android.example.projecthackathon.helper.toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray


class JobsAndInternshipActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var jobModel: JobModel
    private lateinit var searchEditText: EditText
    private var jobModelArrayList: ArrayList<JobModel> = ArrayList()
    private lateinit var jobnInternshipAdapter: JobnInternshipAdapter
    private lateinit var requestQueue: RequestQueue
    private lateinit var noJobsFound:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs_and_internship)
        requestQueue = Volley.newRequestQueue(this)
        recyclerView = findViewById(R.id.recyclerView_job_and_internship)
        searchEditText = findViewById(R.id.searchView)
        swipeRefreshLayout = findViewById(R.id.swipetoRefresh_jobsearch)
        noJobsFound = findViewById(R.id.no_jobs)
        searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchJob(searchEditText.text.toString())
                return@OnEditorActionListener true
            }else{
                return@OnEditorActionListener false
            }

        })
        supportActionBar?.title = "Jobs and Internship"
        getJob()
        swipeRefreshLayout.setOnRefreshListener {
            jobModelArrayList.clear()
            getJob()
        }
    }
    private fun searchJob(search:String?){
//        toast(search.toString())
        val stringRequest = object :StringRequest(
            Request.Method.POST, JOB_SEARCH,
            Response.Listener { response ->
                jobModelArrayList.clear()
                val jsonArray = JSONArray(response)
                val jsonObject = jsonArray.getJSONObject(0)
                val error = jsonObject.getBoolean("error")
                if (error){
                    noJobsFound.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }else{
                    convertJson(response)
                }

//                toast(response)
            },
            Response.ErrorListener { error ->
                toast(error.message.toString())
            }){
            override fun getParams(): MutableMap<String, String> {
                val hashMap = HashMap<String,String>()
                hashMap["search_job"] = search.toString()
                return hashMap
            }
        }
        requestQueue?.add(stringRequest)
    }

    private fun getJob() {
        val stringRequest = StringRequest(
            Request.Method.POST, GET_JOB_POST,
            Response.Listener { response ->
                convertJson(response)
            },
            Response.ErrorListener { error ->
                toast(error.message.toString())
            })
        requestQueue?.add(stringRequest)
    }

    private fun convertJson(response: String?) {
        val jsonArray = JSONArray(response)
//        toast(jsonArray.length().toString())
            for( i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    val jobId = getString("job_upload_id")
                    val companyName = getString("company_name")
                    val jobName = getString("job_name")
                    Log.d("JobFrsagment", "on Response $i : $jobName")
                    val uName = getString("job_uploaded_by")
                    val jobImage = getString("job_image")
                    val jobDesc = getString("job_desc")
                    val jobValid = getString("job_valid")
                    val jobType = getString("job_type")
                    val jobLike = getString("job_like")
                    val jobApplied = getString("job_applied")
                    val jobUploadedAt = getString("job_uploaded_at")
                    val mJob = JobModel(
                        jobId,
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
                    jobModelArrayList.add(mJob)
                }
                noJobsFound.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                jobnInternshipAdapter = JobnInternshipAdapter(jobModelArrayList)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        this@JobsAndInternshipActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = jobnInternshipAdapter
                }
            }
        }
}