package com.example.shymko_hw_lesson11_231220_android2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import com.example.shymko_hw_lesson11_231220_android2.databinding.ActivityMainBinding

class MyBroadcastButton:BroadcastReceiver() {

    companion object {
        const val MYBROADCASTBUTTON_ACTION = "MYBROADCASTBUTTON_ACTION"
        const val MYBROADCASTBUTTON_MESSAGE = "MYBROADCASTBUTTON_MESSAGE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, intent.getStringExtra(MYBROADCASTBUTTON_MESSAGE),Toast.LENGTH_SHORT).show()
    }
}
