package edu.notes.bankapp.utility

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import edu.notes.bankapp.HomeFragment

fun toast(context: Context, message:String){
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}

fun fadeInAnim(context:Context,widget: View){
    widget.startAnimation(AnimationUtils.loadAnimation(context,android.R.anim.fade_in))
}