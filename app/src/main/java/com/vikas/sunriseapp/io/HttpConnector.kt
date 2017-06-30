package com.vikas.sunriseapp.io

import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Created by konny on 29/02/16.
 */
object HttpConnector {
    private val TAG = HttpConnector::class.java.simpleName

    val SERVER_ADDRESS = "https://api.matchrider.de"

    /**
     * Create server connection to endpoint and makes POST call
     * @param endpoint API endpoint
     * *
     * @return response body
     */
    @Throws(IOException::class)
    fun connect(endpoint: String, method: String, requestData: String?): String {

        var inputStream: InputStream?
        var urlConnection: HttpsURLConnection?
        val url = URL(endpoint)

        urlConnection = url.openConnection() as HttpsURLConnection
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
        urlConnection.setRequestProperty("Accept", "application/json")

        urlConnection.requestMethod = method

        if (requestData != null) {
            val outputBytes = requestData.toByteArray(charset("UTF-8"))
            val os = urlConnection.outputStream
            os.write(outputBytes)
            os.flush()
        }

        inputStream = BufferedInputStream(urlConnection.inputStream)
        val input = BufferedReader(InputStreamReader(inputStream))
        val response = convertInputStreamToString(input)
        urlConnection.disconnect()
        return response
    }

    /**
     * Converts input stream from server to String
     * @param in buffered input stream
     * *
     * @return server response as string
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun convertInputStreamToString(input: BufferedReader): String {
        val response = StringBuilder()
        var line = input.readLine()

        while (line != null) {
            response.append(line)
            line = input.readLine()
        }
        return response.toString()
    }
}
