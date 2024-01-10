package th.ac.airconditionerservice.user

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.android.synthetic.main.activity_money.*
import kotlinx.android.synthetic.main.activity_show_detail.*
import th.ac.airconditionerservice.R
import th.ac.airconditionerservice.firebasedata.Complete
import th.ac.airconditionerservice.firebasedata.Complete_photo
import th.ac.airconditionerservice.technician.ShowListActivity
import java.util.*

class MoneyActivity : AppCompatActivity() {

    var sCid = ""
    var sUid = ""
    var sFirstName = ""
    var sLastName = ""
    var sAddress = ""
    var sPhone = ""
    var sPrice = ""
    var sList = ""
    var sStatus = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money)

        intent = intent
        val cid = intent.getStringExtra("cid")
        val uid = intent.getStringExtra("uid") // get data from ShowListActivity
        val firstname = intent.getStringExtra("FirstName")
        val lastname = intent.getStringExtra("LastName")
        val address = intent.getStringExtra("Address")
        val phone = intent.getStringExtra("phone")
        val price = intent.getStringExtra("Price")
        val list = intent.getStringExtra("List")
        val status = intent.getIntExtra("Status", 0)

            sUid = uid
            sCid = cid
            sFirstName = firstname
            sLastName  = lastname
            sAddress = address
            sPhone =  phone
            sPrice = price
            sList = list
            sStatus = status



        button_send_slipe.setOnClickListener {
            uploadImageToFirebaseStorage()
        }

        button_back_money.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        button_select_photo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }

    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            selectphoto_imageview.setImageBitmap(bitmap)
            button_select_photo.alpha = 0f
        }
    }

    private fun uploadImageToFirebaseStorage(){
        if (selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            Log.d("Register","Successfully uploaded image: ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("Register","File Location: $it")
                saveUserToFirebaseDatabase(it.toString())
            }
        }
    }


    private fun saveUserToFirebaseDatabase(profileImageUrl:String){
        val ref = FirebaseDatabase.getInstance().getReference("/$sCid/$sUid")
        val type = "โอนเงิน"
        val complete = Complete(sUid,sFirstName, sLastName, sAddress,sPhone, sPrice, sList,sStatus,0,profileImageUrl,type)
        ref.setValue(complete).addOnSuccessListener {
            val intent = Intent(this, ThankYouActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}