package th.ac.airconditionerservice.technician

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_detail.*
import th.ac.airconditionerservice.R
import th.ac.airconditionerservice.firebasedata.Complete

class ShowDetailActivity : AppCompatActivity() {

    var shop = 0
    var uri = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)

        val cid = intent.getStringExtra("CID") // get data from ShowListActivity
        val uid = intent.getStringExtra("UID") // get data from ShowListActivity


        val mRootRef = FirebaseDatabase.getInstance().getReference("$uid/$cid")
        mRootRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val detail = p0.getValue(Complete::class.java)
                if (detail != null){
                    uri = detail.profileImageUrl
                    //set data from Firebase database
                    text_detail_name.text = detail.firstname
                    text_detail_lastname.text = detail.lastname
                    text_detail_list.text = detail.list
                    text_detail_address.text = detail.address
                    text_detail_phone.text = detail.phone
                    text_status.text = detail.status.toString()
                    Picasso.get().load(uri).into(image_slip_detail)
                    text_detail_price.text = detail.price
                    shop = detail.shop
                    text_type_detail.text = detail.type

                }else{ //when delete finish go to ShowListActivity
                    val intent = Intent(applicationContext, ShowListActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                if (text_status.text == "1") { //check status
                    text_status.text = getString(R.string.status_complete)
                    text_status.setTextColor(Color.parseColor("#00C853"))

                }
                else{ //check status
                    text_status.text = getString(R.string.status_not_complete)
                    text_status.setTextColor(Color.parseColor("#D50000"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })



        button_complete.setOnClickListener { // button complete
            text_status.setText(R.string.status_complete) //set text
            text_status.setTextColor(Color.parseColor("#D50000")) // set text color
            val status = 1  // defines status = 1 (complete repair)
            val ref = FirebaseDatabase.getInstance().getReference("/$uid/$cid") // path Firebase database
            val complete = Complete(cid!!.toString(),text_detail_name.text.toString(),text_detail_lastname.text.toString(),text_detail_address.text.toString(), text_detail_phone.text.toString(),text_detail_price.text.toString(), text_detail_list.text.toString(),status,shop,uri,text_type_detail.text.toString())
            ref.setValue(complete).addOnSuccessListener {
                val intent = Intent(this, ShowListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        }

        button_back_detail.setOnClickListener {//button back
            startActivity(Intent(this, ShowListActivity::class.java))
            finish()
        }

        button_delete_detail.setOnClickListener { //button delete list
            val deleteDatabase = FirebaseDatabase.getInstance().getReference("/$uid/$cid")
            deleteDatabase.removeValue()
            }
            }
    }
