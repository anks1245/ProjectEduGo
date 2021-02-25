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

    private lateinit var sliderLayout: SliderLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        sliderLayout = view.findViewById(R.id.imageSlider)
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.WORM)
        sliderLayout.scrollTimeInSec = 1
        setSliderViews()
        // Inflate the layout for this fragment
        return view
    }

    private fun setSliderViews() {

        val resources = listOf(R.drawable.ic_home, R.drawable.ic_job, R.drawable.ic_message)
        for (i in resources) {
            val sliderView = SliderView(context)
            //sliderView.setImageDrawable(i)
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            sliderLayout.addSliderView(sliderView)
        }
    }
}