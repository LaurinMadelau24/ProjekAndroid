package com.example.cinotopup

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_editprofil.*
import com.example.cinotopup.databinding.DrawerprofilBinding


class profile: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding: DrawerprofilBinding

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DrawerprofilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        val user : FirebaseUser? = auth.currentUser

        if (user != null){
            etName.setText(user.displayName)
            etEmail.setText(user.email)

        }
        btnUpdateemail.setOnClickListener {
            Intent(this@profile, cekpassword::class.java).also {
                startActivity(it)
            }
        }

        textgantipassword.setOnClickListener {
            Intent(this@profile, changepassword::class.java).also {
                startActivity(it)
            }
        }

//        etEmail.setOnClickListener{
//            val actionUpdateEmail = profileDirections.actionupdateemail()
//            Navigation.findNavController(it).navigate(actionUpdateEmail)
//        }



        btnUpdate.setOnClickListener{
            val name = etName.text.toString().trim()

            if (name.isEmpty()){
                etName.error = "Nama harus diisi"
                etName.requestFocus()
                return@setOnClickListener
            }
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build().also {
                    user?.updateProfile(it)?.addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(this, "Profile Update", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                        Intent(this@profile, topup::class.java).also {
                            startActivity(it)
                        }
                    }
                }

        }


        toggle = ActionBarDrawerToggle(this, binding.drawerprofil, R.string.open, R.string.close)
        binding.drawerprofil.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener(this)

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                Toast.makeText(applicationContext, "Ini Halaman Home", Toast.LENGTH_SHORT)
                    .show()
                Intent(this@profile, topup::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.profile -> {
                Toast.makeText(applicationContext, "Ini Halaman Profile", Toast.LENGTH_SHORT)
                    .show()
                Intent(this@profile, profile::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.logout -> {
                auth.signOut()
                Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                Intent(this@profile, LoginActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.cart -> {
                auth.signOut()
                Toast.makeText(this, "Halaman Keranjang", Toast.LENGTH_SHORT).show()
                Intent(this@profile, keranjang::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.history -> {
                auth.signOut()
                Toast.makeText(this, "Halaman Pembelian", Toast.LENGTH_SHORT).show()
                Intent(this@profile, pembelian::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }

        }
        binding.drawerprofil.closeDrawers()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}