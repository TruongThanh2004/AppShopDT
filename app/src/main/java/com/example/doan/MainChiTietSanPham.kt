package com.example.doan

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.lifecycle.lifecycleScope
import com.example.doan.database.CartDAO
import com.example.doan.database.Carts
import com.example.doan.database.CartsDatabase
import com.example.doan.database.ProductDao
import com.example.doan.database.Products
import com.example.doan.database.ProductsDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class MainChiTietSanPham : AppCompatActivity() {

    private lateinit var db: ProductsDatabase
    private lateinit var productDao: ProductDao

    private lateinit var dbCart: CartsDatabase
    private lateinit var cartDao: CartDAO

    private lateinit var tvTenSP: TextView
    private lateinit var tvGia: TextView
    private lateinit var tvChiTiet: TextView

    private lateinit var btnGioHang: Button
    private lateinit var btnBack: Button

    private lateinit var btnMuaHang:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_chi_tiet_san_pham)

        db = ProductsDatabase.getInstance(applicationContext)
        productDao = db.getProductDao()

        dbCart = CartsDatabase.getDatabase(applicationContext)
        cartDao = dbCart.cartDao()

        setControl()
        setEvent()
    }
    private fun setEvent() {
        val productId = intent.getIntExtra("productId", -1)
        Log.d("ProductID", "Sản phẩm được chọn: ${productId}")
        if (productId != -1) {
            lifecycleScope.launch {
                val product = productDao.getProductById(productId)
                if (product != null) {
                    val spannableTenSP = SpannableString("Tên sản phẩm\n${product.tenSP}")
                    spannableTenSP.setSpan(
                        ForegroundColorSpan(Color.RED),
                        0,
                        "Tên sản phẩm\n".length,
                        0
                    )
                    tvTenSP.text = spannableTenSP
                    val spannableGia = SpannableString("Giá sản phẩm\n${product.gia.toString()}đ")
                    spannableGia.setSpan(
                        ForegroundColorSpan(Color.RED),
                        0,
                        "Giá sản phẩm\n".length,
                        0
                    )
                    tvGia.text = spannableGia
                    val spannableChiTiet = SpannableString("Chi tiết sản phẩm\n${product.chitiet}")
                    spannableChiTiet.setSpan(
                        ForegroundColorSpan(Color.RED),
                        0,
                        "Chi tiết sản phẩm\n".length,
                        0
                    )
                    tvChiTiet.text = spannableChiTiet
                } else {
                    Toast.makeText(
                        this@MainChiTietSanPham,
                        "Sản phẩm không tồn tại",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                this@MainChiTietSanPham,
                "ID sản phẩm không hợp lệ",
                Toast.LENGTH_SHORT
            ).show()
        }
        btnBack.setOnClickListener {
            finish()
        }


        btnMuaHang.setOnClickListener{
            lifecycleScope.launch {

                val productId = intent.getIntExtra("productId", -1)


                val product = productDao.getProductById(productId)

                val phone = product?.let {
                    Phone(
                        imageID = 2131165343,
                        tenSP = it.tenSP,
                        gia = it.gia,
                        soLuong = 1,
                        isSelected = false
                    )
                }
                val products = ArrayList<Phone>()

                phone?.let {
                    products.add(phone)
                }
                val intent = Intent(this@MainChiTietSanPham, MainThongTinDonHang::class.java)
               intent.putParcelableArrayListExtra("dsDaChon", products)
                startActivity(intent)
            }

        }
        btnGioHang.setOnClickListener {
            lifecycleScope.launch {
                val productId = intent.getIntExtra("productId", -1)
                val product = productDao.getProductById(productId)

                if (product != null) {
                    val cart = cartDao.getCartById(productId)

                    if (cart != null) {
                        Log.e("Check","${cart}")
                        cartDao.updateSoLuong(cart.productId)
                        Log.e("Check2","${cart}")
                        Toast.makeText(
                            this@MainChiTietSanPham,
                            "Đã tăng số lượng sản phẩm trong giỏ hàng!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val cartItem = Carts(
                            productId = product.id,
                            tenSP = product.tenSP,
                            soLuong = 1,
                            gia = product.gia
                        )
                        cartDao.addCart(cartItem)
                        Toast.makeText(
                            this@MainChiTietSanPham,
                            "Thêm vào giỏ hàng thành công!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@MainChiTietSanPham,
                        "Không thể thêm sản phẩm vào giỏ hàng",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun setControl() {
        tvTenSP = findViewById(R.id.tvTenSP)
        tvGia = findViewById(R.id.tvGia)
        tvChiTiet = findViewById(R.id.tvChiTiet)
        btnGioHang = findViewById(R.id.btnThemGioHang)
        btnBack = findViewById(R.id.btnBack)
        btnMuaHang = findViewById(R.id.btnMuaSP)
    }
}
