package th.ac.airconditionerservice.technician

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_show_list.view.*
import th.ac.airconditionerservice.R
import th.ac.airconditionerservice.comment.CommentLogActivity
import th.ac.airconditionerservice.firebasedata.Complete
import th.ac.airconditionerservice.firebasedata.Data

class ShowListActivity : AppCompatActivity() {

    private val uid1 = "ALFxOF2unrfhn7ArPipBRRsqd2X2" //uid shop3
    private val uid2 = "gOTrHcEAl7QcnwVZKwFIjhuYP9k2" //uid shop3
    private val uid3 = "fqyKMYAJdcW1Rlsk6MSDED4BAnG2"  //uid shop3

    var path = "0"
    var commentId = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        fetchUsers()

        val adapter = GroupAdapter<ViewHolder>()
        recyclerview_list_customer.adapter = adapter

        //call function

        button_view_review.setOnClickListener {
            val intent = Intent(this@ShowListActivity, CommentLogActivity::class.java)
            intent.putExtra("ShopID", commentId) //save price to ConfirmActivity
            startActivity(intent)
        }
    }

    private fun fetchUsers(){

        //check uid shop
        when (FirebaseAuth.getInstance().uid) {
            uid1 -> {
                path = "/1"
                commentId ="1"
            }
            uid2 -> {
                path = "/2"
                commentId ="2"
            }
            uid3 -> {
                path = "/3"
                commentId ="3"
            }
        }

        val ref = FirebaseDatabase.getInstance().getReference(path)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach{

                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(Complete::class.java)

                    if (user != null){
                        adapter.add(UserItem(user))
                    }else {
                        textViewShowList.text = "ยังไม่มีรายการ"
                    }

                }

                adapter.setOnItemClickListener{ item,view->

                    val userItem = item as UserItem

                    val intent = Intent(view.context, ShowDetailActivity::class.java)
                    intent.putExtra("CID", userItem.complete.uid) // save data to ShowDetailActivity
                    intent.putExtra("UID", path) // save data to ShowDetailActivity
                    startActivity(intent)
                    finish()

                }

                recyclerview_list_customer.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


}



class  UserItem(val complete: Complete): Item<ViewHolder>(){

    //set Recycle View List adapter

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textFirstName.text = complete.firstname

        //check status if 1  "ดำเนินการเรียบร้อย" and change color to green or 0 "รอดำเนินการ" and change color to red
        if (complete.status == 1){
            viewHolder.itemView.text_status_list.text = "ดำเนินการเรียบร้อย"
            viewHolder.itemView. text_status_list.setTextColor(Color.parseColor("#00C853"))
        }else{
            viewHolder.itemView.text_status_list.text = "รอดำเนินการ"
            viewHolder.itemView. text_status_list.setTextColor(Color.parseColor("#D50000"))
        }

    }

    override fun getLayout(): Int {
        return R.layout.activity_show_list
    }

}