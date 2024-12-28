package com.example.doan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.doan.database.UserDAO
import com.example.doan.database.Users
import com.example.doan.database.UsersDatabase
import kotlinx.coroutines.launch

class Dangki : AppCompatActivity() {

    private lateinit var db:UsersDatabase
    private lateinit var dbDao:UserDAO


    private lateinit var edt_email:EditText
    private lateinit var edt_password:EditText
    private lateinit var btn_register:Button
    private lateinit var tvback:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dangki)

        db = UsersDatabase.DatabaseInstance.getDatabase(applicationContext)
        dbDao = db.getUserDao()

        setControl()
        setEvent()

    }

    private fun setEvent() {
        btn_register.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val existingUser = dbDao.getUserByEmail(email)
                    if (existingUser != null) {
                        Toast.makeText(this@Dangki, "Email đã tồn tại!", Toast.LENGTH_SHORT).show()
                    } else {
                        val user = Users(email = email, password = password)
                        dbDao.addUser(user)
                        Toast.makeText(this@Dangki, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Dangki, MainDangNhap::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }
        tvback.setOnClickListener(){
            val intent = Intent(this, MainDangNhap::class.java)
            startActivity(intent)
        }
    }

    private fun setControl() {
         edt_email = findViewById(R.id.edt_email)
         edt_password = findViewById(R.id.edt_password)
         btn_register = findViewById(R.id.btn_register)
         tvback = findViewById(R.id.tvback)
    }
}

