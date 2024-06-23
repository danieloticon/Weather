package com.example.weather

import android.content.Context
import android.widget.Toast

class toast() {
    fun showtoast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}