package edu.notes.bankapp.databinding.adapter

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import edu.notes.bankapp.databinding.ChildItemBinding
import edu.notes.bankapp.databinding.viewholder.BankAccountViewHolder
import edu.notes.bankapp.entity.BankEntity
import edu.notes.bankapp.utility.fadeInAnim

class BankAccountAdapter(private val context: Context,private var bankList:List<BankEntity>):RecyclerView.Adapter<BankAccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankAccountViewHolder {
        val binding=ChildItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return BankAccountViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: BankAccountViewHolder, position: Int) {
        holder.bind(bankList[position])
        holder.itemBinding.menuIcon.setOnClickListener {
            fadeInAnim(context,holder.itemBinding.menuIcon)
            holder.popupMenus(it,bankList[position].id,bankList[position])
        }


    }

    override fun getItemCount(): Int {
        return bankList.size
    }



}