package edu.notes.bankapp.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.notes.bankapp.dao.BankDao
import edu.notes.bankapp.entity.BankEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [BankEntity::class], version = 1, exportSchema = false)
abstract class BankDatabase:RoomDatabase() {
    abstract fun bankDao():BankDao
    companion object{
        @Volatile
        private var INSTANCE:BankDatabase?=null
        @InternalCoroutinesApi
        fun getDatabase(context: Context):BankDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    BankDatabase::class.java,
                    "bank_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE=instance
                return instance
            }
        }
    }
}