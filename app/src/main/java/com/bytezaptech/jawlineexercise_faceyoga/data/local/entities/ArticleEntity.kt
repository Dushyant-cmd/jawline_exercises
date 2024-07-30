package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?, val title: String?, val description: String?, val image: Int?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeValue(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleEntity> {
        override fun createFromParcel(parcel: Parcel): ArticleEntity {
            return ArticleEntity(parcel)
        }

        override fun newArray(size: Int): Array<ArticleEntity?> {
            return arrayOfNulls(size)
        }
    }
}