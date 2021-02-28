package com.android.example.projecthackathon.mentor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.GET_MENTOR
import com.android.example.projecthackathon.helper.toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MentorActivity : AppCompatActivity() {
    private lateinit var requestQueue: RequestQueue
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var mentorAdapter: MentorAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var mentorArrayList : ArrayList<MentorModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor)
        requestQueue = Volley.newRequestQueue(this)
        recyclerView = findViewById(R.id.recyclerciew_mentor)
        progressBar = findViewById(R.id.progress_bar)
        swipeRefreshLayout = findViewById(R.id.swipetorefresh_mentor)
        supportActionBar?.title ="Mentors"
        getData()
        swipeRefreshLayout.setOnRefreshListener {
            mentorArrayList.clear()
            getData()
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun getData() {
        progressBar.visibility = View.VISIBLE
        val stringRequest = StringRequest(Request.Method.POST , GET_MENTOR ,Response.Listener { response ->
//            toast(response)
            progressBar.visibility = View.GONE
            convertJson(response)
        },Response.ErrorListener { error ->
            toast(error.message.toString())
        })
        requestQueue.add(stringRequest)
    }
    private fun convertJson(response: String?){
        val jsonArray = JSONArray(response)
        for (i in 0 until jsonArray.length()){
            jsonArray.getJSONObject(i).apply{
                val mentorName = getString("mentor_name")
                val mentorImage = getString("mentor_image")
                val mentorDesc = getString("mentor_desc")
                val mentorMail = getString("mentor_email")
                val mMentor = MentorModel(mentorName,mentorImage,mentorDesc,mentorMail)
                mentorArrayList.add(mMentor)
            }
            mentorAdapter = MentorAdapter(mentorArrayList)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MentorActivity,LinearLayoutManager.VERTICAL,false)
                adapter = mentorAdapter
            }
        }
    }
}