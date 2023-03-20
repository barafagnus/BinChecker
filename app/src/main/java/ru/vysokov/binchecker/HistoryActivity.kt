package ru.vysokov.binchecker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.asLiveData
import ru.vysokov.binchecker.Model.RoomDb.BinDatabase
import ru.vysokov.binchecker.Model.RoomDb.BinDatabaseItems
import ru.vysokov.binchecker.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var database : BinDatabase
    lateinit var binding : ActivityHistoryBinding
    private lateinit var mListView : ListView
    private val historyList = ArrayList<BinDatabaseItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mListView = findViewById(R.id.listView)
        database = BinDatabase.getDb(this)

        database.getDao().getAllItem().asLiveData().observe(this) {
            it.forEach {
                historyList.add(it)
            }
            val arrayAdapter : ArrayAdapter<BinDatabaseItems> = HistoryAdapter(this, historyList)
            if (historyList.isNotEmpty()) {
                mListView.adapter = arrayAdapter
            }
        }

        mListView.setOnItemClickListener { parent, view, position, id ->
            val binInfoIntent = Intent(this, BinInfoActivity::class.java)
            val currentElement = historyList.get(position)
            binInfoIntent.putExtra("historyList", currentElement)
            startActivity(binInfoIntent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }


}







