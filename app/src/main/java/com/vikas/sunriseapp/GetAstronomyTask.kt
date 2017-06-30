package com.vikas.sunriseapp

import android.os.AsyncTask
import com.vikas.sunriseapp.io.HttpConnector
import com.vikas.sunriseapp.io.HttpMethod
import org.json.JSONException
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.net.ConnectException

/**
 * Created by daffolap-402 on 30/6/17.
 */
class GetAstronomyTask(callback: ICallback) : AsyncTask<String, Void, Astronomy?>() {

    private var mCallback: WeakReference<ICallback> = WeakReference(callback)

    override fun doInBackground(vararg params: String?): Astronomy? {
        if (params.size != 1)
            return null
        var astronomy: Astronomy? = null
        val location = params[0]
        var endpoint = "https://query.yahooapis.com/v1/public/yql?" +
                "q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20" +
                "(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22$location%22)&" +
                "format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys"
        try {
            val response = JSONObject(HttpConnector.connect(endpoint, HttpMethod.GET, null))
            val query = response.getJSONObject("query")
            val count: Int = query.getInt("count")
            if (count > 0) {
                val result = query.getJSONObject("results")
                val channel = result.getJSONObject("channel")
                val astr = channel.getJSONObject("astronomy")
                astronomy = Astronomy(astr,location)
            }
        } catch (e: JSONException) {

        } catch (e: ConnectException) {

        } catch (e: Exception) {

        }
        return astronomy
    }

    override fun onPostExecute(result: Astronomy?) {
        super.onPostExecute(result)
        mCallback.get()?.onAstronomyDataReceived(result)
    }
}