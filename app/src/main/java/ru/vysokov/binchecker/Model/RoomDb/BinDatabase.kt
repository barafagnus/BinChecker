package ru.vysokov.binchecker.Model.RoomDb

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database (
    entities = [BinDatabaseItems::class],
    version = 2,
    autoMigrations = [AutoMigration (from = 1, to = 2)]
)

abstract class BinDatabase : RoomDatabase() {
    abstract fun getDao() : Dao
    companion object {
        fun getDb(context: Context) : BinDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                BinDatabase::class.java,
                "BinDataDb.db")
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE binDataBase ADD COLUMN latitude INTEGER")
        database.execSQL("ALTER TABLE binDataBase ADD COLUMN longitude INTEGER")
    }

}

