package com.example.ahoku

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

const val QUERY_URL = "https://westeurope.api.cognitive.microsoft.com/luis/prediction/v3.0/apps/870be0b2-d359-4069-9b3d-baa5d1500d4b/slots/production/predict?subscription-key=30b265146fef419189699d2aa8691734&verbose=true&show-all-intents=true&log=true&query="
const val DEBUG_TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        queryButton.setOnClickListener {
            if (queryEditText.text.isNullOrEmpty()) return@setOnClickListener
            val queue = Volley.newRequestQueue(this)
            val url = "$QUERY_URL${queryEditText.text}"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    Log.d(DEBUG_TAG, response)
                    val result = JSONObject(response)
                    val prediction = result.getJSONObject("prediction")
                    val topIntent = prediction.getString("topIntent")
                    val entities = prediction.getJSONObject("entities")

                    resultTopIntent.text = str(R.string.result_topintent, topIntent)
                    if (entities.has("Web.SearchText")) {
                        val text = entities.getJSONArray("Web.SearchText")
                        resultText.text = str(R.string.result_text, text.join(","))
                    }
                },
                Response.ErrorListener { Log.e(DEBUG_TAG,"That didn't work!") })
            queue.add(stringRequest)
        }
    }

    private fun Context.str(
        @StringRes stringResId: Int,
        vararg formatArgs: Any?
    ): String = resources.getString(stringResId, *formatArgs)
}
