package com.example.composemovieapp.network.response

import com.example.composemovieapp.network.model.Cast
import com.google.gson.annotations.SerializedName

class CreditResponse (@SerializedName("cast") val cast: List<Cast>)