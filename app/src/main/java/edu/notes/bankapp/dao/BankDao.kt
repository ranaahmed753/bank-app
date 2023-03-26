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

    //@Query("SELECT * FROM bank_account where bankName=:searchText")
    @Query("SELECT * FROM bank_account WHERE bankName LIKE :searchText OR branchName LIKE :searchText OR routingNumber LIKE :searchText")
    fun searchBank(searchText:String):LiveData<List<BankEntity>>

    @Query("UPDATE bank_account set color=:color WHERE id=:id")
    fun updateColor(color: String,id: Int)

    @Query("UPDATE bank_account set font=:font WHERE id=:id")
    fun updateFontFamily(font: Int,id: Int)

    @Query("UPDATE bank_account set fontColor=:fontColor WHERE id=:id")
    fun updateFontColor(fontColor: String,id: Int)

    @Query("UPDATE bank_account set likeStatus=:likeStatus WHERE id=:id")
    fun toggleLike(likeStatus: String,id: Int)



}