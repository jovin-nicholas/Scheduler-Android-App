package com.jovin.customcalendar

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    lateinit var emailEditText: EditText
    lateinit var nameEditText: EditText
    lateinit var designationEditText: EditText
    lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailEditText = findViewById(R.id.sign_up_email_username)
        nameEditText = findViewById(R.id.sign_up_name)
        designationEditText = findViewById(R.id.sign_up_designation)
        passwordEditText = findViewById(R.id.sign_up_email_password)

        mAuth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener {
            signUpUser()
        }

        btn_sign_in.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun signUpUser() {
        val email = emailEditText.text.toString()
        val name = nameEditText.text.toString()
        val designation = designationEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty()) {
            sign_up_email_username.error = "Please enter email"
            sign_up_email_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sign_up_email_username.error = "Please enter valid email"
            sign_up_email_username.requestFocus()
            return
        }

        if (password.isEmpty()) {
            sign_up_email_password.error = "Please enter email"
            sign_up_email_password.requestFocus()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //store additional fields to database
                    val user = Users(name, email, designation)
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "User Registered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    "User not Registered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }


                    if (email == "admin@gmail.com" && password == "admin123") {
                        Toast.makeText(
                            this, "Admin Signed up",
                            Toast.LENGTH_SHORT
                        ).show()
                        admin = true
                    }

                    startActivity(Intent(this, Login::class.java))
                    finish()
                } else { // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "SignUp Authentication failed.", Toast.LENGTH_SHORT)
                        .show()
                }
                // ...
            }

    }

    companion object {
        @JvmStatic
        var admin: Boolean = false
    }
}
