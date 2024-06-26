package com.example.best;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "notifications")
data class Notifications (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "value")
    var value: Double,
    @ColumnInfo(name = "date")
    var date: Long,
)