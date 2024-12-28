package com.example.doan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Dao
import androidx.room.Database
import com.example.doan.database.ProductDao
import com.example.doan.database.Products
import com.example.doan.database.ProductsDatabase
import kotlinx.coroutines.launch

class MainDanhSachSanPham : AppCompatActivity() {

    private lateinit var db:ProductsDatabase
    private lateinit var dbDao:ProductDao

    private lateinit var lvProduct:ListView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<Products>

    private lateinit var btnIphone:Button
    private lateinit var btnSamSung:Button
    private lateinit var btnAll:Button
    private lateinit var btnback:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_danh_sach_san_pham)

        setControl()
        setEvent()
    }

    private fun setEvent() {
      loadProductData()
        lvProduct.setOnItemClickListener { parent, view, position, id ->
            val product = productList[position]
            val intent = Intent(this,MainChiTietSanPham::class.java)
            intent.putExtra("productId", product.id)
            startActivity(intent)
        }

        btnIphone.setOnClickListener{
            getDataTypeProduct("Iphone")
        }
        btnSamSung.setOnClickListener{
            getDataTypeProduct("SamSung")
        }
        btnAll.setOnClickListener{
            loadProductData()
        }
        btnback.setOnClickListener{
            finish()
        }
    }

    private fun setControl() {
        db = ProductsDatabase.getInstance(applicationContext)
        dbDao = db.getProductDao()
        lvProduct = findViewById(R.id.lvProducts)
        productList = mutableListOf()
        productAdapter = ProductAdapter(this, productList)
        lvProduct.adapter = productAdapter
        btnIphone = findViewById(R.id.btnIphone)
        btnSamSung = findViewById(R.id.btnSamSung)
        btnAll = findViewById(R.id.btnAll)
        btnback = findViewById(R.id.btnBack)
    }


    private fun getDataTypeProduct(type:String){
        lifecycleScope.launch {
            val productsFromDb = dbDao.getProductsType(type)
            productList.clear()
            productList.addAll(productsFromDb)
            productAdapter.notifyDataSetChanged()
        }
    }


    private fun loadProductData() {
        lifecycleScope.launch {
            val productsFromDb = dbDao.getAllProducts()
            productList.clear()
            productList.addAll(productsFromDb)
            productAdapter.notifyDataSetChanged()
        }
    }
}