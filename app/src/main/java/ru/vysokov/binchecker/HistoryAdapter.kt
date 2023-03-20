package ru.vysokov.binchecker

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.vysokov.binchecker.Model.RoomDb.BinDatabaseItems


class HistoryAdapter(
    private val context: Activity,
    private val historyList: ArrayList<BinDatabaseItems>) : ArrayAdapter<BinDatabaseItems>
    (context, R.layout.item_history, historyList)
    {
        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater : LayoutInflater = LayoutInflater.from(context)
            val view : View = inflater.inflate(R.layout.item_history, parent,false)

            val itemId : TextView = view.findViewById(R.id.itemId)
            val itemBank : TextView = view.findViewById(R.id.itemBank)
            val itemScheme : TextView = view.findViewById(R.id.itemScheme)
            val itemType : TextView = view.findViewById(R.id.itemType)

            itemId.text = historyList[position].id.toString()
            itemBank.text = historyList[position].bankName.toString()
            itemScheme.text = historyList[position].scheme.toString()
            itemType.text = historyList[position].type.toString()
            return view
        }
}