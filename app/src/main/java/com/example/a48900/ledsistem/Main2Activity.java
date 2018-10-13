package com.example.a48900.ledsistem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a48900.ledsistem.Model.PendingQuestion;
import com.example.a48900.ledsistem.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = Main2Activity.class.getSimpleName();
    private TextView txtDetails;
    private EditText txt_username, txt_password;
    private Button button_login;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    private Date date;
    String questionType;
    PendingQuestion pendingQuestion = new PendingQuestion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtDetails = (TextView) findViewById(R.id.txt_user);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        button_login = (Button) findViewById(R.id.button_login);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("LED Sistem Assesment");
        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");
                String appTitle = dataSnapshot.getValue(String.class);
                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // contoh kondisinya buat pindah layout
//                objectmapper.(allrespon, PendingQuestion.class);
//
//                pendingQuestion.getQuestionType(response.getquestioType);
//
//                if(pendingQuestion.getQuestionType().equals("freetext")){
//                    layoutfreetext
//                }

                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String date = getCurrentDate();

                //String answerType;
                if ("freetext".equals(questionType)) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("username", txt_username.getText().toString());
                    startActivity(intent);
                } else {
                    Intent i = new Intent(getApplicationContext(), Main3Activity.class);
                    i.putExtra("username", txt_username.getText().toString());
                    startActivity(i);
                }


                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(username, password);
                } else {
                    updateUser(username, password);
                }
            }
        });

        toggleButton();
    }

    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            button_login.setText("Login");

        } else {
            //button_login.setText("Update");
        }
    }
    public String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        return day + "/" + (month+1) + "/" + year;
    }
    /**
     * Creating new user node under 'users'
     */
    private void createUser(String username, String password) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

       // User user = new User(username, password);
        User user = new User(username,date);

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.username);
               // Log.e(TAG, "User data is changed!" + user.username + ", " + user.password);

                // Display newly updated name and email
              // txtDetails.setText(user.username + ", " + user.password);
                txtDetails.setText(user.username);
//
//                // clear edit text
                txt_username.setText("");
               // txt_password.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String username, String password) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(username))
            mFirebaseDatabase.child(userId).child("username").setValue(username);

        if (!TextUtils.isEmpty(password))
            mFirebaseDatabase.child(userId).child("password").setValue(password);
    }
}
