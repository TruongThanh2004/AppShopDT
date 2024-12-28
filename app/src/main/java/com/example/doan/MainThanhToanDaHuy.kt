package com.example.doan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainThanhToanDaHuy : AppCompatActivity() {

    private lateinit var tvKQ: TextView
    private lateinit var lvDSDH: ListView
    private lateinit var productApdapter: PhoneApdater
    private lateinit var btnTiepTuc: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_thanh_toan_da_huy)


        setControl()
        setEvent()
    }

    private fun setEvent() {
        val selectedProducts = intent.getParcelableArrayListExtra<Phone>("dsDaChon")
        val thongTinDonHang = intent.getStringExtra("thongTinDonHang")

        selectedProducts?.let {

            productApdapter = PhoneApdater(this, it)
            lvDSDH.adapter = productApdapter
        }

        tvKQ.text =thongTinDonHang
        btnTiepTuc.setOnClickListener{
            val intent = Intent(this,MainGioHang::class.java)
            startActivity(intent)
        }
    }

        private fun setControl() {
            tvKQ = findViewById(R.id.tvKQ)
            lvDSDH = findViewById(R.id.lvDSDH)
            btnTiepTuc = findViewById(R.id.btnTiepTuc)

        }
    }
