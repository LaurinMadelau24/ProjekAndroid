package com.example.cinotopup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import com.example.cinotopup.databinding.DrawerpbBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.pb.*
import kotlinx.android.synthetic.main.valorant.*


class halamanpointblank : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: DrawerpbBinding

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth : FirebaseAuth

    private lateinit var edtNicknamepb : EditText
    private lateinit var edtjumlahTopuppb : EditText
    private lateinit var edthargapb : EditText
    private lateinit var edtpembayaranpb : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DrawerpbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        toggle = ActionBarDrawerToggle(this, binding.drawerpb, R.string.open, R.string.close)
        binding.drawerpb.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener(this)

        edtNicknamepb = findViewById(R.id.edtnickname)
        edtjumlahTopuppb = findViewById(R.id.edttopup)
        edthargapb = findViewById(R.id.edtharga)
        edtpembayaranpb = findViewById(R.id.edtbayar)


        btnbuypb.setOnClickListener{
            val nickname = edtNicknamepb.text.toString().trim()
            val jmltopup = edtjumlahTopuppb.text.toString().trim()
            val harga = edthargapb.text.toString().trim()
            val pembayaran = edtpembayaranpb.text.toString().trim()

            if (nickname.isEmpty()){
                edtNicknamepb.error = "Isi NickName! wajib diisi"
                return@setOnClickListener
            }

            if (jmltopup.isEmpty()){
                edtjumlahTopuppb.error = "Jumlah TopUp wajib diisi"
                return@setOnClickListener
            }

            if (harga.isEmpty()){
                edthargapb.error = "Harga Wajib diisi"
                return@setOnClickListener
            }

            if (pembayaran.isEmpty()){
                edtpembayaranpb.error = "Metode Pembayaran wajib diisi"
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

        btn_keranjangpb.setOnClickListener{
            val nickname = edtNicknamepb.text.toString().trim()
            val jmltopup = edtjumlahTopuppb.text.toString().trim()
            val harga = edthargapb.text.toString().trim()
            val pembayaran = edtpembayaranpb.text.toString().trim()

            if (nickname.isEmpty()){
                edtNicknamepb.error = "Isi NickName! wajib diisi"
                return@setOnClickListener
            }

            if (jmltopup.isEmpty()){
                edtjumlahTopuppb.error = "Jumlah TopUp wajib diisi"
                return@setOnClickListener
            }

            if (harga.isEmpty()){
                edthargapb.error = "Harga Wajib diisi"
                return@setOnClickListener
            }

            if (pembayaran.isEmpty()){
                edtpembayaranpb.error = "Metode Pembayaran wajib diisi"
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
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                Toast.makeText(applicationContext, "Ini Halaman Home", Toast.LENGTH_SHORT)
                    .show()
                Intent(this@halamanpointblank, topup::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.profile -> {
                Toast.makeText(applicationContext, "Ini Halaman Profile", Toast.LENGTH_SHORT)
                    .show()
                Intent(this@halamanpointblank, profile::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.logout -> {
                auth.signOut()
                Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                Intent(this@halamanpointblank, LoginActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.cart -> {
                auth.signOut()
                Toast.makeText(this, "Halaman Keranjang", Toast.LENGTH_SHORT).show()
                Intent(this@halamanpointblank, keranjang::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.history -> {
                auth.signOut()
                Toast.makeText(this, "Halaman Pembelian", Toast.LENGTH_SHORT).show()
                Intent(this@halamanpointblank, pembelian::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }

        }
        binding.drawerpb.closeDrawers()
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
