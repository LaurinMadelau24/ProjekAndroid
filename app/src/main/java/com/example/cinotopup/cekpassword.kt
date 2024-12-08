package com.example.cinotopup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.halamanemailpass.*

class cekpassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halamanemailpass)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser

        btncek.setOnClickListener {
            val password = etpasswordemail.text.toString().trim()
            if (password.isEmpty()) {
                etpasswordemail.error = "Password Wajib diisi"
                etpasswordemail.requestFocus()
                return@setOnClickListener
            }
            user?.let {
                val userCredential = EmailAuthProvider.getCredential(it.email!!, password)
                it.reauthenticate(userCredential).addOnCompleteListener {
                }
            }
            Intent(this@cekpassword, updateemail::class.java).also {
                startActivity(it)
            }
        }
    }
}