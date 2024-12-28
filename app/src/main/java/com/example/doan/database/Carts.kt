
package com.example.doan.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cart_table")
data class Carts(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "tenSP") val tenSP: String,
    @ColumnInfo(name = "soLuong") val soLuong: Int,
    @ColumnInfo(name = "gia") val gia: Double
) : Serializable
