package edu.notes.bankapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_account")
data class BankEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "bankName")
    val bankName:String,
    @ColumnInfo(name = "branchName")
    val branchName:String,
    @ColumnInfo(name = "routingNumber")
    val routingNumber:String
)
