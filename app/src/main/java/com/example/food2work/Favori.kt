import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "favoris")
data class Favori(
    @PrimaryKey val pk: Int,
    val title: String,
    val description: String,
    val featured_image: String,
    @TypeConverters(StringListConverter::class) val ingredients: List<String>,
    val date_added: String?,
    val date_updated: String?

)