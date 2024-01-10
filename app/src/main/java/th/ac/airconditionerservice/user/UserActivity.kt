package th.ac.airconditionerservice.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.android.synthetic.main.activity_user.*
import th.ac.airconditionerservice.MainActivity
import th.ac.airconditionerservice.R
import th.ac.airconditionerservice.comment.CommentLogActivity
import th.ac.airconditionerservice.firebasedata.User


class UserActivity : AppCompatActivity() {

    private lateinit var mShop1 : Button
    private lateinit var mShop2 : Button
    private lateinit var mShop3 : Button
    private lateinit var mTel1 : Button
    private lateinit var mTel2 : Button
    private lateinit var mTel3 : Button
    private lateinit var mFirstName : TextView
    private lateinit var mLastName : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        mShop1 = findViewById(R.id.btnChoose1)
        mShop2 = findViewById(R.id.btnChoose2)
        mShop3 = findViewById(R.id.btnChoose3)
        mTel1 = findViewById(R.id.btnTel1)
        mTel2 = findViewById(R.id.btnTel2)
        mTel3 = findViewById(R.id.btnTel3)
        mFirstName = findViewById(R.id.textViewName)
        mLastName = findViewById(R.id.textViewLastName)

        val uid = FirebaseAuth.getInstance().uid  //User ID code
        val mRootRef = FirebaseDatabase.getInstance().getReference("/users/$uid") //path in Firebase

        mRootRef.addValueEventListener(object : ValueEventListener {  // Firebase show username
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(User::class.java)!!
                mFirstName.text = value.firstname
                mLastName.text = value.lastname
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


        mShop1.setOnClickListener {//button choose shop 1
            val intent = Intent(applicationContext, FirstShopActivity::class.java)
            startActivity(intent)
        }

        mShop2.setOnClickListener {//button choose shop 2
            val intent = Intent(applicationContext, SecondShopActivity::class.java)
            startActivity(intent)
        }

        mShop3.setOnClickListener { //button choose shop 3
            val intent = Intent(applicationContext, ThirdShopActivity::class.java)
            startActivity(intent)
        }


        mTel1.setOnClickListener {// button function Call Shop1
            callShop1("tel:055212677")
        }

        mTel2.setOnClickListener {// button function Call Shop2
            callShop2("tel:0817867399")
        }

        mTel3.setOnClickListener {  // button function Call Shop3
            callShop3("tel:055220264")
        }


        button_lockout_customer.setOnClickListener {// button Log Out
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        button_review_1.setOnClickListener {
            val intent = Intent(this@UserActivity, CommentLogActivity::class.java)
            intent.putExtra("ShopID", "1") //save price to ConfirmActivity
            startActivity(intent)
        }

        button_review_2.setOnClickListener {
            val intent = Intent(this@UserActivity, CommentLogActivity::class.java)
            intent.putExtra("ShopID", "2") //save price to ConfirmActivity
            startActivity(intent)
        }

        button_review_3.setOnClickListener {
            val intent = Intent(this@UserActivity, CommentLogActivity::class.java)
            intent.putExtra("ShopID", "3") //save price to ConfirmActivity
            startActivity(intent)
        }

        button_promotion.setOnClickListener {
            val intent = Intent(this@UserActivity, PromotionActivity::class.java)
            startActivity(intent)
        }
    }

    //function Call Shop1
    private fun callShop1(number : String){  //function การโทรออก
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(number)
        startActivity(intent)
    }

    //function Call Shop2
    private fun callShop2(number: String){  //function การโทรออก
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(number)
        startActivity(intent)
    }

    //function Call Shop3
    private fun callShop3(number: String){  //function การโทรออก
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(number)
        startActivity(intent)
    }

}

