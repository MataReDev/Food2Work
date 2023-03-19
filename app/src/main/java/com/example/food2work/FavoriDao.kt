import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favori: Favori)

    @Delete
    fun delete(favori: Favori)

    @Query("SELECT * FROM favoris")
    fun getAll(): LiveData<List<Favori>>
}
