package th.ac.airconditionerservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import th.ac.airconditionerservice.user.UserLoginActivity
import th.ac.airconditionerservice.technician.TechnicianLoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCustomer.setOnClickListener {
            startActivity(Intent(this, UserLoginActivity::class.java))
        }

        buttonUser.setOnClickListener {
            startActivity(Intent(this, TechnicianLoginActivity::class.java))
        }
    }
}