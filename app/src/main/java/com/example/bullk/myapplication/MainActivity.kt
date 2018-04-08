package com.example.bullk.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import android.os.StrictMode
import android.text.util.Linkify
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        makeBtn.setOnClickListener(makeClicked)
        button2.setOnClickListener(nnClicked)
    }

    private val makeClicked: (View) -> Unit = {_ ->
        val intent = Intent(this@MainActivity, MakeActivity::class.java)
        startActivity(intent)
    }

    private val connClicked : (View) -> Unit = {_ ->
        val msg = JSONObject()
        msg.put("title", "hello, Json")
        msg.put("content", "hello, Json")

        val str =  JsonConn.sendMsg(msg.toString())
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    private val resetDB : (View) -> Unit = { _ ->
        this.database.use {
            delete(MyDataBaseOpenHelper.TABLENAME, "*")
        }
    }

    //TODO : When MakeActivity closed, refresh alarm list
    private val nnClicked : (View) -> Unit = { _ ->
        var alarmList : List<AlarmInfo>? = null
        this.database.use {
            alarmList = select(MyDataBaseOpenHelper.TABLENAME, "*").
                    parseList(classParser())
        }

        if(alarmList!!.isNotEmpty())
            debugText.text = alarmList!![0].toString()
    }
}