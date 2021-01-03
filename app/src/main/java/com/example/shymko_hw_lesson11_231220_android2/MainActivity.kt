@file:Suppress("DEPRECATION")

package com.example.shymko_hw_lesson11_231220_android2


import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.shymko_hw_lesson11_231220_android2.databinding.ActivityMainBinding

/*
1 -  Створити додаток, у якому буде декілька кнопок, які будуть показувати різні типи оповіщень
●     Просте оповіщення с тайтлом, іконкою та текстом.
●     Оповіщення з кнопкою дії.
●     Оповіщення з кнопкою reply, куди можна ввести текст та відобразити його на екрані.
●     Оповіщення з прогресом.
      Тему з оповіщенням, в який можна вводити текст ми не розбирали, щоб ви могли самостійно попрацювати з документацією.
2 - Створити додаток, у якому буде:
●     Відкриття нового Activity с посиланням строки та числа.
●     Кнопка, яка буде відкривати додаток Maps з коордінатами
3 -  Створити додаток, у якому буде два BroadcastReceiver, які будуть:
●     Підписані на зміну connection state (відсилати івенти коли інтернет пропадае та з’являється
●     Підписані на копку на екрані (onReceive() повинен спрацювати коли юзер натисне на кнопку),
      та при тиске повивен будте лог с описанням подіі (“RECEIVER_NAME - user tap on button”)
*/


class MainActivity : AppCompatActivity() {
    var Tag = "MY TAG FOR WIFI CHANGING"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupListeners()
        IntentFilter(MyBroadcastButton.MYBROADCASTBUTTON_ACTION)
        IntentFilter(MyBroadcastInternet.MYBROADCASTCONNECTION_ACTION)
        checkConnection()
        registerReceiver(MyBroadcastInternet(), IntentFilter(MyBroadcastInternet.MYBROADCASTCONNECTION_ACTION))
    }

    private lateinit var binding: ActivityMainBinding

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupListeners() {
        binding.mainButtonBNotification.setOnClickListener {
            NotificationActivity.start(this)
        }

        binding.mainButtonBroadcast.setOnClickListener {
            BroadcastActivity.start(this)
        }

        binding.mainButtonGoodle.setOnClickListener {
            openGoodleMap()
        }

    }

    private fun openGoodleMap() {
        val gmmIntentUri = Uri.parse("geo:37.7749,-122.4194")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkConnection() {
        val manager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        if (networkInfo != null) {
            if (networkInfo.type == TYPE_WIFI) {
                val intent = Intent()
                intent.putExtra(MyBroadcastInternet.MYBROADCASTCONNECTION_MESSAGE, "Connection to WIFI is OK")
                intent.action = MyBroadcastInternet.MYBROADCASTCONNECTION_ACTION
                sendBroadcast(intent)
                Log.d(Tag, "- changing wifi is OK")
            }
        }
    }
}



