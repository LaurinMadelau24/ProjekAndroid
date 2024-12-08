package com.example.cinotopup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        btnlogin.setOnClickListener {
            val email = edt_email.text.toString().trim()
            val password = edt_password.text.toString().trim()

                if (email.isEmpty()) {
                edt_email.error = "Email Harus Diisi"
                edt_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edt_email.error = "Email Tidak Valid"
                edt_email.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                edt_password.error = "Password Harus Diisi"
                edt_password.requestFocus()
                return@setOnClickListener
            }

            LoginUser(email, password)
        }

        btn_registrasi.setOnClickListener {
            Intent(this@LoginActivity, registrasi::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun LoginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    Intent(this@LoginActivity, topup::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

