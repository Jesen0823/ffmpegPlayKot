package com.jesen.code.ffmpegplaykot.databean

import android.os.Parcel
import android.os.Parcelable

data class MediaPrev(var mimeType:String?,
                     var filePath:String?,
                     var previewPath:String?,
                     var createTime: Long) :Parcelable, Comparable<MediaPrev>{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mimeType)
        parcel.writeString(filePath)
        parcel.writeString(previewPath)
        parcel.writeLong(createTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaPrev> {
        override fun createFromParcel(parcel: Parcel): MediaPrev {
            return MediaPrev(parcel)
        }

        override fun newArray(size: Int): Array<MediaPrev?> {
            return arrayOfNulls(size)
        }
    }

    override fun compareTo(other: MediaPrev): Int {
        return other.createTime.compareTo(this.createTime)
    }

    fun isVideo():Boolean{
        mimeType?.let {
            return it.contains("video")
        }
        return false
    }

    fun isPicture():Boolean{
        return if (mimeType.isNullOrEmpty()) false else mimeType!!.contains("image")
    }

}