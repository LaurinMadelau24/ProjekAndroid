package com.example.cinotopup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class beliadapter (val mCtx : Context, val layoutRestId : Int, val beliList : List<beli>) :
    ArrayAdapter<beli>(mCtx,layoutRestId,beliList) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
            val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

            val view : View = layoutInflater.inflate(layoutRestId,null)

            val tampilnickname : TextView = view.findViewById(R.id.tampil_nickname)
            val tampiltopup : TextView = view.findViewById(R.id.tampil_topup)
            val tampilharga : TextView = view.findViewById(R.id.tampil_harga)
            val tampilpembayaran : TextView = view.findViewById(R.id.tampil_metodepembayaran)

            val beli = beliList[position]

            tampilnickname.text = beli.nickname
            tampiltopup.text = beli.jmltopup
            tampilharga.text = beli.harga
            tampilpembayaran.text = beli.pembayaran

            return view

        }
}