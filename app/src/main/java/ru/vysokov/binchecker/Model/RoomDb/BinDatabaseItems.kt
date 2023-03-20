package ru.vysokov.binchecker.Model.RoomDb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = "binDataBase")
data class BinDatabaseItems(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "numberLength")
    var numberLength: Int?,

    @ColumnInfo(name = "numberLuhn")
    var numberLuhn: Boolean?,

    @ColumnInfo(name = "scheme")
    var scheme: String?,

    @ColumnInfo(name = "type")
    var type: String?,

    @ColumnInfo(name = "prepaid")
    var prepaid: Boolean?,

    @ColumnInfo(name = "brand")
    var brand: String?,

    @ColumnInfo(name = "countryName")
    var countryName: String?,

    @ColumnInfo(name = "bankName")
    var bankName: String?,

    @ColumnInfo(name = "bankUrl")
    var bankUrl: String?,

    @ColumnInfo(name = "bankPhone")
    var bankPhone: String?,

    @ColumnInfo(name = "latitude")
    var latitude: Int?,

    @ColumnInfo(name = "longitude")
    var longitude: Int?
) : Parcelable
