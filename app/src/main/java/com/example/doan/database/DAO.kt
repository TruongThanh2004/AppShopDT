package com.example.doan.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {


    @Insert
    suspend fun insertProduct(product: Products)

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts(): List<Products>

    @Query("SELECT * FROM product_table WHERE type = :type")
    suspend fun getProductsType(type: String): List<Products>

    @Query("SELECT * FROM product_table WHERE id = :productId ")
    suspend fun getProductById(productId: Int): Products?


    @Update
    suspend fun updateProduct(product: Products)
    @Delete
    suspend fun deleteProduct(product: Products)
}
