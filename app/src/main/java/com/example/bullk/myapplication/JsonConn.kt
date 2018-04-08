package com.example.bullk.myapplication

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by bullk on 2018-02-04.
 */

class JsonConn {
    companion object {
        val url = "http://happyjoy1234.iptime.org:8999/first"

        fun sendMsg(msg : String) : String {
            var response = ""

            try {
                val url = URL(this.url)
                val conn = url.openConnection()!! as HttpURLConnection
                conn.connectTimeout = 5 * 1000
                conn.readTimeout = 5 * 1000
                conn.requestMethod = "POST"
                conn.setRequestProperty("Cache-Control", "no-cache")
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty("Accept", "application/json")
                conn.doOutput = true
                conn.doInput = true

                val outs = conn.outputStream!!
                outs.write(msg.toByteArray())
                outs.flush()

                val responseCode = conn.responseCode!!
                response = "$responseCode, ${HttpURLConnection.HTTP_OK}"
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val ins = conn.inputStream!!
                    val baos = ByteArrayOutputStream()
                    val buff = ByteArray(1024)
                    var len : Int

                    len = ins.read(buff, 0, buff.size)
                    while (len != -1) {
                        baos.write(buff, 0, len)
                        len = ins.read(buff, 0, buff.size)
                    }

                    response = String(baos.toByteArray())
                }
            } catch (e : MalformedURLException) {
                e.printStackTrace()
            } catch (e : IOException) {
                e.printStackTrace()
            } catch (e : Exception) {
                e.printStackTrace()
            }

            return response
        }
    }
}