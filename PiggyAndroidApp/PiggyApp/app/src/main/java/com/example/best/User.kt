package com.example.best

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "deposit")
    var deposit: Double,
    @ColumnInfo(name = "month")
    var month: Int,
    @ColumnInfo(name = "year")
    var year: Int,

)