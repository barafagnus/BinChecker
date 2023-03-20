package ru.vysokov.binchecker.Model.API

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class Number(
    @SerializedName("length" ) var length : Int?     = null,
    @SerializedName("luhn"   ) var luhn   : Boolean? = null
) //: Parcelable
