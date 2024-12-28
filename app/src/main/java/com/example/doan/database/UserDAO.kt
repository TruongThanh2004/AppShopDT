package com.example.doan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert
    suspend fun addUser(user: Users)

    @Query("SELECT * FROM user_table ")
    suspend fun getAllUser(): List<Users>


    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): Users?

}