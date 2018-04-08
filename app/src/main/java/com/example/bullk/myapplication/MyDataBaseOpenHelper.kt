package com.example.bullk.myapplication

import android.content.Context
import android.database.sqlite.*
import org.jetbrains.anko.db.*

/**
 * Created by bullk on 2018-01-22.
 */
class MyDataBaseOpenHelper(context: Context) :
        ManagedSQLiteOpenHelper(context, dbName, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(TABLENAME, true,
                REQUESTCODE to INTEGER + PRIMARY_KEY,
                TIME to INTEGER,
                DAYOFWEEK to INTEGER,
                ISREPEAT to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("", true)
    }

    val SQLiteDatabase.TABLENAME
        get() = "Alarm"



    companion object {
        private const val dbName = "AlarmDB"
        const val TABLENAME = "Alarm"

        const val REQUESTCODE = "requestCode"
        const val TIME = "time"
        const val DAYOFWEEK = "dayOfWeek"
        const val ISREPEAT = "isRepeat"

        private var instance : MyDataBaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx : Context) : MyDataBaseOpenHelper {
            if(instance == null)
                instance = MyDataBaseOpenHelper(ctx.applicationContext)
            return instance!!
        }
    }
}

val Context.database : MyDataBaseOpenHelper
    get() = MyDataBaseOpenHelper.getInstance(applicationContext)