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

class ThirdShopActivity : AppCompatActivity() {

    private lateinit var s1 : Switch
    private lateinit var mPrice : TextView
    private lateinit var mBtnConfirm : Button
    private var list : String = "ไม่มีรายการ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val price1 = 500
        var sum = 0


        s1 = findViewById(R.id.switch1_1)
        mBtnConfirm = findViewById(R.id.btnConfirm1)
        mPrice = findViewById(R.id.textPrice)

        s1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                sum += price1
                list = "ล้างแอร์"
                mPrice.text = sum.toString()
            }else{
                sum -= price1
                mPrice.text = sum.toString()
            }
        }


        mBtnConfirm.setOnClickListener {
            val intent = Intent(this@ThirdShopActivity, ConfirmActivity::class.java)
            intent.putExtra("Price", sum)
            intent.putExtra("List", list)
            intent.putExtra("Shop", 3)
            startActivity(intent)
        }

        button_view_review.setOnClickListener {
            val intent = Intent(this@ThirdShopActivity, CommentLogActivity::class.java)
            intent.putExtra("ShopID", "3") //save price to ConfirmActivity
            startActivity(intent)
        }


    }
}