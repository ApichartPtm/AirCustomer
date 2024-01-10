package th.ac.airconditionerservice.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_list.*
import th.ac.airconditionerservice.R
import th.ac.airconditionerservice.comment.CommentLogActivity

class FirstShopActivity : AppCompatActivity() {

    private lateinit var s1 : Switch
    private lateinit var mPrice : TextView
    private lateinit var mBtnConfirm : Button
    private var list : String = "ไม่มีรายการ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        val price1 = 500  //price
        var sum = 0


        s1 = findViewById(R.id.switch1_1)
        mBtnConfirm = findViewById(R.id.btnConfirm1)
        mPrice = findViewById(R.id.textPrice)

        s1.setOnCheckedChangeListener { compoundButton, isChecked -> //function choose repair
            if (isChecked){
                sum += price1
                list = "ล้างแอร์"
                mPrice.text = sum.toString()
            }else{
                sum -= price1
                mPrice.text = sum.toString()
            }
        }


        mBtnConfirm.setOnClickListener {  //function confirm and go to ConfirmActivity
            val intent = Intent(this@FirstShopActivity, ConfirmActivity::class.java)
            intent.putExtra("Price", sum) //save price to ConfirmActivity
            intent.putExtra("List", list) //save list repair to ConfirmActivity
            intent.putExtra("Shop", 1) //save shop number 1-3 to ConfirmActivity
            startActivity(intent)
        }


    }
}