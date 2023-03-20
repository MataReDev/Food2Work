package com.example.food2work

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.food2work.database.StringListConverter

@Entity(tableName = "recettes_favoris")
data class RecipeModel(
    @PrimaryKey var pk: Int,
    var title: String,
    var description: String,
    var featured_image: String,
    @TypeConverters(StringListConverter::class) var ingredients: List<String>,
    var date_added: String?,
    var date_updated: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(pk)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(featured_image)
        parcel.writeStringList(ingredients)
        parcel.writeString(date_added)
        parcel.writeString(date_updated)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeModel> {
        override fun createFromParcel(parcel: Parcel): RecipeModel {
            return RecipeModel(parcel)
        }

        override fun newArray(size: Int): Array<RecipeModel?> {
            return arrayOfNulls(size)
        }
    }
}
