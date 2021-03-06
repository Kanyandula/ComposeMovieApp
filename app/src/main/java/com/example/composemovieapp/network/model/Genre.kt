package com.example.composemovieapp.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    var id: Int.Companion,
    var name: String
): Parcelable