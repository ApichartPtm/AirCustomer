package th.ac.airconditionerservice.firebasedata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//save shop ID

@Parcelize
class Shop (val sid: String): Parcelable{
    constructor(): this ("")
}