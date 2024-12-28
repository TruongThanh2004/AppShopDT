package com.example.doan.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Products::class], version = 2)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao

    companion object {
        @Volatile
        private var instance: ProductsDatabase? = null
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE products ADD COLUMN chitiet TEXT")
            }
        }
        fun getInstance(context: Context): ProductsDatabase {
            return instance ?: synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductsDatabase::class.java,
                    "product_database"
                )
                    .addMigrations(MIGRATION_1_2) // Đảm bảo migration
                    .build()
                instance = tempInstance
                tempInstance
            }
        }
    }
}
