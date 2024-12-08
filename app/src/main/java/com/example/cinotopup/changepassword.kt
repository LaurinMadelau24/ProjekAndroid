package com.example.cinotopup


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.halamanchangepassword.*


class changepassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halamanchangepassword)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser

        btncekpassword.setOnClickListener {
            val password = gantipassword.text.toString().trim()
            if (password.isEmpty()) {
                gantipassword.error = "Password Wajib diisi"
                gantipassword.requestFocus()
                return@setOnClickListener
            }
            user?.let {
                val userCredential = EmailAuthProvider.getCredential(it.email!!, password)
                it.reauthenticate(userCredential).addOnCompleteListener {
                }
            }
            Intent(this@changepassword, changepassword1::class.java).also {
                startActivity(it)
            }
        }

    }
}