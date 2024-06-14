package com.bytezaptech.jawlineexercise_faceyoga.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class EachDayExerciseModel(val day: String?,
    val img: Int?,
    val title: String?,
    val duration: String?,
    val description: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(day)
        parcel.writeValue(img)
        parcel.writeString(title)
        parcel.writeString(duration)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EachDayExerciseModel> {
        override fun createFromParcel(parcel: Parcel): EachDayExerciseModel {
            return EachDayExerciseModel(parcel)
        }

        override fun newArray(size: Int): Array<EachDayExerciseModel?> {
            return arrayOfNulls(size)
        }
    }
}
