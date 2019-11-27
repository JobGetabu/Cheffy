package com.app.cheffyuser.profile.model

import android.os.Parcel
import android.os.Parcelable
import com.google.android.libraries.places.api.model.Place

class DropdownItem : Parcelable {
    var primaryText: String? = null
    var secondaryText: String? = null
    var place: Place? = null

    constructor() {}

    constructor(primaryText: String, secondaryText: String, place: Place) {
        this.primaryText = primaryText
        this.secondaryText = secondaryText
        this.place = place
    }

    override fun toString(): String {
        return "DropdownItem{" +
                "primaryText='" + primaryText + '\''.toString() +
                ", secondaryText='" + secondaryText + '\''.toString() +
                ", place name=" + place!!.name +
                ", place getAddress=" + place!!.address +
                ", place getLatLng=" + place!!.latLng +
                '}'.toString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.primaryText)
        dest.writeString(this.secondaryText)
        dest.writeParcelable(this.place, flags)
    }

    protected constructor(`in`: Parcel) {
        this.primaryText = `in`.readString()
        this.secondaryText = `in`.readString()
        this.place = `in`.readParcelable(Place::class.java.classLoader)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DropdownItem> = object : Parcelable.Creator<DropdownItem> {
            override fun createFromParcel(source: Parcel): DropdownItem {
                return DropdownItem(source)
            }

            override fun newArray(size: Int): Array<DropdownItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}
