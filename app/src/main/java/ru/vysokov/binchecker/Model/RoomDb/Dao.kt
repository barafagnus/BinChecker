package ru.vysokov.binchecker.Model.RoomDb

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: BinDatabaseItems)

    @Query("SELECT * FROM binDataBase")
    fun getAllItem() : Flow<List<BinDatabaseItems>>
}