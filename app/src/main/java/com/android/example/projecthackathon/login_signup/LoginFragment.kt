package com.android.example.projecthackathon.login_signup

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.android.example.projecthackathon.MainActivity
import com.android.example.projecthackathon.databinding.FragmentLoginBinding
import com.google.firebase.database.*


class LoginFragment : Fragment() {

    lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val  binding = FragmentLoginBinding.inflate(inflater)

        database = FirebaseDatabase.getInstance()

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

            if (validateUserName(binding) && validatePassword(binding)) {
                binding.loader.visibility = View.VISIBLE
                checkUserExists(email,pass, binding)
            }
        }
        return binding.root
    }

    private fun checkUserExists(email: String, pass: String, binding: FragmentLoginBinding) {
        val databaseReference = database.reference.child("users")

        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                    val post = snapshot.value
                    if (post == null) {
                        Toast.makeText(context, "User does not exist, Register here!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                    } else {
                        binding.loader.visibility = View.GONE
                        val sharedPreferences = context?.getSharedPreferences("user", android.content.Context.MODE_PRIVATE)
                        if (email == sharedPreferences?.getString("userKey", null) && pass == sharedPreferences.getString("passKey", null)) {
                            startActivity(Intent(context, MainActivity::class.java))
                            sharedPreferences.edit().putString("emailKey", email).apply()
                            Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            if (findNavController().currentDestination!!.id == 2131362049) {
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                                Toast.makeText(context, "User does not exist, Register here!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        databaseReference.addValueEventListener(postListener)

    }

    private fun validatePassword(binding: FragmentLoginBinding): Boolean {
        if (binding.password.editText?.text.toString().isEmpty()) {
            binding.password.error = "This field cannot be empty"
            return false
        }
        binding.password.error = null
        binding.password.isErrorEnabled = false
        return true
    }
    private fun validateUserName(binding: FragmentLoginBinding): Boolean {
        if (binding.username.editText?.text.toString().isEmpty()) {
            binding.username.error = "This field cannot be empty"
            return false
        }
        binding.username.error = null
        binding.username.isErrorEnabled = false
        return true
    }

}