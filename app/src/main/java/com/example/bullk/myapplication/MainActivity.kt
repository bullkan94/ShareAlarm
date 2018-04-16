package com.example.bullk.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import android.os.StrictMode
import android.support.v7.widget.LinearLayoutManager
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showList()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        makeBtn.setOnClickListener(makeClicked)
        button2.setOnClickListener(resetDB)

        val data : List<DataBean> = List(3, { it -> DataBean(it, it) })
        val adapter = DataBeanAdapter(data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private val makeClicked: (View) -> Unit = {_ ->
        debugText.text = ""
        val intent = Intent(this@MainActivity, MakeActivity::class.java)
        startActivityForResult(intent, 1)
    }

    private fun showList() {
        var alarmList : List<AlarmInfo>? = null
        this.database.use {
            alarmList = select(MyDataBaseOpenHelper.TABLENAME, "*").
                    parseList(classParser())
        }

        if(alarmList!!.isNotEmpty())
            debugText.text = alarmList!![0].toString()
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
            delete(MyDataBaseOpenHelper.TABLENAME)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == 1) {
            showList()
        }
    }
}