package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "exercise_challenge_table")
data class ExerciseChallenge(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "daysCompleted", defaultValue = "1")
    val daysCompleted: Int?,
    @ColumnInfo(name = "totalDays", defaultValue = "1")
    val totalDays: Int?,
    @ColumnInfo(name = "isFinished", defaultValue = "false")
    val isFinished: Boolean?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(daysCompleted)
        parcel.writeValue(totalDays)
        parcel.writeValue(isFinished)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExerciseChallenge> {
        override fun createFromParcel(parcel: Parcel): ExerciseChallenge {
            return ExerciseChallenge(parcel)
        }

        override fun newArray(size: Int): Array<ExerciseChallenge?> {
            return arrayOfNulls(size)
        }
    }
}