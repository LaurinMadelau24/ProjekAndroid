package com.example.cinotopup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import com.example.cinotopup.databinding.DrawervaloBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.valorant.*


class halamanvalorant : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: DrawervaloBinding

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth : FirebaseAuth

    private lateinit var edtNickname : EditText
    private lateinit var edtjumlahTopup : EditText
    private lateinit var edtharga : EditText
    private lateinit var edtpembayaran : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DrawervaloBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        toggle = ActionBarDrawerToggle(this, binding.drawervalo, R.string.open, R.string.close)
        binding.drawervalo.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener(this)


        edtNickname = findViewById(R.id.editnickname)
        edtjumlahTopup = findViewById(R.id.edittopup)
        edtharga = findViewById(R.id.editharga)
        edtpembayaran = findViewById(R.id.editpembayaran)


        btn_keranjangvalo.setOnClickListener {
            val nickname = edtNickname.text.toString().trim()
            val jmltopup = edtjumlahTopup.text.toString().trim()
            val harga = edtharga.text.toString().trim()
            val pembayaran = edtpembayaran.text.toString().trim()

            if (nickname.isEmpty()){
                edtNickname.error = "Isi NickName! wajib diisi"
                return@setOnClickListener
            }

            if (jmltopup.isEmpty()){
                edtjumlahTopup.error = "Jumlah TopUp wajib diisi"
                return@setOnClickListener
            }

            if (harga.isEmpty()){
                edtharga.error = "Harga Wajib diisi"
                return@setOnClickListener
            }

            if (pembayaran.isEmpty()){
                edtpembayaran.error = "Metode Pembayaran wajib diisi"
            }
            val ref = FirebaseDatabase.getInstance().getReference("cart")
            val cartid = ref.push().key

            val Keranjang = cart(cartid!!,nickname,jmltopup, harga, pembayaran)

            if(cartid != null){
                ref.child(cartid).setValue(Keranjang).addOnCompleteListener {
                    Toast.makeText(applicationContext,"Pesanan masuk ke dalam keranjang", Toast.LENGTH_SHORT).show()
                }

            }
        }

        btnbuyvalo.setOnClickListener{
            val nickname = edtNickname.text.toString().trim()
            val jmltopup = edtjumlahTopup.text.toString().trim()
            val harga = edtharga.text.toString().trim()
            val pembayaran = edtpembayaran.text.toString().trim()

            if (nickname.isEmpty()){
                edtNickname.error = "Isi NickName! wajib diisi"
                return@setOnClickListener
            }

            if (jmltopup.isEmpty()){
                edtjumlahTopup.error = "Jumlah TopUp wajib diisi"
                return@setOnClickListener
            }

            if (harga.isEmpty()){
                edtharga.error = "Harga Wajib diisi"
                return@setOnClickListener
            }

            if (pembayaran.isEmpty()){
                edtpembayaran.error = "Metode Pembayaran wajib diisi"
            }
            val ref = FirebaseDatabase.getInstance().getReference("beli")
            val pembelianid = ref.push().key

            val pesan = beli(pembelianid!!,nickname,jmltopup, harga, pembayaran)

            if(pembelianid != null){
                ref.child(pembelianid).setValue(pesan).addOnCompleteListener {
                    Toast.makeText(applicationContext,"Pemesanan Telah Berhasil", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                Toast.makeText(applicationContext, "Ini Halaman Home", Toast.LENGTH_SHORT)
                    .show()
                Intent(this@halamanvalorant, topup::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.profile -> {
                Toast.makeText(applicationContext, "Ini Halaman Profile", Toast.LENGTH_SHORT)
                    .show()
                Intent(this@halamanvalorant, profile::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.logout -> {
                auth.signOut()
                Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                Intent(this@halamanvalorant, LoginActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.cart -> {
                auth.signOut()
                Toast.makeText(this, "Halaman Keranjang", Toast.LENGTH_SHORT).show()
                Intent(this@halamanvalorant, keranjang::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.history -> {
                auth.signOut()
                Toast.makeText(this, "Halaman Pembelian", Toast.LENGTH_SHORT).show()
                Intent(this@halamanvalorant, pembelian::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }

        }
        binding.drawervalo.closeDrawers()
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
