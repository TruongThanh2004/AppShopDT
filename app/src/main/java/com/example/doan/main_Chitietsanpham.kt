package com.example.doan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doan.database.Products



class main_Chitietsanpham(context: Context, private val productList: MutableList<Products>) :

    ArrayAdapter<Products>(context, 0, productList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_main_chitietsanpham, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        val product = productList[position]
        holder.tvTen.text = product.tenSP
        holder.tvGia.text = product.gia.toString()
        holder.ivHinhAnh.setImageResource(R.drawable.dt)
        return view
    }
    private class ViewHolder(view: View) {

        val tvTen: TextView = view.findViewById(R.id.tvTen)

        val tvGia: TextView = view.findViewById(R.id.tvGia)

        val ivHinhAnh: ImageView = view.findViewById(R.id.ivHinhAnh)


    }

}