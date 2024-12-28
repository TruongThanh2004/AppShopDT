package com.example.doan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class PhoneApdater (context: Context, private val productList: MutableList<Phone>) :
ArrayAdapter<Phone>(context, 0, productList) {

    private var totalPrice: Double = 0.0
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_phone_thong_tin, parent, false)
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
        holder.tvSoLuong.text = "Số Lượng : ${product.soLuong.toString()}"
        return view
    }
    class ViewHolder(view: View) {
        val tvTen: TextView = view.findViewById(R.id.tvTenSP)
        val tvGia: TextView = view.findViewById(R.id.tvGia)
        val ivHinhAnh: ImageView = view.findViewById(R.id.ivImage)
        val tvSoLuong: TextView = view.findViewById(R.id.tvSoLuong)


    }
}