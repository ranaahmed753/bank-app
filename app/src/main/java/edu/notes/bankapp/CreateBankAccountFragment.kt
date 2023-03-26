package edu.notes.bankapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dagger.hilt.android.AndroidEntryPoint
import edu.notes.bankapp.databinding.FragmentCreateBankAccountBinding
import edu.notes.bankapp.entity.BankEntity
import edu.notes.bankapp.utility.toast
import edu.notes.bankapp.viewmodel.BankViewModel
import java.text.SimpleDateFormat
import java.util.*


class CreateBankAccountFragment : Fragment() {
   private lateinit var binding:FragmentCreateBankAccountBinding
   lateinit var bankViewModel: BankViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bankViewModel=ViewModelProvider(requireActivity())[BankViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context=requireContext()
        binding= FragmentCreateBankAccountBinding.inflate(inflater,container,false)
        binding.createAccountButton.setOnClickListener {
            if(TextUtils.isEmpty(binding.bankName.text.toString()) || TextUtils.isEmpty(binding.branchName.text.toString()) || TextUtils.isEmpty(binding.routingNumber.text.toString())){
                toast(context,"Please fill the form!")
            }else{
                val currentDate=Calendar.getInstance().time
                val dateFormat=SimpleDateFormat("dd/MM/yyyy")
                val date=dateFormat.format(currentDate)
                bankViewModel.createBankAccount(BankEntity(
                    0
                    ,binding.bankName.text.toString()
                    ,binding.branchName.text.toString()
                    ,binding.routingNumber.text.toString()
                    ,"default"
                    ,R.font.poppins_regular
                    ,date
                    ,"default"
                    ,"like"
                ))
                toast(context,"Note Created Successfully")
                binding.bankName.text.clear()
                binding.branchName.text.clear()
                binding.routingNumber.text.clear()
            }

        }

        return binding.root
    }


}