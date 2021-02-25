package com.android.example.projecthackathon

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.example.projecthackathon.home.HomeFragment
import com.android.example.projecthackathon.job.JobFragment
import com.android.example.projecthackathon.message.MessageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smarteist.autoimageslider.SliderLayout
import com.smarteist.autoimageslider.SliderView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val jobFragment = JobFragment()
        val messageFragment = MessageFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.selectedItemId = R.id.home
        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.job -> setCurrentFragment(jobFragment)
                R.id.message -> setCurrentFragment(messageFragment)

            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}