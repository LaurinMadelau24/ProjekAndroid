package com.example.cinotopup

data class beli (
    val id : String,
    val nickname : String,
    val jmltopup : String,
    val harga : String,
    val pembayaran : String
){
    constructor(): this("","","","",""){

    }
}