package com.example.cinotopup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.cinotopup.databinding.ActivityHeaderBinding
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_header.*

class halamanheader: AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val user : FirebaseUser? = auth.currentUser

        if (user != null){
            textname.setText(user.displayName)
            textemail.setText(user.email)
        }
    }
}