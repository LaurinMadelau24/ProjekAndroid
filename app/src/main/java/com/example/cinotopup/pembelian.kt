package com.example.cinotopup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import com.example.cinotopup.databinding.DrawerpembelianBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class pembelian: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding: DrawerpembelianBinding

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth : FirebaseAuth

    private lateinit var listbeli : ListView
    private lateinit var ref : DatabaseReference
    private lateinit var beliList : MutableList<beli>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DrawerpembelianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        toggle = ActionBarDrawerToggle(this, binding.drawerpembelian, R.string.open, R.string.close)
        binding.drawerpembelian.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener(this)

        ref = FirebaseDatabase.getInstance().getReference("beli")

        listbeli = findViewById(R.id.lv_hasilbeli)
        beliList = mutableListOf()


        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    beliList.clear()
                    for(h in p0.children){
                        val Beli = h.getValue(beli::class.java)
                        if (Beli != null){
                            beliList.add(Beli)
                        }
                    }
                    val adapter = beliadapter(this@pembelian, R.layout.layout_itempembelian, beliList)
                    listbeli.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                Toast.makeText(applicationContext, "Ini Halaman Home", Toast.LENGTH_SHORT)
                    .show()
                Intent(this@pembelian, topup::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.profile -> {
                Toast.makeText(applicationContext, "Ini Halaman Profile", Toast.LENGTH_SHORT)
                    .show()
                Intent(this@pembelian, profile::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.logout -> {
                auth.signOut()
                Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                Intent(this@pembelian, LoginActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.cart -> {
                auth.signOut()
                Toast.makeText(this, "Halaman Keranjang", Toast.LENGTH_SHORT).show()
                Intent(this@pembelian, keranjang::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            R.id.history -> {
                auth.signOut()
                Toast.makeText(this, "Halaman Pembelian", Toast.LENGTH_SHORT).show()
                Intent(this@pembelian, pembelian::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }

        }
        binding.drawerpembelian.closeDrawers()
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
