package com.example.a48900.ledsistem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a48900.ledsistem.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

        TextView mtvSkor2,mtvSoal2,tvSoal;
        //ImageView mivGambar;
        EditText medtJawaban;
        Button mbtnSubmit2;
        int x=0;
        int arr;
        int skor;
        String jawaban;

        SoalFreetext essay = new SoalFreetext();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            tvSoal = (TextView) findViewById(R.id.tvSoal);
           // mtvSoal2 = (TextView) findViewById(R.id.tvSoal2);
           // mivGambar = (ImageView) findViewById(R.id.ivGambar);
            medtJawaban = (EditText) findViewById(R.id.edtJawaban);
            mbtnSubmit2 = (Button) findViewById(R.id.btnSubmit2);

            setKonten();

            mbtnSubmit2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cekJawaban();
                }
            });
        }

        public void setKonten(){
            medtJawaban.setText(null);
            arr = essay.pertanyaan.length;
            if(x >= arr){ //jika nilai x melebihi nilai arr(panjang array) maka akan pindah activity (kuis selesai)
                String jumlahSkor = String.valueOf(skor);	//menjadikan skor menjadi string
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                //waktu pindah activity, sekalian membawa nilai jumlahSkor yang ditampung dengan inisial skorAkhir2
                //singkatnya skorAkhir2 = jumlahSkor
                //jika masih belum jelas silahkan bertanya
                i.putExtra("skorAkhir2",jumlahSkor);
                i.putExtra("activity","Essay");
                startActivity(i);
            }else{
                //setting text dengan mengambil text dari method getter di kelas SoalEssay
//                mtvSoal2.setText(essay.getPertanyaan(x));
//               // ubahGambar();
//                jawaban = essay.getJawabanBenar(x);
            }
            x++;
        }

//        public void ubahGambar(){
//            Resources res = getResources();
//            String mPhoto = essay.getStringGambar(x);
//            int resID = res.getIdentifier(mPhoto, "drawable", getPackageName());
//            Drawable drawable = res.getDrawable(resID);
//            mivGambar.setImageDrawable(drawable);
//        }

        public void cekJawaban(){
            if(!medtJawaban.getText().toString().isEmpty()){ //jika edit text TIDAK kosong
                //jika text yang tertulis di edit text tsb = nilai dari var jawaban
                if(medtJawaban.getText().toString().equalsIgnoreCase(jawaban)){
                    skor = skor + 10;
                    mtvSkor2.setText(""+skor);	//mtvSkor2 diset nilainya = var skor
                    Toast.makeText(this, "Jawaban Benar", Toast.LENGTH_SHORT).show(); //keluar notifikasi "Jawaban Benar"
                    setKonten(); //update next konten
                }else{
                    mtvSkor2.setText(""+skor);
                    Toast.makeText(this, "Jawaban Salah", Toast.LENGTH_SHORT).show();
                    setKonten();
                }
            }else{
                Toast.makeText(this, "Silahkan pilih jawaban dulu!", Toast.LENGTH_SHORT).show();
            }
        }

        //ini adalah method bawaan dari Android Studio
        //fungsi : memberi aksi ketika tombol back pada hp diklik
        public void onBackPressed(){
            Toast.makeText(this, "Selesaikan kuis", Toast.LENGTH_SHORT).show();
            //jadi yang awalnya klik tombol back maka akan kembali ke activity sebelumnya
            //kali ini ketika tombol back diklik maka
            //hanya muncul Toast
        }


//    private static final String TAG = MainActivity.class.getSimpleName();
//    private TextView txtDetails;
//    private EditText inputName, inputEmail;
//    private Button btnSave;
//    private DatabaseReference mFirebaseDatabase;
//    private FirebaseDatabase mFirebaseInstance;
//    private String userId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Displaying toolbar icon
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
//
//        txtDetails = (TextView) findViewById(R.id.txt_user);
//        inputName = (EditText) findViewById(R.id.name);
//        inputEmail = (EditText) findViewById(R.id.email);
//        btnSave = (Button) findViewById(R.id.btn_save);
//
//        mFirebaseInstance = FirebaseDatabase.getInstance();
//
//        // get reference to 'users' node
//        mFirebaseDatabase = mFirebaseInstance.getReference("users");
//
//        // store app title to 'app_title' node
//        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");
//
//        // app_title change listener
//        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e(TAG, "App title updated");
//
//                String appTitle = dataSnapshot.getValue(String.class);
//
//                // update toolbar title
//                getSupportActionBar().setTitle(appTitle);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.e(TAG, "Failed to read app title value.", error.toException());
//            }
//        });
//
//        // Save / update the user
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = inputName.getText().toString();
//                String email = inputEmail.getText().toString();
//
//                // Check for already existed userId
//                if (TextUtils.isEmpty(userId)) {
//                    createUser(name, email);
//                } else {
//                    updateUser(name, email);
//                }
//            }
//        });
//
//        toggleButton();
//    }
//
//    // Changing button text
//    private void toggleButton() {
//        if (TextUtils.isEmpty(userId)) {
//            btnSave.setText("Save");
//        } else {
//            btnSave.setText("Update");
//        }
//    }
//
//    /**
//     * Creating new user node under 'users'
//     */
//    private void createUser(String name, String email) {
//        // TODO
//        // In real apps this userId should be fetched
//        // by implementing firebase auth
//        if (TextUtils.isEmpty(userId)) {
//            userId = mFirebaseDatabase.push().getKey();
//        }
//
//        User user = new User(name, email);
//
//        mFirebaseDatabase.child(userId).setValue(user);
//
//        addUserChangeListener();
//    }
//
//    /**
//     * User data change listener
//     */
//    private void addUserChangeListener() {
//        // User data change listener
//        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//
//                // Check for null
//                if (user == null) {
//                    Log.e(TAG, "User data is null!");
//                    return;
//                }
//
//               // Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);
//
//                // Display newly updated name and email
//               // txtDetails.setText(user.name + ", " + user.email);
//
//                // clear edit text
//                inputEmail.setText("");
//                inputName.setText("");
//
//                toggleButton();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.e(TAG, "Failed to read user", error.toException());
//            }
//        });
//    }
//
//    private void updateUser(String name, String email) {
//        // updating the user via child nodes
//        if (!TextUtils.isEmpty(name))
//            mFirebaseDatabase.child(userId).child("name").setValue(name);
//
//        if (!TextUtils.isEmpty(email))
//            mFirebaseDatabase.child(userId).child("email").setValue(email);
//    }
}
