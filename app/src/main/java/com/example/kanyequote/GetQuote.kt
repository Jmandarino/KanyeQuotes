package com.example.kanyequote

import android.os.AsyncTask
import android.util.Log
import khttp.get


class GetMethodDemo : AsyncTask<String, Void, String>() {
    var output = "hello"

    override fun doInBackground(vararg strings: String): String? {

        //val url = "http://api.kanye.rest"

        val test = get("https://api.kanye.rest/").jsonObject.get("quote")
        Log.e("error------", test.toString())
        output = test.toString()
        return null
    }

    override fun onPostExecute(s: String) {
        super.onPostExecute(s)

        Log.e("Response", output)
 //https://stackoverflow.com/questions/8654876/http-get-using-android-httpurlconnection/38313386#38313386

    }
}