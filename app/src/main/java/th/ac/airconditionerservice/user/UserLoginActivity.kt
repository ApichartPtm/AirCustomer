package th.ac.airconditionerservice.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login_user.*
import th.ac.airconditionerservice.R

class UserLoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)


        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            checkLogin()  // call function checkLogin(
        }

        textRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish() //go to RegisterActivity
        }

    }

    private fun checkLogin() { //function CheckLogin()

        if (editEmail1.text.toString().isEmpty()) { // check email is not empty
            editEmail1.error = "Please enter Email"
            editEmail1.requestFocus()
            return
        }
        if (editPass1.text.toString().isEmpty()) { // check password is not empty
            editPass1.error = "Please enter Password"
            editPass1.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(editEmail1.text.toString(), editPass1.text.toString()) // API Firebase Auth, check email and password
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }

            }
    }


    private fun updateUI(currentUser : FirebaseUser?){  //function when login success
        if (currentUser != null){ // if login success go to CustomerActivity
            startActivity(Intent(this, UserActivity::class.java))
            finish()
        }else{ //else login failed show text Login failed
            Toast.makeText(baseContext, "Login failed.",
                Toast.LENGTH_SHORT).show()
        }
    }



}