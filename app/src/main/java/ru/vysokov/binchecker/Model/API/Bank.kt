package ru.vysokov.binchecker.Model.API

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class Bank(
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("url"   ) var url   : String? = null,
    @SerializedName("phone" ) var phone : String? = null,
    @SerializedName("city"  ) var city  : String? = null
) //: Parcelable
