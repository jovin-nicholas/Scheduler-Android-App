package com.jovin.customcalendar

import android.content.Context
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            signInUser()
        }
        btn_sign_up.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun signInUser() {
        if (login_email_username.text.toString().isEmpty()) {
            login_email_username.error = "Please enter email"
            login_email_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(login_email_username.text.toString()).matches()) {
            login_email_username.error = "Please enter valid email"
            login_email_username.requestFocus()
            return
        }

        if (login_email_password.text.toString().isEmpty()) {
            login_email_password.error = "Please enter email"
            login_email_password.requestFocus()
            return
        }


        mAuth.signInWithEmailAndPassword(login_email_username.text.toString(), login_email_password.text.toString())
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        this, "SignIn Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
                // ...
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null) {

            if(login_email_username.text.toString() == "admin@gmail.com"  && login_email_password.text.toString() == "admin123"){
                Toast.makeText(
                    this, "Admin Signed in",
                    Toast.LENGTH_LONG
                ).show()
                Register.admin = true
            }

            Toast.makeText(
                this, "Authentication success.",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else{
            Toast.makeText(
                this, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}


