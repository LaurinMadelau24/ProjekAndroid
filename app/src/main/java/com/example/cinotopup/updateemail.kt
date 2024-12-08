package com.example.cinotopup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.halamanupdateemail.*


class updateemail : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halamanupdateemail)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser

        btnUpdateemail.setOnClickListener { view ->
            val email = etupdateemail.text.toString().trim()

            if (email.isEmpty()) {
                etupdateemail.error = "Email Harus Diisi"
                etupdateemail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etupdateemail.error = "Email Tidak Valid"
                etupdateemail.requestFocus()
                return@setOnClickListener
            }
            user?.let {
                user.updateEmail(email)
            }

            Intent(this@updateemail, topup::class.java).also {
                Toast.makeText(this, "Email Telah Berhasil diubah", Toast.LENGTH_SHORT).show()
                startActivity(it)
            }
        }
    }
}