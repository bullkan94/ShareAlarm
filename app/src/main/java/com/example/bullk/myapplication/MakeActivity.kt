package com.example.bullk.myapplication

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_make.*
import org.jetbrains.anko.db.*
import java.text.SimpleDateFormat
import java.util.*

class MakeActivity : AppCompatActivity() {
    private var datePickerDialog: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make)

        init()
    }

    private fun init() {
        if(android.os.Build.VERSION.SDK_INT >= 24) {
            datePickerDialog = DatePickerDialog(this)
            datePickerDialog!!.setOnDateSetListener({ _, _, _, _ -> dateText.text = dayFormat() })
        } else {
            val date = Date(System.currentTimeMillis())
            var getDate = SimpleDateFormat("yyyy").format(date)
            val year = getDate.toInt()
            getDate = SimpleDateFormat("MM").format(date)
            val month = getDate.toInt() - 1
            getDate = SimpleDateFormat("dd").format(date)
            val day = getDate.toInt()

            datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { _, _, _, _ ->
                dateText.text = dayFormat()
            }, year, month, day)
        }

        toggleSwitch.isChecked = true
        make.setOnClickListener(okClicked)

        dateText.text = dayFormat()

        toggleSwitch.setOnClickListener(showHideToggle)
        dateText.setOnClickListener(textClicked)
    }

    //Lambda Functions
    private val okClicked: (View) -> Unit = { _ ->
        //Catch Exception
        val datePicker = datePickerDialog!!.datePicker
        val currentTime = System.currentTimeMillis()
        val setTime = Calendar.getInstance()
        setTime.clear()

        if(android.os.Build.VERSION.SDK_INT >= 23)
            setTime.set(datePicker.year, datePicker.month, datePicker.dayOfMonth,
                    timePicker.hour, timePicker.minute)
        else
            setTime.set(datePicker.year, datePicker.month, datePicker.dayOfMonth,
                    timePicker.currentHour, timePicker.currentMinute)

        //When setTime are smaller than currentTime
        if (setTime.timeInMillis < currentTime) {
            Toast.makeText(this, "시간 혹은 날짜를 다시 설정해주세요",
                    Toast.LENGTH_LONG).show()
        } else {
            //Register Alarm
            val mAlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val mAlarmIntent = Intent(this, AlarmActivity::class.java)
            var requestCode = 0
            var cursor = 1
            if(cursor > 0) {
                requestCode = (0..1000000).random()
                this.database.use {
                    cursor = select(MyDataBaseOpenHelper.TABLENAME, "count(*)").
                            whereArgs("({code} = {val})",
                                    "code" to MyDataBaseOpenHelper.REQUESTCODE,
                                    "val" to requestCode).
                            parseSingle(rowParser { count : Int -> count })
                }
            }

            //TODO : Database error check

            val mPendingIntent = PendingIntent.getActivity(
                    this,
                    requestCode,
                    mAlarmIntent,
                    0
            )

            //RTC
            mAlarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    setTime.timeInMillis,
                    mPendingIntent
            )

            //Elapsed
/*
            mAlarmManager.set(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + (setTime.timeInMillis - currentTime),
                    mPendingIntent
            )
*/
            var retCode : Long = -1
            this.database.use {
                retCode = insert(MyDataBaseOpenHelper.TABLENAME,
                        MyDataBaseOpenHelper.REQUESTCODE to requestCode,
                        MyDataBaseOpenHelper.TIME to setTime.timeInMillis,
                        MyDataBaseOpenHelper.DAYOFWEEK to 0,
                        MyDataBaseOpenHelper.ISREPEAT to "false")
            }

            if(retCode < 0) {
                //TODO : Some error control code
            }

            Toast.makeText(this, "알람이 생성 되었습니다", Toast.LENGTH_SHORT).show()

            this.finish()
        }
    }

    private val showHideToggle: (View) -> (Unit) = { it ->
        it as Switch
        if (!it.isChecked) {
            calendarLayout.animate()
                    .translationY(0.0f)
                    .alpha(0.0f)
                    .setListener(MyAnimator(_onAnimationEnd =
                    { _ -> calendarLayout.visibility = View.GONE }))
        } else {
            calendarLayout.animate()
                    .translationY(0.0f)
                    .alpha(1.0f)
                    .setListener(MyAnimator(_onAnimationEnd =
                    { _ -> calendarLayout.visibility = View.VISIBLE }))
        }
    }

    private val dayFormat: () -> (String) = {
        val datePicker = datePickerDialog!!.datePicker
        //${month + 1} -> Month in DatePicker start with 0
        "${datePicker.year}.${datePicker.month + 1}.${datePicker.dayOfMonth}"
    }


    private val textClicked: (View) -> Unit = { _ ->
        datePickerDialog!!.show()
    }

    private fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start + start)
}