package ru.vysokov.binchecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.vysokov.binchecker.Model.BroadcastServices
import ru.vysokov.binchecker.Model.RoomDb.BinDatabaseItems
import ru.vysokov.binchecker.databinding.ActivityBinInfoBinding

class BinInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityBinInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data : BinDatabaseItems = intent.getParcelableExtra("historyList")!!
        binding.cardScheme.text = data.scheme.toString()
        binding.cardBrand.text = data.brand.toString()
        binding.cardLength.text = "LENGTH ${data.numberLength.toString()}"
        binding.cardLuhn.text = "LUHN ${data.numberLuhn.toString()}"
        binding.cardType.text = data.type.toString()
        binding.cardPrepaid.text = data.prepaid.toString()
        binding.cardCountry.text = data.countryName.toString()
        binding.bankName.text = data.bankName.toString()
        binding.bankUrl.text = data.bankUrl.toString()
        binding.bankPhone.text = data.bankPhone.toString()
        binding.bankCoordinates.text = "longitude ${data.longitude} latitude ${data.latitude}"

        binding.bankUrl.setOnClickListener {
            BroadcastServices.openUrl(data.bankUrl.toString(), this)
        }

        binding.bankPhone.setOnClickListener {
            BroadcastServices.makeCall(data.bankPhone.toString(), this, this)
        }

        binding.bankCoordinates.setOnClickListener {
            BroadcastServices.openMaps(data.latitude.toString(), data.longitude.toString(), this)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}