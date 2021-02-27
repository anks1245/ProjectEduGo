package com.android.example.projecthackathon.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.example.projecthackathon.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smarteist.autoimageslider.SliderLayout
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {

    private lateinit var sliderLayout: SliderLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //setSliderViewsOn1(view)
        setSliderViewsOn2(view)

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