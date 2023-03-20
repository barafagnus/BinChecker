package ru.vysokov.binchecker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.*
import retrofit2.HttpException
import ru.vysokov.binchecker.Model.API.BinData
import ru.vysokov.binchecker.Model.BroadcastServices
import ru.vysokov.binchecker.Model.RetrofitHelper.BinService
import ru.vysokov.binchecker.Model.RetrofitHelper.RetrofitHelper
import ru.vysokov.binchecker.Model.RoomDb.BinDatabase
import ru.vysokov.binchecker.Model.RoomDb.BinDatabaseItems
import ru.vysokov.binchecker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private var checkBinData : BinData = BinData()
    private lateinit var database : BinDatabase
    private lateinit var TAG : String
    private var binService = RetrofitHelper.getInstance().create(BinService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TAG = this::class.java.simpleName
        database = BinDatabase.getDb(this@MainActivity)

        binding.btnEnter.setOnClickListener {
            if (binding.editText.text.toString().isEmpty()) {
                Toast.makeText(this@MainActivity, "Неверный ввод", Toast.LENGTH_SHORT).show()
            }
            else {
                getBinDataJob(binding.editText.text.toString())
            }
            val view: View? = this.currentFocus
            if (view != null) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        binding.btnHistory.setOnClickListener {
            val historyIntent = Intent(this, HistoryActivity::class.java)
            startActivity(historyIntent)
        }

        binding.bankPhone.setOnClickListener {
            if (checkBinData.bank?.phone != null) {
               BroadcastServices.makeCall(checkBinData.bank?.phone.toString(), this, this@MainActivity)
            }
        }

        binding.bankUrl.setOnClickListener {
            if (checkBinData.bank?.url != null) {
                BroadcastServices.openUrl(checkBinData.bank?.url.toString(), this)
            }
        }

        binding.bankCoordinates.setOnClickListener {
            if (checkBinData.country?.latitude != null && checkBinData.country?.longitude != null) {
                BroadcastServices.openMaps(
                    checkBinData.country?.latitude.toString(),
                    checkBinData.country?.longitude.toString(),
                    this
                )
            }
        }
    }

    private fun getBinDataJob(binNumber : String)  {
        CoroutineScope(Dispatchers.IO).launch {
            val result = binService.getData(binNumber)
            withContext(Dispatchers.Main) {
                try {
                    if (result.isSuccessful) {
                        checkBinData = result.body()!!
                        renderUi()
                        renderUiColors()
                        databaseEntry()
                    }
                    else {
                        Toast.makeText(this@MainActivity, "Ошибка получения данных\n(проверьте корректность ввода)", Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e: HttpException) { Log.e(TAG, e.message.toString()) }
                catch (e: Throwable) { Log.e(TAG, e.message.toString()) }
            }
        }
    }

    private fun databaseEntry() {
        lifecycleScope.launch(Dispatchers.IO) {
            val items = BinDatabaseItems(
                null,
                numberLength = checkBinData.number?.length,
                numberLuhn = checkBinData.number?.luhn,
                scheme = checkBinData.scheme,
                type = checkBinData.type,
                prepaid = checkBinData.prepaid,
                brand = checkBinData.brand,
                countryName = checkBinData.country?.name,
                bankName = checkBinData.bank?.name,
                bankUrl = checkBinData.bank?.url,
                bankPhone = checkBinData.bank?.phone,
                latitude = checkBinData.country?.latitude,
                longitude = checkBinData.country?.longitude
            )
            database.getDao().insertItem(items)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderUi() {
        binding.cardScheme.text = checkBinData.scheme.toString()
        binding.cardType.text = checkBinData.type.toString()
        binding.cardBrand.text = checkBinData.brand.toString()
        binding.cardPrepaid.text = checkBinData.prepaid.toString()
        binding.cardLength.text = "LENGTH ${checkBinData.number?.length.toString()}"
        binding.cardLuhn.text = "LUHN ${checkBinData.number?.luhn.toString()}"
        binding.cardCountry.text = checkBinData.country?.name.toString()
        binding.bankName.text = checkBinData.bank?.name.toString()
        binding.bankUrl.text = checkBinData.bank?.url.toString()
        binding.bankPhone.text = checkBinData.bank?.phone.toString()
        binding.bankCoordinates.text = "longitude ${checkBinData.country?.longitude} latitude ${checkBinData.country?.latitude}"
    }

    private fun renderUiColors() {
        if (checkBinData.bank?.url != null) {
            binding.bankUrl.setTextColor(ContextCompat.getColor(this, R.color.blue))
        }
        else binding.bankUrl.setTextColor(ContextCompat.getColor(this, R.color.grey))

        if (checkBinData.bank?.phone != null) {
            binding.bankPhone.setTextColor(ContextCompat.getColor(this, R.color.blue))
        }
        else binding.bankPhone.setTextColor(ContextCompat.getColor(this, R.color.grey))

        if (checkBinData.country?.latitude != null && checkBinData.country?.longitude != null) {
            binding.bankCoordinates.setTextColor(ContextCompat.getColor(this, R.color.blue))
        }
        else binding.bankCoordinates.setTextColor(ContextCompat.getColor(this, R.color.grey))
    }
}



