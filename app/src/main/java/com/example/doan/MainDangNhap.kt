package com.example.doan

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.doan.database.UserDAO
import com.example.doan.database.UsersDatabase
import kotlinx.coroutines.launch

class MainDangNhap : AppCompatActivity() {

    private lateinit var db:UsersDatabase
    private lateinit var userDao:UserDAO

    private lateinit var edt_email:EditText
    private lateinit var edt_password:EditText
    private lateinit var btn_login:Button
    private lateinit var tv_dangki:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_dang_nhap)

        db = UsersDatabase.DatabaseInstance.getDatabase(applicationContext)
        userDao = db.getUserDao()
        lifecycleScope.launch {
            val userList = userDao.getAllUser()
            Log.e("CheckUser","${userList}")
        }
        setControl()
        setEvent()

    }

    private fun setEvent() {
        btn_login.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            var checkRoleUser = false
            var checkRoleAdmin = false

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val userList = userDao.getAllUser()
                    for (user in userList) {
                        if (user.email == email && user.password == password) {
                            if (user.role == 1) {
                                checkRoleUser = true
                            } else if (user.role == 0) {
                                checkRoleAdmin = true
                            }
                            break
                        }
                    }

                    if (checkRoleUser) {
                        Toast.makeText(this@MainDangNhap, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainDangNhap, MainTrangChu::class.java)
                        startActivity(intent)
                        finish()
                    } else if (checkRoleAdmin) {
                        Toast.makeText(this@MainDangNhap, "Đăng nhập thành công ADMIN!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainDangNhap, main_menusp::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@MainDangNhap, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        tv_dangki.setOnClickListener {
            val intent = Intent(this, Dangki::class.java)
            startActivity(intent)
        }
    }

    private fun setControl() {
         edt_email = findViewById(R.id.edt_email)
         edt_password = findViewById(R.id.edt_password)
         btn_login = findViewById(R.id.btn_login)
         tv_dangki = findViewById(R.id.tv_dangki)
    }
}
