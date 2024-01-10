package th.ac.airconditionerservice.firebasedata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//save User data

@Parcelize
class User (val uid: String, val firstname: String, val lastname:String, val address: String,val phone: String): Parcelable{
    constructor(): this ("","","","","")
}