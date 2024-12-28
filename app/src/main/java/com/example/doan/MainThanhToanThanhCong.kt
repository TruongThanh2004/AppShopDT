    package com.example.doan

    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.widget.Button
    import android.widget.ListView
    import android.widget.TextView
    import android.widget.Toast
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import androidx.lifecycle.lifecycleScope
    import androidx.room.Database
    import com.example.doan.database.CartDAO
    import com.example.doan.database.CartsDatabase
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.GlobalScope
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext

    class MainThanhToanThanhCong : AppCompatActivity() {
        private lateinit var btnThanhToanThanhCong:Button
        private lateinit var tvKQ: TextView
        private lateinit var lvDSDH: ListView
        private lateinit var productApdapter: PhoneApdater

        private lateinit var db:CartsDatabase
        private lateinit var dbCart:CartDAO

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main_thanh_toan_thanh_cong)
            setControl()
            setEvent()
        }

        private fun setEvent() {
            val selectedProducts = intent.getParcelableArrayListExtra<Phone>("dsDaChon")
            val thongTinDonHang = intent.getStringExtra("thongTinDonHang")

            selectedProducts?.let { product->

                productApdapter = PhoneApdater(this, product)
                lvDSDH.adapter = productApdapter
            }
            tvKQ.text =thongTinDonHang
            btnThanhToanThanhCong.setOnClickListener {
                selectedProducts?.let { products ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        val dbHelper = CartsDatabase.getDatabase(applicationContext)
                        for (product in products) {
                             dbHelper.cartDao().deleteProductByNameAndPrice(product.tenSP, product.gia)
                        }
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@MainThanhToanThanhCong, MainTrangChu::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }

        private fun setControl() {
            db = CartsDatabase.getDatabase(applicationContext)
            dbCart = db.cartDao()
            btnThanhToanThanhCong = findViewById(R.id.btnThanhToanThanhCong)
            tvKQ = findViewById(R.id.tvKQ)
            lvDSDH = findViewById(R.id.lvDSDH)
        }



    }