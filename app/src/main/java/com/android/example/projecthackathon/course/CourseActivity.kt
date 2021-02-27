package com.android.example.projecthackathon.course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.GET_COURSE
import com.android.example.projecthackathon.helper.toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class CourseActivity : AppCompatActivity() {
    private var TAG = "CourseActivity"
    private lateinit var requestQueue: RequestQueue
    private var courseArrayList: ArrayList<CourseModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        requestQueue = Volley.newRequestQueue(this)

        getCourse()

    }

    private fun getCourse() {
        val stringRequest : StringRequest = StringRequest(Request.Method.POST, GET_COURSE , Response.Listener { response ->
            toast(response.toString())
            convertJson(response)
        },Response.ErrorListener { error->
            Log.e(TAG,error.message.toString())
            toast(error.message.toString())
        })
        requestQueue.add(stringRequest)
    }
    private fun convertJson(response: String?){
        val jsonArray = JSONArray(response)
//        toast(jsonArray.length().toString())
        for (i in 0 until jsonArray.length()){
            jsonArray.getJSONObject(i).apply {
                val courseName = getString("course_name")
                val courseImage = getString("course_image")
                val courseDesc = getString("course_desc")
                val coursePrice = getString("course_price")
                toast(courseName + courseImage + courseDesc + coursePrice)
                val cModel = CourseModel(courseName,courseImage,courseDesc,coursePrice)
                courseArrayList.add(cModel)
            }
            
        }
    }

}