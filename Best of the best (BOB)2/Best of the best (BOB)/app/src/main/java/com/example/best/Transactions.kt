package com.example.best

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Trans")
data class Transactions (
    @PrimaryKey@ColumnInfo(name ="date")
    var date: Long?=null,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "sum")
    var sum: Double,
    @ColumnInfo(name = "type")
    var type: String?,
    @ColumnInfo(name = "comm")
    var comm: String?,
)