package com.example.doan
import android.os.Parcel
import android.os.Parcelable
data class Phone(var imageID:Int,
                 var tenSP:String,
                 var gia:Double,
                 var soLuong:Int = 1,
                 var isSelected: Boolean = false ) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageID)
        parcel.writeString(tenSP)
        parcel.writeDouble(gia)
        parcel.writeInt(soLuong)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Phone> {
        override fun createFromParcel(parcel: Parcel): Phone = Phone(parcel)
        override fun newArray(size: Int): Array<Phone?> = arrayOfNulls(size)
    }
}



