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
}