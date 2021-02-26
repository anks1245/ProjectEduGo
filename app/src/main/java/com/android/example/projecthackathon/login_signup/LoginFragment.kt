package com.android.example.projecthackathon.login_signup

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.example.projecthackathon.MainActivity
import com.android.example.projecthackathon.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val  binding = FragmentLoginBinding.inflate(inflater)

        binding.login.setOnClickListener{

            //open the main activity
            startActivity(Intent(context, MainActivity :: class.java))
            activity?.finish()
        }
        return binding.root
    }
}