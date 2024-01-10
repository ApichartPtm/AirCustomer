package th.ac.airconditionerservice.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_confirm.*
import th.ac.airconditionerservice.R
import th.ac.airconditionerservice.firebasedata.Complete
import th.ac.airconditionerservice.firebasedata.User

class ConfirmActivity : AppCompatActivity() {

    private lateinit var mSum : TextView
    private lateinit var mFinish : Button


    var firstName : String =""
    var lastName : String =""
    var phone : String = ""

    var fshop : Int = 0
    var fList : String = ""
    private var fPrice : Int = 0

    var fTruePrice : Int = 0

    private val pro1 = "fa2020nu"
    private val pro2 = "fa2563nu"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        mSum = findViewById(R.id.textAllSum)
        mFinish = findViewById(R.id.btnFinish)

        intent = intent

        val price = intent.getIntExtra("Price" , 0)  //get data from ShopActivity
        val list = intent.getStringExtra("List") //get data from ShopActivity
        val shop = intent.getIntExtra("Shop",0) //get data from ShopActivity

        mSum.text = price.toString()
        textTotal.text = price.toString()
        textSum.text = list

        fshop = shop
        fList = list!!
        fPrice = price

        showData() // call function

        mFinish.setOnClickListener {
            confirmData() // call function
        }

        button_check_promotion.setOnClickListener {
            checkPromotion() // call function
        }
    }

    private fun showData(){ //function show data in activity
        val uid = FirebaseAuth.getInstance().uid
        val mRootRef = FirebaseDatabase.getInstance().getReference("/users/$uid")
        mRootRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(User::class.java)!!
                editAddress2.setText(value.address)
                firstName = value.firstname
                lastName = value.lastname
                phone = value.phone
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun confirmData(){ //function confirm data in activity

        val uid = FirebaseAuth.getInstance().uid
        val cash = "ชำระเงินสด"
        val pic = "https://firebasestorage.googleapis.com/v0/b/airconditionerservice-1eda8.appspot.com/o/none.gif?alt=media&token=d831ffbb-6d8c-4394-a249-891400822cda"
        val id: Int = radio_grop.checkedRadioButtonId
        if (id!=-1){
            if (id == R.id.radio_cash){
                val updateData = FirebaseDatabase.getInstance().getReference("${fshop}/$uid")
                val complete = Complete(uid.toString(),firstName, lastName, editAddress2.text.toString(),phone, fTruePrice.toString(), fList,0,fshop,pic,cash)
                updateData.setValue(complete).addOnSuccessListener {
                    val intent = Intent(this, ThankYouActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }else{
                val sUID = uid.toString()
                val sCID = fshop.toString()
                val intent = Intent(this@ConfirmActivity, MoneyActivity::class.java)
                intent.putExtra("uid", sUID) //save price to ConfirmActivity
                intent.putExtra("cid", sCID) //save list repair to ConfirmActivity
                intent.putExtra("FirstName", firstName)
                intent.putExtra("LastName", lastName)
                intent.putExtra("Address", editAddress2.text.toString())
                intent.putExtra("phone", phone)
                intent.putExtra("Price", fTruePrice.toString())
                intent.putExtra("List", fList)
                intent.putExtra("Status", 0)
                startActivity(intent)
            }


        }
    }

     private fun checkPromotion(){
         if (edit_code_promotion.text.toString() == pro1 || edit_code_promotion.text.toString() == pro2){
             val percent =  fPrice * 0.1
            fTruePrice = fPrice - percent.toInt()
             mSum.text = fTruePrice.toString()
             textPromotion.text = "-50 บาท"
             Toast.makeText(this,"ใช้โค้ดส่วนลด",Toast.LENGTH_LONG).show()
         }else{
             Toast.makeText(this,"โค้ดส่วนลดไม่ถูกต้อง",Toast.LENGTH_LONG).show()
         }
    }

    }


