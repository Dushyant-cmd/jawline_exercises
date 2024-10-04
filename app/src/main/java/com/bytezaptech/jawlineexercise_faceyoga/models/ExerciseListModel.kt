package com.bytezaptech.jawlineexercise_faceyoga.models

import android.os.Parcel
import android.os.Parcelable
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import java.io.Serializable

data class ExerciseListModel(val name: String, val day: Int,  val isFinished: Boolean, val exerciseChallenge: ExerciseChallenge?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(ExerciseChallenge::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(day)
        parcel.writeByte(if (isFinished) 1 else 0)
        parcel.writeParcelable(exerciseChallenge, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExerciseListModel> {
        override fun createFromParcel(parcel: Parcel): ExerciseListModel {
            return ExerciseListModel(parcel)
        }

        override fun newArray(size: Int): Array<ExerciseListModel?> {
            return arrayOfNulls(size)
        }
    }
}
