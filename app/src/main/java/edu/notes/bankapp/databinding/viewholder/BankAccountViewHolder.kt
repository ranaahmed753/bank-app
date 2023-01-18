package edu.notes.bankapp.databinding.viewholder

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.notes.bankapp.R
import edu.notes.bankapp.databinding.ChildItemBinding
import edu.notes.bankapp.databinding.DeleteAccountPopupBinding
import edu.notes.bankapp.databinding.UpdateAccountPopupBinding
import edu.notes.bankapp.entity.BankEntity
import edu.notes.bankapp.utility.toast
import edu.notes.bankapp.view.MainActivity
import edu.notes.bankapp.viewmodel.BankViewModel

class BankAccountViewHolder(val itemBinding:ChildItemBinding,private val context: Context):RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(bank:BankEntity){
        itemBinding.bankName.text=bank.bankName
        itemBinding.branchName.text=bank.branchName
        itemBinding.routingNumber.text=bank.routingNumber
    }

    fun deleteBankAccount(id:Int){
        val bankViewModel=ViewModelProvider(context as MainActivity)[BankViewModel::class.java]
        var dialog=Dialog(context)
        val dialogBinding=DeleteAccountPopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
        dialogBinding.yesButton.setOnClickListener {
            bankViewModel.deleteBankAccount(id)
            dialog.cancel()
            toast(context,"Account deleted successfully")
        }

        dialogBinding.noButton.setOnClickListener {
            dialog.cancel()
        }

    }

    fun updateBankAccount(id: Int){
        val bankViewModel=ViewModelProvider(context as MainActivity)[BankViewModel::class.java]
        var dialog=Dialog(context)
        val dialogBinding=UpdateAccountPopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
        dialogBinding.updateButton.setOnClickListener {
            if(TextUtils.isEmpty(dialogBinding.bankNameEdittext.text.toString()) || TextUtils.isEmpty(dialogBinding.branchNameEdittext.text.toString()) || TextUtils.isEmpty(dialogBinding.routingNumberEdittext.text.toString())){
                toast(context,"empty value can't be updated!!")
            }else{
                bankViewModel.updateBankAccount(
                    id
                    ,dialogBinding.bankNameEdittext.text.toString()
                    ,dialogBinding.branchNameEdittext.text.toString()
                    ,dialogBinding.routingNumberEdittext.text.toString()
                )
                dialog.cancel()
                toast(context,"Account updated successfully")
            }

        }


    }


}