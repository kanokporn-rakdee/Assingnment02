package com.example.login02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.reflect.Member

class Login : AppCompatActivity() {



    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser!=null){
            val it = Intent(this, Member::class.java)
            startActivity(it)
            finish()
        }

        textView.setOnClickListener{
            val Email = edtEmail.getText().toString().trim()
            val Pass = edtPassword.getText().toString().trim()

            if (Email.isEmpty()){
                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(Pass.isEmpty()){
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            auth!!.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener{task ->
                if (!task.isSuccessful){
                    if (Pass.length < 8){
                        edtPassword.error = "Password must be 8-16 Character"
                    }
                    else{
                        Toast.makeText(this,"Login failed: "+ task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show()
                    val it = Intent(this, Member::class.java)
                    startActivity(it)
                    finish()
                }
            }
        }


        cmdRegister.setOnClickListener {
            val it = Intent(this, Register::class.java)
            startActivity(it)
        }

        cmdBack.setOnClickListener {
            onBackPressed()
        }
    }
}