package com.example.doan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CartDAO {
    @Query("SELECT * FROM cart_table")
    suspend fun getAllCartItems(): List<Carts>

    @Query("DELETE FROM cart_table WHERE id = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: Int)

    @Query("DELETE FROM cart_table WHERE tenSP = :productName AND gia = :productPrice")
    fun deleteCart(productName: String, productPrice: Double): Int


    @Query("DELETE FROM cart_table WHERE tenSP = :tenSP AND gia = :gia")
    fun deleteProductByNameAndPrice(tenSP: String, gia: Double): Int


    @Query("SELECT * FROM cart_table WHERE product_id = :productId ")
    suspend fun getCartById(productId: Int): Carts?

    @Query("UPDATE cart_table SET soLuong = soLuong + 1 WHERE product_id = :productId")
    suspend fun updateSoLuong(productId: Int)

    @Query("UPDATE cart_table SET soLuong = :newQuantity WHERE tenSP = :tenSP AND gia = :gia")
    fun updateQuantity(tenSP: String, gia: Double, newQuantity: Int): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCart(cartItem: Carts)

}