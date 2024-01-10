package th.ac.airconditionerservice.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_comment_log.*
import kotlinx.android.synthetic.main.comment_from_row.view.*
import kotlinx.android.synthetic.main.comment_to_row.view.*
import th.ac.airconditionerservice.R
import th.ac.airconditionerservice.firebasedata.User

class CommentLogActivity : AppCompatActivity() {

    companion object{
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<ViewHolder>()
    var FromUser: User? = null
    var shopId  = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_log)

        recycleView_comment_log.adapter = adapter



        val idShop = intent.getStringExtra("ShopID")
        shopId = idShop

        // setupDummyData()
        listenForMessage()

        sendButton_comment_log.setOnClickListener {
            performSendMessage()
        }
    }

    private fun listenForMessage(){
        val currentUser = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-comment/$shopId")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                if (chatMessage != null){
                    Log.d(TAG, chatMessage.text)
                    if (currentUser == chatMessage.fromId){
                        adapter.add(ChatToItem(chatMessage.text))
                    }else{
                        adapter.add(ChatFromItem(chatMessage.text))
                    }
                }

                recycleView_comment_log.scrollToPosition(adapter.itemCount -1)
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }
            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }

    private fun performSendMessage(){
        val text = editText_comment_log.text.toString()
        val fromId = FirebaseAuth.getInstance().uid

        // val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val reference = FirebaseDatabase.getInstance().getReference("/user-comment/$shopId").push()
        val chatMessage = ChatMessage(text,fromId.toString())
        reference.setValue(chatMessage).addOnSuccessListener {
            Log.d(TAG, "Saved our chart message: ${reference.key}")
            editText_comment_log.text.clear()
            recycleView_comment_log.scrollToPosition(adapter.itemCount - 1)
        }
    }

}

class ChatFromItem(val text:String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_from_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.comment_from_row
    }
}


class ChatToItem(val text:String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text_to_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.comment_to_row
    }
}