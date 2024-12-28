package com.example.doan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainThanhToan : AppCompatActivity() {
    private lateinit var btnThanhToan:Button
    private lateinit var btnBack:Button
    private lateinit var tvTongGia:TextView
    private lateinit var tvThongTin:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_thanh_toan)

        setControl()
        setEvent()

    }

    private fun setEvent() {

        val selectedProducts = intent.getParcelableArrayListExtra <Phone>("dsDaChon")
        val tongTien = intent.getDoubleExtra("tongGiaTien", 0.0)
        val thongTin = intent.getStringExtra("thongTin")
        btnThanhToan.setOnClickListener{
            val intent = Intent(this,MainThanhToanBangMa::class.java)
            intent.putExtra("thongTin",thongTin)
            intent.putParcelableArrayListExtra("dsDaChon", selectedProducts)
            intent.putExtra("tongTien",tongTien)
            startActivity(intent)
        }
        btnBack.setOnClickListener{
            finish()
        }



        tvTongGia.text = "${tongTien} đ"
        tvThongTin.text = thongTin
    }

    private fun setControl() {
        btnThanhToan = findViewById(R.id.btnThanhToan)
        btnBack = findViewById(R.id.btnBack)
        tvTongGia = findViewById(R.id.tvTongGia)
        tvThongTin = findViewById(R.id.tvThongTin)
    }
}