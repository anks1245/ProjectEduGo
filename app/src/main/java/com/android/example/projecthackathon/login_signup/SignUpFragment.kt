package com.android.example.projecthackathon.login_signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.example.projecthackathon.MainActivity
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.databinding.FragmentLoginBinding
import com.android.example.projecthackathon.databinding.FragmentSignUpBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignUpFragment : Fragment() {

    lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSignUpBinding.inflate(inflater)

        database = FirebaseDatabase.getInstance()

        binding.register.setOnClickListener{

            val name = binding.name.editText?.text.toString()
            val email = binding.username.editText?.text.toString()
            val pass = binding.password.editText?.text.toString()
            val confirmPass = binding.confirmPassword.editText?.text.toString()
            val uType = binding.uType.checkedRadioButtonId

            validateName(binding)
            validateUserName(binding)
            validatePassword(binding)
            validateConfirmPassword(binding)
            validateUType(binding)


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

            if (validateUserName(binding) && validatePassword(binding))
                checkUserExists(email)
        }
        return binding.root
    }

    private fun checkUserExists(email: String) {
        val databaseReference = database.reference.child("users")
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.value
                if (post == null) {
                    Toast.makeText(
                        context,
                        "User does not exist, Register here!",
                        Toast.LENGTH_SHORT
                    ).show()
                    //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                } else {
                    startActivity(Intent(context, MainActivity::class.java))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

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


    private fun validateConfirmPassword(binding: FragmentSignUpBinding): Boolean {
        if (binding.confirmPassword.editText?.text.toString().isEmpty()) {
            binding.confirmPassword.error = "This field cannot be empty"
            return false
        } else if (binding.confirmPassword.editText!!.text != binding.password.editText!!.text) {
            binding.confirmPassword.error = "Password does not match"
            return false
        }
        binding.confirmPassword.error = null
        binding.confirmPassword.isErrorEnabled = false
        return true
    }

    private fun validateUType(binding: FragmentSignUpBinding) {

    }
}