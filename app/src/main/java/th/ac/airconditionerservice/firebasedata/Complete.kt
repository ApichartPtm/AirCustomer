package th.ac.airconditionerservice.firebasedata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
class Complete (val uid: String, val firstname: String, val lastname:String, val address: String, val phone: String, val price:String, val list:String, val status:Int, val shop:Int, val profileImageUrl:String, val type:String): Parcelable{
    constructor(): this ("","","","","","","",0,0,"","")
}