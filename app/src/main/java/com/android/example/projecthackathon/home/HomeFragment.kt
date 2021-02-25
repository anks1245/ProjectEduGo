package com.android.example.projecthackathon.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.example.projecthackathon.R
import com.smarteist.autoimageslider.SliderLayout
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {

    private lateinit var sliderLayout1: SliderLayout
    private lateinit var sliderLayout2: SliderLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        sliderLayout1 = view.findViewById(R.id.imageSlider1)
        sliderLayout1.setIndicatorAnimation(SliderLayout.Animations.WORM)
        sliderLayout1.scrollTimeInSec = 1

        sliderLayout2 = view.findViewById(R.id.imageSlider2)
        sliderLayout2.setIndicatorAnimation(SliderLayout.Animations.WORM)
        sliderLayout2.scrollTimeInSec = 1

        setSliderViews()
        // Inflate the layout for this fragment
        return view
    }

    private fun setSliderViews() {

        val resources = listOf(R.drawable.ic_home, R.drawable.ic_job, R.drawable.ic_message)
        for (i in resources) {
            val sliderView1 = SliderView(context)
            val sliderView2 = SliderView(context)

            //sliderView.setImageDrawable(i)
            sliderView1.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            sliderView2.setImageScaleType(ImageView.ScaleType.CENTER_CROP)

            sliderLayout1.addSliderView(sliderView1)
            sliderLayout2.addSliderView(sliderView2)
        }
    }
}