package com.android.example.projecthackathon.course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.helper.GET_COURSE_IMAGE
import com.bumptech.glide.Glide

class CourseFullActivity : AppCompatActivity() {
    private lateinit var courseImageView : ImageView
    private lateinit var courseName : TextView
    private lateinit var coursePrice : TextView
    private lateinit var courseDesc : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_full)
        coursePrice = findViewById(R.id.course_price)
        courseImageView = findViewById(R.id.course_image)
        courseName = findViewById(R.id.course_name)
        coursePrice = findViewById(R.id.course_price)
        courseDesc =findViewById(R.id.course_desc)
        var cn =""
        var ci =""
        var cp =""
        var cd =""
        intent?.apply {
            cn = getStringExtra("cName").toString()
             ci = getStringExtra("cImage").toString()
             cp = getStringExtra("cPrice").toString()
             cd = getStringExtra("cDesc").toString()
        }
        courseDesc.text =cd
        courseName.text=cn
        coursePrice.text=cp
        val imgLink = GET_COURSE_IMAGE+ci
        Glide.with(this).load(imgLink).placeholder(R.drawable.logo).into(courseImageView)
    }
}