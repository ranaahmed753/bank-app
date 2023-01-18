package edu.notes.bankapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.notes.bankapp.databinding.FragmentHomeBinding
import edu.notes.bankapp.databinding.adapter.BankAccountAdapter
import edu.notes.bankapp.entity.BankEntity
import edu.notes.bankapp.utility.toast
import edu.notes.bankapp.view.MainActivity
import edu.notes.bankapp.viewmodel.BankViewModel


class HomeFragment : Fragment() {
private lateinit var binding:FragmentHomeBinding
private lateinit var bankAccountAdapter:BankAccountAdapter
private var bankViewModel:BankViewModel?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bankViewModel=ViewModelProvider(requireActivity())[BankViewModel::class.java]
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        bankViewModel!!.getAllBankLiveData().observe(requireActivity(), Observer {allBanks->
            bankAccountAdapter= BankAccountAdapter(requireActivity(),allBanks)
            binding.recyclerView.adapter=bankAccountAdapter
            bankAccountAdapter.notifyDataSetChanged()
            if(allBanks.size==0){
                toast(requireActivity(),"No accound found!")
            }
        })

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createBankAccountFragment)
        }

    }


}