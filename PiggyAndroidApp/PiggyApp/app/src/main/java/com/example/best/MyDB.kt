package com.example.best

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [User::class, Category::class, Statistics::class,
    Transactions::class, Notifications::class], version = 5)
abstract class Db : RoomDatabase() {
    abstract fun getMyDao(): MyDao

    companion object{
        fun getMyDb(context: Context): Db{
            return Room.databaseBuilder(
                context.applicationContext,
                Db::class.java,
                "test.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}