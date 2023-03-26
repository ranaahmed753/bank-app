package edu.notes.bankapp.databinding.viewholder

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.notes.bankapp.R
import edu.notes.bankapp.databinding.*
import edu.notes.bankapp.databinding.adapter.BankAccountAdapter
import edu.notes.bankapp.entity.BankEntity
import edu.notes.bankapp.utility.*
import edu.notes.bankapp.view.MainActivity
import edu.notes.bankapp.viewmodel.BankViewModel
import top.defaults.drawabletoolbox.DrawableBuilder
import java.text.FieldPosition
import javax.inject.Inject

class BankAccountViewHolder(val itemBinding:ChildItemBinding,private val context: Context):RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(bank:BankEntity){
        val bankViewModel=ViewModelProvider(context as MainActivity)[BankViewModel::class.java]
        itemBinding.bankName.text=bank.bankName
        itemBinding.branchName.text=bank.branchName
        itemBinding.routingNumber.maxLines=6
        itemBinding.routingNumber.text=bank.routingNumber
        itemBinding.date.text=bank.date

        itemBinding.routingNumber.post {
            if (itemBinding.routingNumber.lineCount > itemBinding.routingNumber.maxLines) {
                val lineEndIndex = itemBinding.routingNumber.layout.getLineEnd(itemBinding.routingNumber.maxLines - 1)
                val text = itemBinding.routingNumber.text.subSequence(0, lineEndIndex - 3).toString() + "..."

                val spannableStringBuilder = SpannableStringBuilder(text)
                spannableStringBuilder.setSpan(
                    ForegroundColorSpan(itemBinding.routingNumber.currentTextColor),
                    spannableStringBuilder.length - 3,
                    spannableStringBuilder.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                itemBinding.routingNumber.text = spannableStringBuilder
            }
        }

        itemBinding.likeIcon.setOnClickListener {
            fadeInAnim(context,itemBinding.likeIcon)
            if(bank.likeStatus=="like"){
                bankViewModel.toggleLike("liked",bank.id)
            }else{
                bankViewModel.toggleLike("like",bank.id)
            }
        }
        if(bank.likeStatus=="like"){
            itemBinding.likeIcon.setImageResource(R.drawable.ic_favorite_outlined)
        }else{
            itemBinding.likeIcon.setImageResource(R.drawable.ic_favorite_filled)
        }

        if(bank.color=="default"){
            changeItemBackgroundColor("#fcfcfcfc",itemBinding.constraintLayout)
        }else{
            changeItemBackgroundColor(bank.color,itemBinding.constraintLayout)
        }

        if (bank.font==R.font.poppins_regular){
            val typeFace= ResourcesCompat.getFont(context,R.font.poppins_regular)
            itemBinding.bankName.typeface=typeFace
            itemBinding.branchName.typeface=typeFace
            itemBinding.routingNumber.typeface=typeFace
            itemBinding.date.typeface=typeFace
        }else{
            val typeFace= ResourcesCompat.getFont(context,bank.font)
            itemBinding.bankName.typeface=typeFace
            itemBinding.branchName.typeface=typeFace
            itemBinding.routingNumber.typeface=typeFace
            itemBinding.date.typeface=typeFace
        }

        if (bank.fontColor=="default"){
            itemBinding.bankName.setTextColor(Color.parseColor(Colors.BLACK))
            itemBinding.branchName.setTextColor(Color.parseColor(Colors.BLACK))
            itemBinding.routingNumber.setTextColor(Color.parseColor(Colors.BLACK))
            itemBinding.date.setTextColor(Color.parseColor(Colors.BLACK))
        }else{
            itemBinding.bankName.setTextColor(Color.parseColor(bank.fontColor))
            itemBinding.branchName.setTextColor(Color.parseColor(bank.fontColor))
            itemBinding.routingNumber.setTextColor(Color.parseColor(bank.fontColor))
            itemBinding.date.setTextColor(Color.parseColor(bank.fontColor))
        }
    }

