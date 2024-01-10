package th.ac.airconditionerservice.technician

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login_technician.*
import th.ac.airconditionerservice.R

class TechnicianLoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_technician)

        auth = FirebaseAuth.getInstance()

        btnLoginUser.setOnClickListener {
            checkLogin()
        }
    }

    private fun checkLogin() {

        if (editEmailUser.text.toString().isEmpty()) {
            editEmailUser.error = "Please enter Email"
            editEmailUser.requestFocus()
            return
        }
        if (editPassUser.text.toString().isEmpty()) {
            editPassUser.error = "Please enter Password"
            editPassUser.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(editEmailUser.text.toString(), editPassUser.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }

            }
    }


    private fun updateUI(currentUser : FirebaseUser?){
        if (currentUser != null){
            startActivity(Intent(this, ShowListActivity::class.java))
            finish()
        }else{
            Toast.makeText(baseContext, "Login failed.",
                Toast.LENGTH_SHORT).show()
        }
    }



}