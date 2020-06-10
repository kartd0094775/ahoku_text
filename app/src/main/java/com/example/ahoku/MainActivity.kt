package com.example.ahoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

const val QUERY_URL = "https://westeurope.api.cognitive.microsoft.com/luis/prediction/v3.0/apps/870be0b2-d359-4069-9b3d-baa5d1500d4b/slots/production/predict?subscription-key=30b265146fef419189699d2aa8691734&verbose=true&show-all-intents=true&log=true&query="


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        queryButton.setOnClickListener {
            val query = "$QUERY_URL$queryEditText.text"
        }
    }
}
