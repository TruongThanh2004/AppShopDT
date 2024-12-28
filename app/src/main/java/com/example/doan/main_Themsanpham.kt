package com.example.doan

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.doan.database.ProductDao
import com.example.doan.database.Products
import com.example.doan.database.ProductsDatabase
import kotlinx.coroutines.launch


class main_Themsanpham : AppCompatActivity() {

    private lateinit var db: ProductsDatabase
    private lateinit var productDao: ProductDao
    private lateinit var productList: MutableList<Products>
    private lateinit var productAdapter: main_Chitietsanpham
    private lateinit var lvProductList: ListView
    private lateinit var edtTen: EditText
    private lateinit var edtGia: EditText
    private lateinit var edtChiTiet:EditText
    private lateinit var spProductType: Spinner
    private lateinit var btnThem: Button
    private lateinit var btnLuu: Button
    private lateinit var btnHuy: Button
    private lateinit var btnTrangChu: Button
    private var  currentProductId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_themsanpham)
        setControl()
        setEvent()
    }

    private fun loadProductData() {
        lifecycleScope.launch {
            val productsFromDb = productDao.getAllProducts()
            productList.clear()
            productList.addAll(productsFromDb)
            productAdapter.notifyDataSetChanged()
        }
    }

    private fun setEvent() {

        // Khởi tạo danh sách sản phẩm và adapter
        productList = mutableListOf()
        productAdapter = main_Chitietsanpham(this, productList)
        lvProductList.adapter = productAdapter
        val productTypeList = listOf("Iphone", "SamSung")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, productTypeList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Đảm bảo dropdown đẹp mắt
        spProductType.adapter = spinnerAdapter
        loadProductData()
        btnThem.setOnClickListener {
            val ten = edtTen.text.toString()
            val gia = edtGia.text.toString().toDoubleOrNull()
            val chiTiet = edtChiTiet.text.toString()
            val selectedType = spProductType.selectedItem.toString()
            if (ten.isNotEmpty() && gia != null && selectedType.isNotEmpty()) {
                val newProduct = Products(tenSP = ten, gia = gia, chitiet = chiTiet ,type = selectedType)

                lifecycleScope.launch {

                    productDao.insertProduct(newProduct)


                    loadProductData()
                }

                edtTen.text.clear()
                edtGia.text.clear()
                edtChiTiet.text.clear()
                spProductType.setSelection(0)

                Toast.makeText(this, "Đã thêm sản phẩm: ${ten}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin và chọn loại sản phẩm", Toast.LENGTH_SHORT).show()
            }
        }



        btnLuu.setOnClickListener {
            val ten = edtTen.text.toString()
            val gia = edtGia.text.toString().toDoubleOrNull()
            val chiTiet = edtChiTiet.text.toString()
            val selectedType = spProductType.selectedItem.toString()

            if (ten.isNotEmpty() && gia != null && selectedType.isNotEmpty()) {

                val updatedProduct = Products(
                    tenSP = ten,
                    gia = gia,
                    chitiet = chiTiet,
                    type = selectedType
                )
                updatedProduct.id = currentProductId
                lifecycleScope.launch {
                    productDao.updateProduct(updatedProduct)
                    loadProductData()
                }
                edtTen.text.clear()
                edtGia.text.clear()
                edtChiTiet.text.clear()
                spProductType.setSelection(0)

                Toast.makeText(this, "Đã sửa sản phẩm: ${ten}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin và chọn loại sản phẩm", Toast.LENGTH_SHORT).show()
            }
        }
        btnHuy.setOnClickListener {
            val intent = Intent(this, main_menusp::class.java)
            startActivity(intent)
        }


        lvProductList.setOnItemClickListener { parent, view, position, id ->
            val product = productList[position]
            edtTen.setText(product.tenSP)
            edtGia.setText(product.gia.toString())
            edtChiTiet.setText(product.chitiet)
            val productTypeIndex = productTypeList.indexOf(product.type)
            spProductType.setSelection(productTypeIndex)
            currentProductId = product.id
        }

        lvProductList.setOnItemLongClickListener { parent, view, position, id ->
            val product = productList[position]
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Xác nhận xóa sản phẩm")
            builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
            builder.setPositiveButton("Xóa") { dialog, _ ->

                lifecycleScope.launch {
                    productDao.deleteProduct(product)

                    loadProductData()
                }

                Toast.makeText(this, "Đã xóa sản phẩm: ${product.tenSP}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            builder.setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
            true
        }
        btnTrangChu.setOnClickListener{
            val intent = Intent(this,MainTrangChu::class.java)
            startActivity(intent)
        }
    }

    private fun setControl() {
        db = ProductsDatabase.getInstance(applicationContext)
        productDao = db.getProductDao()
        edtTen = findViewById(R.id.edtTen)
        edtGia = findViewById(R.id.edtGia)
        edtChiTiet = findViewById(R.id.edtChiTiet)
        btnThem = findViewById(R.id.btnThem)
        btnLuu = findViewById(R.id.btnLuu)
        btnHuy = findViewById(R.id.btn_huy)
        lvProductList = findViewById(R.id.lvProductList)
        btnTrangChu = findViewById(R.id.btn_TrangChu)
        spProductType = findViewById(R.id.spProductType)

    }
}
