package ru.vysokov.binchecker.Model.API

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class Country(
    @SerializedName("numeric"   ) var numeric   : String? = null,
    @SerializedName("alpha2"    ) var alpha2    : String? = null,
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("emoji"     ) var emoji     : String? = null,
    @SerializedName("currency"  ) var currency  : String? = null,
    @SerializedName("latitude"  ) var latitude  : Int?    = null,
    @SerializedName("longitude" ) var longitude : Int?    = null
) //: Parcelable
