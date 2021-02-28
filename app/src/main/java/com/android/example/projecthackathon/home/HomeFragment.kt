package com.android.example.projecthackathon.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.course.CourseActivity
import com.android.example.projecthackathon.job.JobsAndInternshipActivity
import com.android.example.projecthackathon.mentor.MentorActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smarteist.autoimageslider.SliderLayout
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {

    private lateinit var sliderLayout: SliderLayout
    private var viewCourseTextView : TextView ?= null
    private var askOurMentorTextView : TextView ?= null
    private var jobsnInternshipTextView : TextView ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //setSliderViewsOn1(view)
        setSliderViewsOn2(view)
        jobsnInternshipTextView = view.findViewById(R.id.tv1)
        viewCourseTextView = view.findViewById(R.id.tv2)
        askOurMentorTextView = view.findViewById(R.id.tv3)
        jobsnInternshipTextView?.setOnClickListener {
            val intent1 = Intent(requireContext(),JobsAndInternshipActivity::class.java)
            startActivity(intent1)
        }
        viewCourseTextView?.setOnClickListener {
            val intent2 = Intent(requireContext(),CourseActivity::class.java)
            startActivity(intent2)
        }
        askOurMentorTextView?.setOnClickListener { 
            val intent3 = Intent(requireContext(),MentorActivity::class.java)
            startActivity(intent3)
        }

        return view
    }


    private fun setSliderViewsOn1(view: View) {

        sliderLayout = view.findViewById(R.id.imageSlider2)
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.SWAP)
        sliderLayout.scrollTimeInSec = 1

        for (i in 1..3) {
            val sliderView = SliderView(context)

            //sliderView.setImageDrawable(i)
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER)
            sliderLayout.addSliderView(sliderView)
        }
    }

    private fun setSliderViewsOn2(view: View) {

        sliderLayout = view.findViewById(R.id.imageSlider2)
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.SWAP)
        sliderLayout.scrollTimeInSec = 1

        for (i in 1..3) {
            val sliderView = SliderView(context)

            //sliderView.setImageDrawable(i)
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER)
            sliderLayout.addSliderView(sliderView)
        }
    }
}