package ru.vysokov.binchecker.Model

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity


class BroadcastServices {
    companion object {
        private lateinit var TAG : String

        fun makeCall(bankNumber : String, context: Context, activity: Activity) {
            try {
                if (bankNumber.isNotEmpty()) {
                    val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${bankNumber}"))
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE), 101)
                        Toast.makeText(context, "Доступ запрещен", Toast.LENGTH_SHORT).show()
                    } else startActivity(context, callIntent, null)
                }
            } catch (e : Exception) {
                Log.e(TAG, e.message.toString())
            }
        }

        fun openUrl(bankUrl : String, context: Context) {
            var bankUrl = bankUrl
            if (bankUrl.startsWith("https://") && !bankUrl.startsWith("http://")) {
                bankUrl = "https://${bankUrl}"
            }
            val openUrl = Intent(Intent.ACTION_VIEW, Uri.parse("https://${bankUrl}"))
            startActivity(context, openUrl, null)
        }

        fun openMaps(latitude : String, longitude : String, context: Context) {
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${latitude},${longitude}"))
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(context, mapIntent, null)
        }
    }



}