package com.android.example.projecthackathon.login_signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.example.projecthackathon.MainActivity
import com.android.example.projecthackathon.databinding.FragmentLoginBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage


class LoginFragment : Fragment() {

    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val  binding = FragmentLoginBinding.inflate(inflater)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        binding.login.setOnClickListener{
            val email: String = binding.username.editText?.text.toString()
            val pass: String = binding.password.editText?.text.toString()

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

            checkUserExists(email)

            //open the main activity
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
        return binding.root
    }

    private fun checkUserExists(email: String) {
        val databaseReference = database.reference.child("users")
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.value
                Toast.makeText(context,""+post?.javaClass,Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        databaseReference.addValueEventListener(postListener)

    }

    private fun validatePassword(binding: FragmentLoginBinding) {
        if (binding.password.editText?.text.toString().isEmpty()) {
            binding.password.error = "This field cannot be empty"
            return
        }
        binding.password.error = null
        binding.password.isErrorEnabled = false
        return
    }
    private fun validateUserName(binding: FragmentLoginBinding) {
        if (binding.username.editText?.text.toString().isEmpty()) {
            binding.username.error = "This field cannot be empty"
            return
        }
        binding.username.error = null
        binding.username.isErrorEnabled = false
        return
    }

}