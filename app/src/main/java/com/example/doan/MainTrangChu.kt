package com.example.doan

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.example.doan.database.ProductDao
import com.example.doan.database.Products
import com.example.doan.database.ProductsDatabase
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MainTrangChu : AppCompatActivity() {
    private lateinit var db: ProductsDatabase
    private lateinit var productDao: ProductDao
    private lateinit var lvProducts: ListView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<Products>
    private lateinit var navigationView:NavigationView

    private lateinit var bannerContainer: LinearLayout
    private lateinit var banners: List<ImageView>
    private var screenWidth: Int = 0
    private var currentBannerIndex = 0
    lateinit var toolbar: Toolbar
    private lateinit var drawLayout : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_trang_chu)


        db = ProductsDatabase.getInstance(applicationContext)
        productDao = db.getProductDao()


        setControl()
        setEvent()
    }

    private fun setControl() {

        lvProducts = findViewById(R.id.lvProducts)
        productList = mutableListOf()
        productAdapter = ProductAdapter(this, productList)
        lvProducts.adapter = productAdapter
        bannerContainer = findViewById(R.id.bannerContainer)
        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.toolbar)
        drawLayout = findViewById(R.id.draw_layout)
    }

    private fun setEvent() {
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
        toolbar.setNavigationOnClickListener{
            drawLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_cart -> {
                    val intent = Intent(this, MainGioHang::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_DanhSachSP -> {
                    val intent = Intent(this, MainDanhSachSanPham::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_DangXuat -> {
                    val intent = Intent(this, MainDangNhap::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }

        }
        drawLayout.closeDrawer(GravityCompat.START)
        banners = listOf(
            findViewById(R.id.bannerImage1),
            findViewById(R.id.bannerImage2)
        )
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        banners.forEach { it.visibility = View.GONE }
        banners[0].visibility = View.VISIBLE
        startBannerAnimation()
        loadProductData()
        lvProducts.setOnItemClickListener { parent, view, position, id ->
            val product = productList[position]
            Log.d("ProductInfo", "Sản phẩm được chọn: ${product.id}")
            val intent = Intent(this,MainChiTietSanPham::class.java)
            intent.putExtra("productId", product.id)
            startActivity(intent)
        }
    }

    private fun loadProductData() {

        lifecycleScope.launch {
            val productsFromDb = productDao.getAllProducts()
            productList.clear()
            productList.addAll(productsFromDb)
            productAdapter.notifyDataSetChanged()
        }
    }
    private fun startBannerAnimation() {
        val handler = Handler(Looper.getMainLooper())
        val bannerSwitchRunnable = object : Runnable {
            override fun run() {
                switchBanner()
                handler.postDelayed(this, 3000)
            }
        }
        handler.post(bannerSwitchRunnable)
    }

    private fun switchBanner() {
        banners[currentBannerIndex].visibility = View.GONE
        currentBannerIndex = (currentBannerIndex + 1) % banners.size
        val nextBanner = banners[currentBannerIndex]
        nextBanner.translationX = screenWidth.toFloat() // Đặt ngoài màn hình
        nextBanner.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(nextBanner, "translationX", 0f).apply {
            duration = 1000

            start()
        }
    }




}
