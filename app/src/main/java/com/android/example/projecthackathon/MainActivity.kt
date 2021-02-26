package com.android.example.projecthackathon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.android.example.projecthackathon.home.HomeFragment
import com.android.example.projecthackathon.job.JobFragment
import com.android.example.projecthackathon.message.MessageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val jobFragment = JobFragment()
        val messageFragment = MessageFragment()


//        drawerLayout = findViewById(R.id.drawerLayout)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
//        val navController: NavController = navHostFragment.navController
//
//        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)
//        val navView: NavigationView = findViewById(R.id.navView)
//        NavigationUI.setupWithNavController(navView, navController)

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
            replace(R.id.myNavHostFragment, fragment)
            commit()
        }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.myNavHostFragment)
//        return NavigationUI.navigateUp(navController, drawerLayout)
//    }
}