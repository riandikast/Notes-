package com.binar.challengechapterempat

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id :Int?,

    @ColumnInfo(name = "judul")
    var judul :String?,

    @ColumnInfo(name = "isi")
    var isi :String?,

    @ColumnInfo(name = "email")
    var email: String

):Parcelable
