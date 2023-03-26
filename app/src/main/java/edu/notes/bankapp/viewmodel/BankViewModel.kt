package edu.notes.bankapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.notes.bankapp.entity.BankEntity
import edu.notes.bankapp.model.BankRepository
import javax.inject.Inject

@HiltViewModel
class BankViewModel @Inject constructor(
    private val bankRepository: BankRepository
):ViewModel() {
     var bankData:LiveData<List<BankEntity>>?=null
     init {
         bankData=bankRepository.getAllBankAccount()
     }

    fun getAllBankLiveData():LiveData<List<BankEntity>>{
        return bankData!!
    }

    fun createBankAccount(bank: BankEntity){
        bankRepository.createBankAccount(bank)
    }

    fun updateBankAccount(id:Int,bankName:String,branchName:String,routingNumber:String){
        bankRepository.updateBankAccount(id,bankName,branchName,routingNumber)
    }

    fun deleteBankAccount(id:Int){
        bankRepository.deleteBankAccount(id)
    }

    fun searchBank(searchText: String):LiveData<List<BankEntity>>{
        return bankRepository.searchBank(searchText)
    }

    fun updateColor(color: String,id: Int){
        bankRepository.updateColor(color,id)
    }

    fun updateFontFamily(font: Int,id: Int){
        bankRepository.updateFontFamily(font,id)
    }

    fun updateFontColor(fontColor: String,id: Int){
        bankRepository.updateFontColor(fontColor,id)
    }

    fun toggleLike(likeStatus: String,id: Int){
        bankRepository.toggleLike(likeStatus,id)
    }
}