package com.example.bullk.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_alarm.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select

class AlarmActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val requestCode = intent.getIntExtra("requestCode", 0)

        showInfo(requestCode)

    }

    override fun onBackPressed() {
        super.onBackPressed()

        val requestCode = intent.getIntExtra("requestCode", 0)

        deleteAlarm(requestCode)
        finish()
    }

    private fun showInfo(requestCode : Int) {
        var alarmList : List<AlarmInfo>? = null
        this.database.use {
            alarmList = select(MyDataBaseOpenHelper.TABLENAME, "*").
                    whereArgs("requestCode = {requestCode}",
                            "requestCode" to requestCode).
                    parseList(classParser())
        }

        if(alarmList!!.isNotEmpty())
            textView.text = alarmList!![0].toString()
    }

    private fun deleteAlarm(requestCode: Int) {
        this.database.use {
            delete(MyDataBaseOpenHelper.TABLENAME, "requestCode = {requestCode}",
                    "requestCode" to requestCode)
        }
    }
}