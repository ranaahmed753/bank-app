package edu.notes.bankapp.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import edu.notes.bankapp.entity.BankEntity

@Dao
interface BankDao {

    @Query("SELECT * FROM bank_account")
    fun getAllBankAccount(): LiveData<List<BankEntity>>

    @Insert
    fun createBankAccount(bank: BankEntity)

    @Query("UPDATE bank_account set bankName=:bankName,branchName=:branchName,routingNumber=:routingNumber WHERE id=:id")
    fun updateBankAccount(id:Int,bankName:String,branchName:String,routingNumber:String)

    @Query("DELETE FROM bank_account where id=:id")
    fun deleteBankAccount(id: Int)



}