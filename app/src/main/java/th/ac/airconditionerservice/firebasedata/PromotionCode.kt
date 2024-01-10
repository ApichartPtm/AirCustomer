package th.ac.airconditionerservice.firebasedata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
class PromotionCode (val code: String): Parcelable{
    constructor(): this ("")
}