package com.example.bullk.myapplication

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

/**
 * Created by bullk on 2018-01-29.
 */

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val mServiceIntent = Intent(p0, AlarmService::class.java)
        p0!!.startService(mServiceIntent)
    }
}

class AlarmService : Service() {
    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "알람", Toast.LENGTH_SHORT).show()
        return START_NOT_STICKY
    }
}