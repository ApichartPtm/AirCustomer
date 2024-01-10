package th.ac.airconditionerservice.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import th.ac.airconditionerservice.R
import th.ac.airconditionerservice.firebasedata.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var mEditFirstNameRegister : EditText
    private lateinit var mEditLastNameRegister : EditText
    private lateinit var mEditEmailRegister : EditText
    private lateinit var mEditPassRegister : EditText
    private lateinit var mEditPhone : EditText
    private lateinit var mEditAddress : EditText
    private lateinit var mBtnRegister : Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        mEditFirstNameRegister = findViewById(R.id.editFirstName)
        mEditLastNameRegister = findViewById(R.id.editLastName)
        mEditEmailRegister = findViewById(R.id.editEmailRegister)
        mEditPassRegister = findViewById(R.id.editPassRegister)
        mEditPhone = findViewById(R.id.editPhone)
        mEditAddress = findViewById(R.id.editAddress)
        mBtnRegister = findViewById(R.id.btnRegister)


        mBtnRegister.setOnClickListener {
            checkRegister() // call function checkRegister(
        }
    }


    private fun checkRegister(){

        val firstName = mEditFirstNameRegister.text.toString()
        val lastName = mEditLastNameRegister.text.toString()
        val email = mEditEmailRegister.text.toString()
        val pass = mEditPassRegister.text.toString()
        val phone = mEditPhone.text.toString()

        //check form is not empty
            if(firstName.isEmpty()){
                mEditFirstNameRegister.error = "Please enter First Name"
                mEditFirstNameRegister.requestFocus()
            return
                }
            if (lastName.isEmpty()) {
                mEditLastNameRegister.error = "Please enter Last Name"
                mEditLastNameRegister.requestFocus()
            return
                }
            if (email.isEmpty()) {
                mEditEmailRegister.error = "Please enter Email"
                mEditEmailRegister.requestFocus()
            return
                }
            if (pass.isEmpty()) {
                mEditPassRegister.error = "Please enter Password"
                mEditPassRegister.requestFocus()
            return
                }
            if (phone.isEmpty()) {
                mEditPhone.error = "Please enter Phone Number"
                mEditPhone.requestFocus()
            return
                }


//API Firebase Auth, check email and password for login
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if(!it.isSuccessful)return@addOnCompleteListener
            Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid} ")
            saveUserToFirebaseDatabase() //call function save data user to firebase database
        }
            .addOnFailureListener {
                Log.d("Main","Failed to created user: ${it.message}")
            }


        }


    private fun saveUserToFirebaseDatabase(){ //function save data user to firebase database

        val uid = FirebaseAuth.getInstance().uid ?: "" //save UID from Firebase Auth
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid") //save by path in Firebase database
        val user = User(uid, mEditFirstNameRegister.text.toString(), mEditLastNameRegister.text.toString(), mEditAddress.text.toString(),mEditPhone.text.toString())
        ref.setValue(user).addOnSuccessListener { //if save data to firebase database success and go to CustomerActivity
            val intent = Intent(this, UserActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


}

