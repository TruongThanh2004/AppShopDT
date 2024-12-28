package com.example.doan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doan.database.CartDAO
import com.example.doan.database.CartsDatabase
import kotlinx.coroutines.launch

class MainGioHang : AppCompatActivity() {

    private lateinit var dbCart: CartsDatabase
    private lateinit var cartDao: CartDAO

    private lateinit var lvProductList: ListView
    private lateinit var productList: MutableList<Phone>
    private lateinit var productApdapter: MyApdater
    private lateinit var btnMuaHang: Button
    private lateinit var tvTongGia: TextView
    private lateinit var cbChonTatCa: CheckBox
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main_gio_hang)

        dbCart = CartsDatabase.getDatabase(applicationContext)
        cartDao = dbCart.cartDao()
        setControl()
        setEvent()
    }

    private fun setEvent() {
        lifecycleScope.launch {

            val cartItems = dbCart.cartDao().getAllCartItems()
            productList = mutableListOf()

            if (cartItems.isNotEmpty()) {
                productList.addAll(cartItems.map {
                    Phone(R.drawable.dt, it.tenSP, it.gia, it.soLuong)
                })
                productApdapter = MyApdater(this@MainGioHang, productList, btnMuaHang)
                lvProductList.adapter = productApdapter
                tvTongGia.text = " ${productApdapter.getTotalPrice()} đ"
                Log.d("DatabaseLog", "Giỏ hàng: ${cartItems.size} sản phẩm.")
            } else {
                Toast.makeText(this@MainGioHang, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show()
                tvTongGia.text = "0 đ"
                btnMuaHang.isEnabled = false
            }
        }

        cbChonTatCa.setOnCheckedChangeListener { _, isChecked ->
            productList.forEach { it.isSelected = isChecked }
            productApdapter.notifyDataSetChanged()
            tvTongGia.text = " ${productApdapter.getTotalPrice()} đ"
            btnMuaHang.isEnabled = isChecked
        }

        btnMuaHang.setOnClickListener {
            val tongGiaTien = productApdapter.getTotalPrice()
            if (tongGiaTien == 0.0) {
                Toast.makeText(this, "Vui lòng chọn sản phẩm để mua", Toast.LENGTH_SHORT).show()
            } else {
                val dsDaChon = ArrayList(productList.filter { it.isSelected })
                val intent = Intent(this, MainThongTinDonHang::class.java)
                intent.putParcelableArrayListExtra("dsDaChon", dsDaChon)
                intent.putExtra("tongGiaTien", tongGiaTien)
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, MainTrangChu::class.java)
            startActivity(intent)
        }
    }


    private fun setControl() {
        lvProductList = findViewById(R.id.lvProductList)
        btnMuaHang = findViewById(R.id.btnMua)
        tvTongGia = findViewById(R.id.tvTongGia)
        cbChonTatCa = findViewById(R.id.cbChonTatCa)
        btnBack = findViewById(R.id.btnBack)
    }
}



