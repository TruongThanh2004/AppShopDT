package com.example.doan


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity


class MainThongTinDonHang : AppCompatActivity() {

    private lateinit var lvProductList: ListView

    private lateinit var productApdapter: PhoneApdater
    private lateinit var btnThongTinDonHang:Button
    private lateinit var btnBack:Button
    private lateinit var tvTongGia:TextView
    private lateinit var edtName:EditText
    private lateinit var edtSDT:EditText
    private lateinit var edtEmail:EditText
    private lateinit var edtDiaChi:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_thong_tin_don_hang)



        setControl()
        setEvent()

    }
    private fun setControl() {
        btnThongTinDonHang = findViewById(R.id.btnThongTinDonHang)
        btnBack = findViewById(R.id.btnBack)
        tvTongGia = findViewById(R.id.tvTongGia)
        lvProductList = findViewById(R.id.lvDS)
        edtName = findViewById(R.id.edtName)
        edtSDT = findViewById(R.id.edtSDT)
        edtEmail = findViewById(R.id.edtEmail)
        edtDiaChi = findViewById(R.id.edtDiaChi)

    }
    private fun setEvent() {


        val tongTien = intent.getDoubleExtra("tongGiaTien", 0.0)

        val selectedProducts = intent.getParcelableArrayListExtra <Phone>("dsDaChon")
        val selectedProducts1 = intent.getParcelableArrayListExtra <Phone>("dsDaChon1")
        Log.e("CheckProducts","${selectedProducts}")
        Log.e("CheckProducts1","${selectedProducts1}")
        selectedProducts?.let {

            productApdapter = PhoneApdater(this, it)
            lvProductList.adapter = productApdapter
        }




        tvTongGia.text = "${tongTien} đ"




        btnThongTinDonHang.setOnClickListener{

            var check = true

            if (edtName.text.isEmpty()){
                edtName.error = "Vui lòng nhập tên"
                check = false
            }

            if (edtSDT.text.isEmpty()){
                edtSDT.error = "Vui lòng nhập số điện thoại"
                check = false
            }

            if (edtSDT.text.length!=10){
                edtSDT.error = "Số điện thoại hong hợp lệ"
                check = false
            }
            if (edtEmail.text.isEmpty()){
                edtEmail.error = "Vui lòng nhập email"
                check = false
            }
            if (edtDiaChi.text.isEmpty()){
                edtDiaChi.error = "Vui lòng nhập địa chỉ"
                check = false
            }






            if (check==true){
                val thongTinNguoiDat = "Nguời đặt:${edtName.text}\nSố điện thoại:${edtSDT.text}\nEmail:${edtEmail.text}\n" +
                        "Địa chỉ:${edtDiaChi.text}"


                val intent = Intent(this,MainThanhToan::class.java)


                intent.putExtra("tongGiaTien", tongTien)
                intent.putExtra("thongTin",thongTinNguoiDat)
                intent.putParcelableArrayListExtra("dsDaChon", selectedProducts)

                startActivity(intent)
            }






        }
        btnBack.setOnClickListener{
            finish()
        }
    }


}