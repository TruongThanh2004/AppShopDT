package com.example.doan

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.doan.database.CartsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyApdater(context: Context, private val productList: MutableList<Phone>, private val btnMuaHang: View) :
    ArrayAdapter<Phone>(context, 0, productList) {

    private var totalPrice: Double = 0.0
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_phone, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        val product = productList[position]
        holder.tvTen.text = product.tenSP
        holder.tvGia.text = product.gia.toString()+"đ"
        holder.ivHinhAnh.setImageResource(product.imageID)
        holder.tvSoLuong.text = product.soLuong.toString()
        holder.btnTang.setOnClickListener {
            product.soLuong++
            holder.tvSoLuong.text = product.soLuong.toString()
            notifyDataSetChanged()
            updateTotalPrice()
            GlobalScope.launch(Dispatchers.IO) {
                val dbHelper = CartsDatabase.getDatabase(context)
                dbHelper.cartDao().updateQuantity(product.tenSP, product.gia, product.soLuong)
            }
        }
        holder.btnGiam.setOnClickListener {
            if (product.soLuong > 1) {
                product.soLuong--
                holder.tvSoLuong.text = product.soLuong.toString()
                notifyDataSetChanged()
                updateTotalPrice()
                GlobalScope.launch(Dispatchers.IO) {
                    val dbHelper = CartsDatabase.getDatabase(context)
                    dbHelper.cartDao().updateQuantity(product.tenSP, product.gia, product.soLuong)
                }
            }
        }
        holder.btnXoa.setOnClickListener {
            val product = productList[position]
            GlobalScope.launch(Dispatchers.IO) {
                val dbHelper = CartsDatabase.getDatabase(context)
                val isDeleted = dbHelper.cartDao().deleteCart(product.tenSP, product.gia)
                withContext(Dispatchers.Main) {
                    if (isDeleted > 0) {
                        productList.removeAt(position)
                        notifyDataSetChanged()
                        updateTotalPrice()
                    } else {
                        Toast.makeText(context, "Xóa sản phẩm không thành công!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        holder.cbChon.isChecked = product.isSelected

        holder.cbChon.setOnCheckedChangeListener { _, isChecked ->
            product.isSelected = isChecked
            updateTotalPrice()
            btnMuaHang.isEnabled=true
        }
        return view
    }
    private fun updateTotalPrice() {
        totalPrice = productList.filter { it.isSelected }.sumOf { it.soLuong * it.gia }

        (context as Activity).findViewById<TextView>(R.id.tvTongGia).text = "$totalPrice đ"
    }


    fun getTotalPrice(): Double {
        return totalPrice
    }






    class ViewHolder(view: View) {
        val tvTen: TextView = view.findViewById(R.id.tvTenSP)
        val tvGia: TextView = view.findViewById(R.id.tvGia)
        val ivHinhAnh: ImageView = view.findViewById(R.id.ivImage)
        val btnTang: TextView = view.findViewById(R.id.btnTang)
        val btnGiam: TextView = view.findViewById(R.id.btnGiam)
        val tvSoLuong: TextView = view.findViewById(R.id.tvSoLuong)
        val btnXoa: ImageView = view.findViewById(R.id.btnXoa)
        val cbChon: CheckBox = view.findViewById(R.id.cbChon)
    }


}
