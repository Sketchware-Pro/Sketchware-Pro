package mod.ilyasse.utils

import android.os.Handler
import android.os.Looper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Network {
    private val client = OkHttpClient()

    /**
     * Send a GET request to the specified URL
     *
     * @param url     The URL to send the request to
     * @param handler The handler to handle the response.
     * <br></br>
     * Careful: This method respondes on the UI thread
     */
    fun get(url: String, handler: ResponseHandler) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread { handler.handleResponse(null) }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    try {
                        val responseBody = response.body!!.string()
                        runOnUiThread { handler.handleResponse(responseBody) }
                    } catch (e: IOException) {
                        runOnUiThread { handler.handleResponse(null) }
                    }
                } else {
                    runOnUiThread { handler.handleResponse(null) }
                }
            }
        })
    }

    fun interface ResponseHandler {
        fun handleResponse(response: String?)
    }

    companion object {
        fun runOnUiThread(runnable: Runnable) {
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post(runnable)
        }
    }
}
