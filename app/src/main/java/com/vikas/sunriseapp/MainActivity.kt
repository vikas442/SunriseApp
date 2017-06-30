package com.vikas.sunriseapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ICallback {

    override fun onAstronomyDataReceived(data: Astronomy?) {
        tvLabel.text = data?.city
        tvSunrise.text = getString(R.string.sunrise_template, data?.sunriseTime)
        tvSunset.text = getString(R.string.sunset_template, data?.sunsetTime)
        etCity.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener { getAstronomy() }
    }

    fun getAstronomy() {
        GetAstronomyTask(this).execute(etCity.text.toString())
    }
}
