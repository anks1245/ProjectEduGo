package com.android.example.projecthackathon.message

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.example.projecthackathon.R
import com.android.example.projecthackathon.databinding.FragmentMessageBinding
import com.android.example.projecthackathon.helper.Message
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class MessageFragment : Fragment() {

    private val DEFAULT_MSG_LENGTH_LIMIT = 1000
    private val RC_PHOTO_PICKER = 2

    private lateinit var mMessageListView: ListView
    private lateinit var mMessageAdapter: MessageAdapter
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mPhotoPickerButton: ImageButton
    private lateinit var mMessageEditText: EditText
    private lateinit var mSendButton: Button

    private lateinit var mUsername: String

    //Firebase instance variables
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mMessagesDatabaseReference: DatabaseReference
    private var mChildEventListener: ChildEventListener? = null

    private lateinit var mFirebaseStorage: FirebaseStorage
    private lateinit var mChatPhotosStorageReference: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentMessageBinding.inflate(inflater)

        mUsername = context?.getSharedPreferences("user", android.content.Context.MODE_PRIVATE)?.getString("userNameKey", null).toString()
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseStorage = FirebaseStorage.getInstance()

        mMessagesDatabaseReference = mFirebaseDatabase.reference.child("messages")
        mChatPhotosStorageReference = mFirebaseStorage.reference.child("chat_photos")

        // Initialize references to views


        // Initialize references to views
        mProgressBar = binding.progressBar
        mMessageListView = binding.messageListView
        mPhotoPickerButton = binding.photoPickerButton
        mMessageEditText = binding.messageEditText
        mSendButton = binding.sendButton

        // Initialize message ListView and its adapter
        val friendlyMessages: List<Message> = ArrayList<Message>()
        mMessageAdapter = MessageAdapter(requireContext(), R.layout.item_message, friendlyMessages)
        mMessageListView.adapter = mMessageAdapter

        // Initialize progress bar
        mProgressBar.visibility = ProgressBar.INVISIBLE

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/jpeg"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(
                Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER
            )
        }

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mSendButton.isEnabled = charSequence.toString().trim().isNotEmpty()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        mMessageEditText.filters =
            arrayOf<InputFilter>(LengthFilter(DEFAULT_MSG_LENGTH_LIMIT))

        // Send button sends a message and clears the EditText

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener {
            val friendlyMessage = Message(mMessageEditText.text.toString(), mUsername, null)
            mMessagesDatabaseReference.push().setValue(friendlyMessage)
            // Clear input box
            mMessageEditText.setText("")
        }

        attachDatabaseReadListener()
        return binding.root
    }


    private fun detachDatabaseReadListener() {
        mChildEventListener?.let { mMessagesDatabaseReference.removeEventListener(it) }
        mChildEventListener = null
    }

    private fun attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val friendlyMessage: Message? =
                        snapshot.getValue(Message::class.java)
                    mMessageAdapter.add(friendlyMessage)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onChildRemoved(snapshot: DataSnapshot) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            }
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener as ChildEventListener)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data!!.data
            val photoRef = mChatPhotosStorageReference.child(selectedImageUri!!.lastPathSegment!!)
            //upload
            val uploadTask = photoRef.putFile(selectedImageUri)
            uploadTask.addOnSuccessListener {
                photoRef.downloadUrl.addOnSuccessListener { uri ->
                    val friendlyMessage = Message(null, mUsername, uri.toString())
                    mMessagesDatabaseReference.push().setValue(friendlyMessage)
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Upload failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onPause() {
        super.onPause()
        detachDatabaseReadListener()
        mMessageAdapter.clear()
    }

    override fun onResume() {
        super.onResume()
        attachDatabaseReadListener()
    }
}