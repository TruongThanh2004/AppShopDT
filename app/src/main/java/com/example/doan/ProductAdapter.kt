package com.example.doan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.TextView
import com.example.doan.database.Products

class ProductAdapter(context: Context, private val productList: List<Products>) :
    ArrayAdapter<Products>(context, 0, productList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.iphone16_product, parent, false)
        val product = productList[position]
        val productName: TextView = view.findViewById(R.id.tvTenSP)
        val productPrice: TextView = view.findViewById(R.id.tvGia)
        productName.text = product.tenSP
        productPrice.text = product.gia.toString()+"đ"
        return view
    }


     
}
