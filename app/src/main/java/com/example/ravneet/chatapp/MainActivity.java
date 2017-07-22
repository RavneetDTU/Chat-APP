package com.example.ravneet.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ListView messageListView;
    private MessageAdapter messageAdapter;
    private ImageView iv_pickImage;
    private Button btn_send;
    private EditText et_Message;

    public static final int RC_PHOTO_PICKER = 2;
    private String mUsername;

    // This is the Entry Point Of Database
    private FirebaseDatabase firebaseDatabase;
    // This is a class that is Referance to a Specific Part Of Database...Eg..Here Is will be Referencing Message portion of Database
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = "anonymous";

        firebaseDatabase = FirebaseDatabase.getInstance();
        // Here we got Instance of the class.....Firebase is Completly Static so can be Used anywhere inProject..Just give a reference...This will be the main access point of our database
        databaseReference = firebaseDatabase.getReference().child("message");
        // Now using above access ponit we get a referance to specfic part of database.......firebaseDatabase.getReference()..this is getting access to rootnode
        //...child("message") and here we specificly saying refering to message portion of Database

        messageListView = (ListView) findViewById(R.id.messageListView);
        iv_pickImage = (ImageView) findViewById(R.id.iv_imageMessage);
        btn_send = (Button) findViewById(R.id.sendButton);
        et_Message = (EditText) findViewById(R.id.messageEditText);

//        iv_pickImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.setType("image/*");
//                i.putExtra(i.EXTRA_LOCAL_ONLY,true);
//                startActivityForResult(i.createChooser(i,"Complete Action Using"),RC_PHOTO_PICKER);
//            }
//        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message message = new Message(et_Message.getText().toString(),mUsername,"");
                // Here we Created new Object......This object has all the keys that we’ll store as a message in the realtime database
                databaseReference.push().setValue(message);
                // Here we are sending messge to Our database.
                et_Message.setText("");
            }
        });

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //This method is called WhenEver a new Message is Inserted into the Message List.....It also triggered when every message in the list when the listner is first attached
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // This is called when the contents of Existing Message get Changed.
               Message thismessage = dataSnapshot.getValue(Message.class);// This is to get Data of new Message
                // Here we Are storing data of Message in Our Similar Node
                messageAdapter.add(thismessage);
                // Here we are sending Our Messge To our Own Adapter.....And this will Display in our ListView
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Called When an Existing message is deleted
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // Called when Any Message Changes Position in list
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // It indicates that some Kind of Error get Occured when we are trying to change data
                // Generally it is Called When you dont have access to Read data
            }
        };
        databaseReference.addChildEventListener(childEventListener);
        // Here we Are definig what will happen to Data in Current Referance


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater ml = getMenuInflater();
        ml.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
