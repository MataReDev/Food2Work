import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Favori::class], version = 1)
@TypeConverters(value = [StringListConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriDao(): FavoriDao

    companion object {
        private const val DATABASE_NAME = "app_database"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                this.instance = instance
                instance
            }
        }
    }
}