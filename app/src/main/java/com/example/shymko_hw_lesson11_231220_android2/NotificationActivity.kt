package com.example.shymko_hw_lesson11_231220_android2

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.shymko_hw_lesson11_231220_android2.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    companion object {
        private const val CHANNEL_ID = "NOTIFICATIONACTIVITY_CHANNEL_ID"
        private const val CHANNEL_NAME = "NOTIFICATIONACTIVITY_CHANNEL_NAME"
        const val REPLY_KEY = "reply_action"
        const val NOTIFICATION_ID = 1010

        fun start(context: Context) {
            val intent = Intent(context, NotificationActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityNotificationBinding

    //   @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        setupBinding()
        setupListenerSimpleTitle()
        setupListenerButtonActivity()
        setupListenerReply()
        reciveReplyInput()
        setupListenerProgress()
    }

    private fun setupBinding() {
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun setupListenerSimpleTitle() {
        binding.notificationButtonSimpleTitle.setOnClickListener {
            sendNotificationSimple()
        }
    }

    private fun setupListenerButtonActivity() {
        binding.notificationButtonActivity.setOnClickListener {
            sendNotificationWithActionButton()
        }
    }

    private fun setupListenerReply() {
        binding.notificationButtonReply.setOnClickListener {
            sendNotificationWithReply()
        }
    }

    private fun setupListenerProgress() {
        binding.notificationButtonProgress.setOnClickListener {
            sendNotificationWithProgress()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = "descriptionText"
            }
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotificationSimple() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Title for NotificationSimple")
                .setContentText("Text for NotificationSimple")
                .setStyle(
                        NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line...")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        createNotificationChannel()
        NotificationManagerCompat.from(this).notify(1, builder.build())

    }

    private fun sendNotificationWithActionButton() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Title for NotificationSimple")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_foreground, "Action Title-Go to MAIN ACTIVITY", pendingIntent)
                .setContentIntent(pendingIntent)
        createNotificationChannel()
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    private fun sendNotificationWithReply() {
        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                0
        )

        val remoteIntput = RemoteInput.Builder(REPLY_KEY).run {
            setLabel("Your answer...")
            build()
        }

        val replyAction = NotificationCompat.Action.Builder(
                0,
                "REPLY",
                pendingIntent)
                .addRemoteInput(remoteIntput)
                .build()

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Title for NotificationSimple")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(replyAction)

        createNotificationChannel()
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun reciveReplyInput() {
        val replyKey = REPLY_KEY
        val channelD = CHANNEL_ID

        val intent = this.intent
        val replyInput = android.app.RemoteInput.getResultsFromIntent(intent)

        if (replyInput != null) {
            val inputReplyString = replyInput.getCharSequence(replyKey).toString()
            binding.notificationButtonReply.text = inputReplyString

            val notificationID = NOTIFICATION_ID
            val updateCurrentTextOnButton = NotificationCompat.Builder(this@NotificationActivity, channelD)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Message sent success")
                    .setContentText("Updated notification")
                    .build()
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationID, updateCurrentTextOnButton)
        }
    }

    private fun sendNotificationWithProgress() {
        val PROGRESS_MAX = 100
        val PROGRESS_CURRENT = 0
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Title for NotificationSimple")
                .setContentText("Text for NotificationSimple")
                .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, true)
                .setStyle(
                        NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line...")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        createNotificationChannel()
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }
}