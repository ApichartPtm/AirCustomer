package th.ac.airconditionerservice.firebasedata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
class Complete_photo (val profileImageUrl:String): Parcelable{
    constructor(): this ("")
}