package com.example.bullk.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.bean.*

class DataBean(val requestCode : Int, val startTime : Int)

class DataBeanAdapter(val items : List<DataBean>)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent!!.context).
                inflate(R.layout.bean, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val content = items.get(position)
        holder!!.setString(content)
    }
}

class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val requestCode : TextView = itemView.findViewById(R.id.vRequestCode)
    val time : TextView = itemView.findViewById(R.id.vTime)

    fun setString(context : DataBean) {
        requestCode.text = context.requestCode.toString()
        time.text = context.startTime.toString()
    }
}