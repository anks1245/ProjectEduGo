package com.android.example.projecthackathon

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.example.projecthackathon.home.HomeFragment
import com.android.example.projecthackathon.job.JobFragment
import com.android.example.projecthackathon.message.MessageFragment
import com.google.android.material.appbar.MaterialToolbar
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


        drawerLayout = findViewById(R.id.drawerLayout)

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)

        val navigationView: NavigationView = findViewById(R.id.navView)
        topAppBar.setNavigationOnClickListener{
            drawerLayout.open()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.close()
            true
        }

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

        val signImageView: ImageView = findViewById(R.id.power)
        signImageView.setOnClickListener {
            startActivity(Intent(this, SignInUpActivity ::class.java))
        }


    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.myNavHostFragment, fragment)
            commit()
        }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}