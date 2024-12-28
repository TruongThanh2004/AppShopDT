package com.example.doan

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class main_menusp : AppCompatActivity() {


    lateinit var toolbar: Toolbar
    private lateinit var drawLayout : DrawerLayout
    private lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setControl()
        setEvent()

    }

    private fun setEvent() {
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
        toolbar.setNavigationOnClickListener{
            drawLayout.openDrawer(GravityCompat.START)
        }
        // Xử lý sự kiện
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Xử lý sự kiện chọn Home
                    supportActionBar?.title = "Danh Sách Sản Phẩm"
                    Toast.makeText(this, "Danh Sách Sản Phẩm", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, main_Themsanpham::class.java)
                    startActivity(intent)

                }
                R.id.nav_settings -> {
                    // Xử lý sự kiện chọn setting
                    supportActionBar?.title = "Settings "
                    Toast.makeText(this, "Cài đặt", Toast.LENGTH_LONG).show()
                }
                R.id.nav_function -> {
                    // Xử lý sự kiện chọn báo cáo
                    supportActionBar?.title = "Báo Cáo Thống Kê"
                    Toast.makeText(this, "Truy Cập Báo Cáo Thống Kê", Toast.LENGTH_LONG).show()
                }

                R.id.nav_DangXuat -> {
                    // Xử lý sự kiện chọn báo cáo
                    supportActionBar?.title = "Đăng Xuất"
                    val intent = Intent(this, MainDangNhap::class.java)
                    startActivity(intent)
                }
            }
            // Đóng Drawer sau khi chọn mục
            drawLayout.closeDrawer(GravityCompat.START)
            true
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.nav_home -> {
                Toast.makeText(this,"Danh Sách Sản Phẩm", Toast.LENGTH_LONG).show()
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun setControl() {
        toolbar = findViewById(R.id.toolbar)
        drawLayout = findViewById(R.id.draw_layout)
        navigationView = findViewById(R.id.navigation_view)

    }
}