package com.example.cinotopup

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class cartadapter (val mCtx : Context, val layoutRestId : Int, val cartList : List<cart>) :
    ArrayAdapter<cart>(mCtx,layoutRestId,cartList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutRestId,null)

        val tampilnickname : TextView = view.findViewById(R.id.tampil_nicknamecart)
        val tampiltopup : TextView = view.findViewById(R.id.tampil_topupcart)
        val tampilharga : TextView = view.findViewById(R.id.tampil_hargacart)
        val tampilpembayaran : TextView = view.findViewById(R.id.tampil_metodepembayarancart)
        val edtcart : TextView = view.findViewById(R.id.editcart)
        val PesanCart : TextView = view.findViewById(R.id.edtpesan)
        val cart = cartList[position]

        PesanCart.setOnClickListener{
            val nickname = tampilnickname.text.toString().trim()
            val jmltopup = tampiltopup.text.toString().trim()
            val harga = tampilharga.text.toString().trim()
            val pembayaran = tampilpembayaran.text.toString().trim()

            val ref = FirebaseDatabase.getInstance().getReference("beli")
            val pembelianid = ref.push().key

            val pesan = beli(pembelianid!!,nickname,jmltopup, harga, pembayaran)

            if(pembelianid != null){
                ref.child(pembelianid).setValue(pesan).addOnCompleteListener {
                    Toast.makeText(mCtx,"Pemesanan Telah Berhasil", Toast.LENGTH_SHORT).show()
                }
            }
        }

        edtcart.setOnClickListener{
            showUpdateDialog(cart)
        }

        tampilnickname.text = cart.nickname
        tampiltopup.text = cart.jmltopup
        tampilharga.text = cart.harga
        tampilpembayaran.text = cart.pembayaran

        return view

    }

    fun showUpdateDialog(cart: cart) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Ubah Keranjang")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.updatedialogcart, null)

        val etnickname = view.findViewById<EditText>(R.id.editnicknamed)
        val ettopup = view.findViewById<EditText>(R.id.edittopupd)
        val etharga = view.findViewById<EditText>(R.id.edithargad)
        val etbayar = view.findViewById<EditText>(R.id.editpembayarand)

        etnickname.setText(cart.nickname)
        ettopup.setText(cart.jmltopup)
        etharga.setText(cart.harga)
        etbayar.setText(cart.pembayaran)


        builder.setView(view)

        builder.setPositiveButton("Update"){p0,p1 ->
            val dbcart = FirebaseDatabase.getInstance().getReference("cart")

            val edtnickname = etnickname.text.toString().trim()
            val edttopup = ettopup.text.toString().trim()
            val edtharga = etharga.text.toString().trim()
            val edtbayar = etbayar.text.toString().trim()
            if (edtnickname.isEmpty()){
                etnickname.error = "Nickname wajib diisi"
                etnickname.requestFocus()
                return@setPositiveButton
            }
            if (edttopup.isEmpty()){
                ettopup.error = "Jumlah Topup wajib diisi"
                ettopup.requestFocus()
                return@setPositiveButton
            }
            if (edtharga.isEmpty()){
                etharga.error = "Harga wajib diisi"
                etharga.requestFocus()
                return@setPositiveButton
            }
            if (edtbayar.isEmpty()){
                etbayar.error = "Metode Pembayaran wajib diisi"
                etbayar.requestFocus()
                return@setPositiveButton
            }
            val Cart = cart(cart.id,edtnickname,edttopup,edtharga,edtbayar)
            dbcart.child(cart.id!!).setValue(Cart)

            Toast.makeText(mCtx, "Data Keranjang berhasil diupdate", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("NO"){p0,p1->

        }

        builder.setNegativeButton("DELETE"){p0,p1 ->
            val dbcart = FirebaseDatabase.getInstance().getReference("cart").child(cart.id)
            dbcart.removeValue()
            Toast.makeText(mCtx, "Data Keranjang berhasil dihapus",Toast.LENGTH_SHORT).show()
        }
        val alert = builder.create()
        alert.show()
    }
}