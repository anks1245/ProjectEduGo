package com.android.example.projecthackathon.login_signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.android.example.projecthackathon.MainActivity
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.databinding.FragmentSignUpBinding
import com.android.example.projecthackathon.helper.User
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Context

class SignUpFragment : Fragment() {

    private lateinit var database: FirebaseDatabase

    lateinit var name: String
    lateinit var email: String
    lateinit var pass: String
    private var uType: Int = R.id.seeker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSignUpBinding.inflate(inflater)

        database = FirebaseDatabase.getInstance()

        binding.register.setOnClickListener {

            name = binding.name.editText?.text.toString()
            email = binding.username.editText?.text.toString()
            pass = binding.password.editText?.text.toString()

            uType = binding.uType.checkedRadioButtonId

            validateName(binding)
            validateUserName(binding)
            validatePassword(binding)


            binding.username.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    validateUserName(binding)
                }

                override fun afterTextChanged(s: Editable) {}
            })
            binding.password.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    validatePassword(binding)
                }

                override fun afterTextChanged(s: Editable) {}
            })
            binding.name.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    validateName(binding)
                }

                override fun afterTextChanged(s: Editable) {}
            })

            if (validateUserName(binding) && validatePassword(binding) && validatePassword(binding))
                createAccount()
        }
        return binding.root
    }

    private fun createAccount() {
        val databaseReference = database.reference.child("users").child(email.replace("@gmail.com","", true))
        val user = User(
            name, pass, email, when (uType) {
                R.id.seeker -> "seeker"
                else -> "employer"
            }
        )

        databaseReference.setValue(user).addOnSuccessListener(OnSuccessListener {
            Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show()
            val sharedPreferences = context?.getSharedPreferences("user", android.content.Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString("userKey", email)
            editor?.putString("emailKey", email)
            editor?.putString("passKey", pass)
            editor?.apply()

            startActivity(Intent(context, MainActivity::class.java))
        })
    }

    private fun validateName(binding: FragmentSignUpBinding): Boolean {
        if (binding.name.editText?.text.toString().isEmpty()) {
            binding.name.error = "This field cannot be empty"
            return false
        }
        binding.name.error = null
        binding.name.isErrorEnabled = false
        return true
    }

    private fun validateUserName(binding: FragmentSignUpBinding): Boolean {
        if (binding.username.editText?.text.toString().isEmpty()) {
            binding.username.error = "This field cannot be empty"
            return false
        }
        binding.username.error = null
        binding.username.isErrorEnabled = false
        return true
    }

    private fun validatePassword(binding: FragmentSignUpBinding): Boolean {
        if (binding.password.editText?.text.toString().isEmpty()) {
            binding.password.error = "This field cannot be empty"
            return false
        } else if (binding.password.editText!!.text.length < 8) {
            binding.password.error = "Password length should be greater than 7 characters"
            return false
        }
        binding.password.error = null
        binding.password.isErrorEnabled = false
        return true
    }

}