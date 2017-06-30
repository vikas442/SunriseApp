package com.vikas.sunriseapp

import org.json.JSONObject

/**
 * Created by daffolap-402 on 30/6/17.
 */
data class Astronomy(val sunriseTime: String = "", val sunsetTime: String = "", val city: String? = "") {
    constructor(jsonObject: JSONObject, city: String?) : this(
            jsonObject.getString("sunrise"),
            jsonObject.getString("sunset"),
            city
    )
}