    fun popupMenus(view: View,id:Int,bank: BankEntity){
        val popupMenus=PopupMenu(context,view)
        popupMenus.inflate(R.menu.recyclerview_card_menu)
        popupMenus.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.edit->{
                    updateBankAccount(id,bank)
                    true
                }
                R.id.delete-> {
                    deleteBankAccount(id)
                    true
                }
                R.id.theme-> {
                    themePopup(id)
                    true
                }
                R.id.font-> {
                    fontFamilyPopup(id)
                    true
                }
                R.id.fontColor->{
                    fontColorPopup(id)
                    true
                }
                else -> true
            }
        }
        popupMenus.show()
        val popup=PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible=true
        val menu=popup.get(popupMenus)
        menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java).invoke(menu,true)
    }

    private fun deleteBankAccount(id:Int){
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
            toast(context,"Note deleted successfully")
        }

        dialogBinding.noButton.setOnClickListener {
            dialog.cancel()
        }

    }

    private fun updateBankAccount(id: Int,bank: BankEntity){
        val bankViewModel=ViewModelProvider(context as MainActivity)[BankViewModel::class.java]
        var dialog=Dialog(context)
        val dialogBinding=UpdateAccountPopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogBinding.bankNameEdittext.hint=bank.bankName
        dialogBinding.branchNameEdittext.hint=bank.branchName
        dialogBinding.routingNumberEdittext.hint=bank.routingNumber
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
                toast(context,"Note updated successfully")
            }

        }


    }

    private fun changeItemBackgroundColor(color: String,view: View){
        val drawable = DrawableBuilder()
            .rectangle()
            .solidColor(Color.parseColor(color))
            .topLeftRadius(5)
            .topRightRadius(5)
            .bottomLeftRadius(5)
            .bottomRightRadius(5)
            .build()
        view.background = drawable
    }

    private fun themePopup(id: Int){
        val bankViewModel=ViewModelProvider(context as MainActivity)[BankViewModel::class.java]
        var dialog=Dialog(context)
        val dialogBinding=ThemePopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
        dialogBinding.purpleBack.setOnClickListener {
            fadeInAnim(context,dialogBinding.purpleBack)
            bankViewModel.updateColor(Background.PURPLE,id)
            dialog.cancel()
        }

        dialogBinding.violetBack.setOnClickListener {
            fadeInAnim(context,dialogBinding.violetBack)
            bankViewModel.updateColor(Background.VIOLET,id)
            dialog.cancel()
        }

        dialogBinding.beigeBack.setOnClickListener {
            fadeInAnim(context,dialogBinding.beigeBack)
            bankViewModel.updateColor(Background.BEIGE,id)
            dialog.cancel()
        }

        dialogBinding.greenBack.setOnClickListener {
            fadeInAnim(context,dialogBinding.greenBack)
            bankViewModel.updateColor(Background.GREEN,id)
            dialog.cancel()
        }

        dialogBinding.lilacBack.setOnClickListener {
            fadeInAnim(context,dialogBinding.lilacBack)
            bankViewModel.updateColor(Background.LILAC,id)
            dialog.cancel()
        }

        dialogBinding.ltBlueBack.setOnClickListener {
            fadeInAnim(context,dialogBinding.ltBlueBack)
            bankViewModel.updateColor(Background.LIGHT_BLUE,id)
            dialog.cancel()
        }

        dialogBinding.yellowBack.setOnClickListener {
            fadeInAnim(context,dialogBinding.yellowBack)
            bankViewModel.updateColor(Background.YELLOW,id)
            dialog.cancel()
        }
    }

    private fun fontFamilyPopup(id: Int){
        val bankViewModel=ViewModelProvider(context as MainActivity)[BankViewModel::class.java]
        var dialog=Dialog(context)
        val dialogBinding=FontFamilyPopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
        dialogBinding.bebaasneue.setOnClickListener {
            fadeInAnim(context,dialogBinding.bebaasneue)
            bankViewModel.updateFontFamily(Fonts.BEBASNEUE_REGULAR,id)
            dialog.cancel()
        }
        dialogBinding.kanit.setOnClickListener {
            fadeInAnim(context,dialogBinding.kanit)
            bankViewModel.updateFontFamily(Fonts.KANIT_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.kavoon.setOnClickListener {
            fadeInAnim(context,dialogBinding.kavoon)
            bankViewModel.updateFontFamily(Fonts.KAVOON_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.lato.setOnClickListener {
            fadeInAnim(context,dialogBinding.lato)
            bankViewModel.updateFontFamily(Fonts.LATO_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.michroma.setOnClickListener {
            fadeInAnim(context,dialogBinding.michroma)
            bankViewModel.updateFontFamily(Fonts.MICHROMA_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.permanentMarker.setOnClickListener {
            fadeInAnim(context,dialogBinding.permanentMarker)
            bankViewModel.updateFontFamily(Fonts.PERMANENTMARKER_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.poppinsThin.setOnClickListener {
            fadeInAnim(context,dialogBinding.poppinsThin)
            bankViewModel.updateFontFamily(Fonts.POPPINS_THIN,id)
            dialog.cancel()
        }

        dialogBinding.poppinsRegular.setOnClickListener {
            fadeInAnim(context,dialogBinding.poppinsRegular)
            bankViewModel.updateFontFamily(Fonts.POPPINS_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.poppinsSemibold.setOnClickListener {
            fadeInAnim(context,dialogBinding.poppinsSemibold)
            bankViewModel.updateFontFamily(Fonts.POPPINS_SEMIBOLD,id)
            dialog.cancel()
        }

        dialogBinding.raleway.setOnClickListener {
            fadeInAnim(context,dialogBinding.raleway)
            bankViewModel.updateFontFamily(Fonts.RALEWAY,id)
            dialog.cancel()
        }

        dialogBinding.righteous.setOnClickListener {
            fadeInAnim(context,dialogBinding.righteous)
            bankViewModel.updateFontFamily(Fonts.RIGHTEOUS_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.roboto.setOnClickListener {
            fadeInAnim(context,dialogBinding.roboto)
            bankViewModel.updateFontFamily(Fonts.ROBOTO_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.lobster.setOnClickListener {
            fadeInAnim(context,dialogBinding.lobster)
            bankViewModel.updateFontFamily(Fonts.LOBSTER_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.lobsterTwoRegular.setOnClickListener {
            fadeInAnim(context,dialogBinding.lobsterTwoRegular)
            bankViewModel.updateFontFamily(Fonts.LOBSTER_TWO_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.lobsterTwoItalic.setOnClickListener {
            fadeInAnim(context,dialogBinding.lobsterTwoItalic)
            bankViewModel.updateFontFamily(Fonts.LOBSTER_TWO_ITALIC,id)
            dialog.cancel()
        }

        dialogBinding.openSans.setOnClickListener {
            fadeInAnim(context,dialogBinding.openSans)
            bankViewModel.updateFontFamily(Fonts.OPEN_SANS_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.nunitoRegular.setOnClickListener {
            fadeInAnim(context,dialogBinding.nunitoRegular)
            bankViewModel.updateFontFamily(Fonts.NUNITO_REGULAR,id)
            dialog.cancel()
        }

        dialogBinding.promptRegular.setOnClickListener {
            fadeInAnim(context,dialogBinding.promptRegular)
            bankViewModel.updateFontFamily(Fonts.PROMPT_REGULAR,id)
            dialog.cancel()
        }

    }

    private fun fontColorPopup(id: Int){
        val bankViewModel=ViewModelProvider(context as MainActivity)[BankViewModel::class.java]
        var dialog=Dialog(context)
        val dialogBinding=FontColorPopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.blackColor.setOnClickListener {
            fadeInAnim(context,dialogBinding.blackColor)
            bankViewModel.updateFontColor(Colors.BLACK,id)
            dialog.cancel()
        }

        dialogBinding.grayColor.setOnClickListener {
            fadeInAnim(context,dialogBinding.grayColor)
            bankViewModel.updateFontColor(Colors.GRAY,id)
            dialog.cancel()
        }

        dialogBinding.lightGrayColor.setOnClickListener {
            fadeInAnim(context,dialogBinding.lightGrayColor)
            bankViewModel.updateFontColor(Colors.LIGHT_GRAY,id)
            dialog.cancel()
        }

        dialogBinding.ebonyColor.setOnClickListener {
            fadeInAnim(context,dialogBinding.ebonyColor)
            bankViewModel.updateFontColor(Colors.EBONY,id)
            dialog.cancel()
        }

        dialogBinding.oxfordBlueColor.setOnClickListener {
            fadeInAnim(context,dialogBinding.oxfordBlueColor)
            bankViewModel.updateFontColor(Colors.OXFORD_BLUE,id)
            dialog.cancel()
        }

        dialogBinding.darkPurpleColor.setOnClickListener {
            fadeInAnim(context,dialogBinding.darkPurpleColor)
            bankViewModel.updateFontColor(Colors.DARK_PURPLE,id)
            dialog.cancel()
        }

        dialogBinding.blueBrightGrayColor.setOnClickListener {
            fadeInAnim(context,dialogBinding.blueBrightGrayColor)
            bankViewModel.updateFontColor(Colors.BLUE_BRIGHT_GRAY,id)
            dialog.cancel()
        }
    }


}