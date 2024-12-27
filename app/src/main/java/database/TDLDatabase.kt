package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bignerdranch.android.todolist.Task

@Database(entities = [Task::class], version = 1)
abstract class TDLDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    companion object {
        @Volatile
        private var INSTANCE: TDLDatabase? = null
        fun getDatabase(context: Context): TDLDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TDLDatabase::class.java,
                    "Task_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}