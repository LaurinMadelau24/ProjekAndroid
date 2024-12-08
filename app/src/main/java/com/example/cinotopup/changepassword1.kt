package com.example.cinotopup


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.halamanchangepassword1.*


class changepassword1 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halamanchangepassword1)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser

        btnupdatepassword.setOnClickListener{
            val newpassword = gantipassword1.text.toString().trim()
            val passwordconfirm = gantipassword2.text.toString().trim()

            if (newpassword.isEmpty() || newpassword.length < 6){
                gantipassword1.error = "Password harus lebih dari 6 karakter"
                gantipassword1.requestFocus()
                return@setOnClickListener
            }
            if (newpassword != passwordconfirm){
                gantipassword2.error = "Password tidak sama"
                gantipassword2.requestFocus()
                return@setOnClickListener
            }

            user?.let {
                user.updatePassword(newpassword)
            }
            Intent(this@changepassword1, topup::class.java).also {
                Toast.makeText(this, "Password Telah Berhasil diganti", Toast.LENGTH_SHORT).show()
                startActivity(it)
            }
        }
    }
}