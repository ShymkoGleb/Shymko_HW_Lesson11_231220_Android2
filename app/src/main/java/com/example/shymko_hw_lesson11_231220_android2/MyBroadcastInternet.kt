package com.example.shymko_hw_lesson11_231220_android2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast


class MyBroadcastInternet : BroadcastReceiver() {

    companion object {
        const val MYBROADCASTCONNECTION_ACTION = "MYBROADCASTCONNECTION_ACTION"
        const val MYBROADCASTCONNECTION_MESSAGE = "MYBROADCASTCONNECTION_MESSAGE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, intent.getStringExtra(MYBROADCASTCONNECTION_MESSAGE), Toast.LENGTH_SHORT).show()
    }
}




