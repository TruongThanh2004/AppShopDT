package com.example.doan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


import kotlin.random.Random

class MainThanhToanBangMa : AppCompatActivity() {
    private lateinit var btnCK: Button
    private lateinit var tvMaDon:TextView
    private lateinit var tvTrangThai:TextView
    private lateinit var tvThoiGian:TextView
    private lateinit var btnHuyGiaoDich:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_thanh_toan_bang_ma)


        setControl()
        setEvent()


    }

    @SuppressLint("NewApi")
    private fun setEvent() {

        CountTime(120)
        var maDonHang = "ABC"+randomMa()
        tvMaDon.text = maDonHang
        var trangThai = ""
        val time = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val current = LocalDateTime.now().format(time)
        val selectedProducts = intent.getParcelableArrayListExtra <Phone>("dsDaChon")
        val thongTin = intent.getStringExtra("thongTin")
        val tongTien = intent.getDoubleExtra("tongTien", 0.0)
        btnCK.setOnClickListener{
            trangThai = "Đơn hàng thanh toán thành công"
            val thongTinDonHang = "Mã đơn hàng:${maDonHang}\nNgày tạo đơn hàng:${current}\n"+thongTin+"\nTrạng thái:${trangThai}\nTổng tiền:${tongTien}đ"
            val intent = Intent(this,MainThanhToanThanhCong::class.java)
            intent.putParcelableArrayListExtra("dsDaChon", selectedProducts)
            intent.putExtra("thongTinDonHang",thongTinDonHang)
            startActivity(intent)
        }

        btnHuyGiaoDich.setOnClickListener{
            trangThai = "Đơn hàng thanh toán không thành công"

            val thongTinDonHang = "Mã đơn hàng:${maDonHang}\nNgày tạo đơn hàng:${current}\n"+thongTin+"\nTrạng thái:${trangThai}\nTổng tiền:${tongTien}đ"
            val intent = Intent(this,MainThanhToanDaHuy::class.java)
            intent.putParcelableArrayListExtra("dsDaChon", selectedProducts)
            intent.putExtra("thongTinDonHang",thongTinDonHang)
            startActivity(intent)
        }
    }

    private fun setControl() {
        btnCK = findViewById(R.id.btnCK)
        tvMaDon =findViewById(R.id.tvMaDon)
        tvTrangThai = findViewById(R.id.tvTrangThai)
        tvThoiGian = findViewById(R.id.tvThoiGian)
        btnHuyGiaoDich = findViewById(R.id.btnHuyGiaoDich)
    }

    fun randomMa():Int{
        var random = RanDom(1000,99999)
        return random
    }





    fun RanDom(min: Int, max: Int): Int {
        return Random.nextInt(min, max+1)
    }


    fun CountTime(time: Int) {
        Thread {
            for (timeLeft in time downTo 1) {
                runOnUiThread {
                    val minutes = timeLeft / 60
                    var seconds = timeLeft % 60



                    tvThoiGian.text = String.format("%02d:%02d",minutes, seconds)
                }
                Thread.sleep(1000)
            }
            runOnUiThread {
                Toast.makeText(this, "Đã hết thời gian thanh toán", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.start()
    }
}