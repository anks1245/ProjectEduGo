package com.android.example.projecthackathon

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
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
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var signImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment()
        val jobFragment = JobFragment()
        val messageFragment = MessageFragment()

        signImageView = findViewById(R.id.power)

        sharedPreferences = getSharedPreferences("user", android.content.Context.MODE_PRIVATE)

        drawerLayout = findViewById(R.id.drawerLayout)

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)

        val navigationView: NavigationView = findViewById(R.id.navView)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.signFragment -> {
                    sharedPreferences.edit().putString("emailKey", null).apply()
                    Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show()
                    signImageView.visibility = View.VISIBLE
                }
            }
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
                R.id.message -> {
                    if (sharedPreferences.getString("emailKey", null).isNullOrEmpty())
                        Toast.makeText(this, "Login first to use this feature", Toast.LENGTH_SHORT).show()
                    else
                        setCurrentFragment(messageFragment)
                }
            }
            true
        }

        signImageView.setOnClickListener {
            startActivity(Intent(this, SignInUpActivity::class.java))
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

    override fun onResume() {
        super.onResume()
        val email = sharedPreferences.getString("emailKey", null)
        if (!email.isNullOrEmpty())
            signImageView.visibility = View.GONE
    }
}