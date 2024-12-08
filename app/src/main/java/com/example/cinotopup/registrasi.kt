package com.example.cinotopup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.registrasi.*
import kotlinx.android.synthetic.main.registrasi.btn_registrasi

class registrasi : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrasi)

        auth = FirebaseAuth.getInstance()

        btn_registrasi.setOnClickListener {
            val email = edtemail.text.toString().trim()
            val password = edtpassword.text.toString().trim()

            if (email.isEmpty()){
                edtemail.error = "Email Harus Diisi"
                edtemail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edtemail.error = "Email Tidak Valid"
                edtemail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                edt_password.error = "Password Harus Diisi"
                edt_password.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6){
                edtpassword.error = "Password Minimal 6 Karakter"
                edtpassword.requestFocus()
                return@setOnClickListener
            }
            RegistrasiUser(email, password)

    }
        login.setOnClickListener {
            Intent(this@registrasi, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }
    private fun RegistrasiUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    Intent(this@registrasi, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}