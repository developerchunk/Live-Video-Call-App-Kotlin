package com.example.videocallappandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class MainActivity : AppCompatActivity() {

    private lateinit var userIdTextField: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userIdTextField = findViewById(R.id.user_id_text_field)
        button = findViewById(R.id.button_next)

        button.setOnClickListener {
            val userId = userIdTextField.text.toString()
            if (userId.isNotEmpty()) {
                val intent = Intent(this@MainActivity, VideoCallActivity::class.java)
                intent.putExtra("userID", userId)
                startActivity(intent)

                videoCallServices(userId)
            }
        }

    }

    private fun videoCallServices(userID: String) {
        val appID: Long = 0L // your App ID of Zoge Cloud
        val appSign = "" // your App Sign of Zoge Cloud
        val application = application // Android's application context
        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true
        val notificationConfig = ZegoNotificationConfig()
        notificationConfig.sound = "zego_uikit_sound_call"
        notificationConfig.channelID = "CallInvitation"
        notificationConfig.channelName = "CallInvitation"
        ZegoUIKitPrebuiltCallInvitationService.init(
            application,
            appID,
            appSign,
            userID,
            userID,
            callInvitationConfig
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        ZegoUIKitPrebuiltCallInvitationService.unInit()
    }
}