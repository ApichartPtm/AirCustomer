package th.ac.airconditionerservice.firebasedata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//save user data

@Parcelize
class Data (val uid: String, val firstname: String, val lastname:String, val address: String, val phone: String, val price:String, val list:String, val status:Int, val shop:Int): Parcelable{
    constructor(): this ("","","","","","","",0,0)
}