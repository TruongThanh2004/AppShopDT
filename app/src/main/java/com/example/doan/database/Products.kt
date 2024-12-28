package com.example.doan.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "product_table")
data class Products(
    @ColumnInfo(name = "tenSP") var tenSP: String = "",
    @ColumnInfo(name = "gia") var gia: Double = 0.0,
    @ColumnInfo(name="chitiet") var chitiet:String = "",
    @ColumnInfo(name = "type") var type: String = ""
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

