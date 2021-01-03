package com.example.shymko_hw_lesson11_231220_android2

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.shymko_hw_lesson11_231220_android2.databinding.ActivityBroadcastreceiverBinding
import com.example.shymko_hw_lesson11_231220_android2.databinding.ActivityMainBinding

class BroadcastActivity:AppCompatActivity() {
    var Tag = "MyBroadcastActivity"
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, BroadcastActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupListenersForButton()
        registerReceiver(MyBroadcastButton(),IntentFilter(MyBroadcastButton.MYBROADCASTBUTTON_ACTION))

    }



    private lateinit var binding: ActivityBroadcastreceiverBinding

    private fun setupBinding() {
        binding = ActivityBroadcastreceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupListenersForButton() {
        binding.broadcastReceiveButtonCustom.setOnClickListener {
            val intent = Intent()
            intent.putExtra(MyBroadcastButton.MYBROADCASTBUTTON_MESSAGE, "Custom massage")
            intent.action = MyBroadcastButton.MYBROADCASTBUTTON_ACTION
            sendBroadcast(intent)
            Log.d(Tag,"- user tap on button")
        }
    }


}