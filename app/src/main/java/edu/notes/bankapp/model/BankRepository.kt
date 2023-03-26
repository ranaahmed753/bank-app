package edu.notes.bankapp.model

import androidx.lifecycle.LiveData
import edu.notes.bankapp.dao.BankDao
import edu.notes.bankapp.entity.BankEntity
import javax.inject.Inject

class BankRepository @Inject constructor(
    private val bankDao: BankDao
) {
    fun getAllBankAccount():LiveData<List<BankEntity>>{
        return bankDao.getAllBankAccount()
    }
    fun createBankAccount(bank: BankEntity){
        bankDao.createBankAccount(bank)
    }

    fun updateBankAccount(id:Int,bankName:String,branchName:String,routingNumber:String){
        bankDao.updateBankAccount(id,bankName,branchName,routingNumber)
    }

    fun deleteBankAccount(id:Int){
        bankDao.deleteBankAccount(id)
    }

    fun searchBank(searchText: String):LiveData<List<BankEntity>>{
        return bankDao.searchBank(searchText)
    }

    fun updateColor(color: String,id: Int){
        bankDao.updateColor(color,id)
    }

    fun updateFontFamily(font: Int,id: Int){
        bankDao.updateFontFamily(font,id)
    }

    fun updateFontColor(fontColor: String,id: Int){
        bankDao.updateFontColor(fontColor,id)
    }

    fun toggleLike(likeStatus: String,id: Int){
        bankDao.toggleLike(likeStatus,id)
    }
}