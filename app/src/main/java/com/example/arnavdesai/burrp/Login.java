package com.example.arnavdesai.burrp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.R.attr.name;
import static android.R.attr.password;
import static android.R.attr.preferenceCategoryStyle;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText nameEdit,passEdit;
    private Button LoginButtOwner;
    private Button LoginButtStudent;
    private FirebaseAuth firebaseAuth;
    private TextView forgetPassword;
    private DatabaseReference databaseReferenceOwner = FirebaseDatabase.getInstance().getReference("Owner");
    private DatabaseReference databaseReferenceStudent = FirebaseDatabase.getInstance().getReference("Student");
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButtOwner=(Button) findViewById(R.id.LoginButtonOwner);
        LoginButtStudent = (Button) findViewById(R.id.LoginButtonStudent);
        nameEdit=(EditText) findViewById(R.id.LoginNameEdit);
        passEdit=(EditText) findViewById(R.id.LoginPasswordEdit);
        forgetPassword=(TextView)findViewById(R.id.forgetPassword);
        firebaseAuth=FirebaseAuth.getInstance();

        preferences=getApplicationContext().getSharedPreferences("MyPref",0);
        editor=preferences.edit();

        if (!preferences.getBoolean("firstime",false))
        {
            String name=preferences.getString("email","false");
            String password=preferences.getString("password","false");
            Log.d("Burrpy",name);
            Log.d("Burrpy",password);

            if(name!=null && password!=null) {
                    if (preferences.getString("type","false").equals("owner")) {
                        firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    uid = firebaseUser.getUid();

                                    Toast.makeText(Login.this, "Login successful ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, OwnerProfile.class);
                                    intent.putExtra("uid", uid);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                    if(preferences.getString("type","false").equals("student"))
                    {
                        firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        uid = firebaseUser.getUid();
                                        Toast.makeText(Login.this, "Login successful ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, StudentOptions.class);
                                        startActivity(intent);
                                }
                            }
                        });
                    }
            }
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 50);
            calendar.set(Calendar.SECOND, 0);

            if (preferences.getString("type","false").equals("owner")) {
                Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }

            if (preferences.getString("type","false").equals("student")) {
                Intent intent = new Intent(getApplicationContext(), Notification_Student.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }

            editor.putBoolean("firsttime",true);
            editor.apply();
        }

        LoginButtOwner.setOnClickListener(this);
        LoginButtStudent.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
    }

    public void LoginUserOwner(String name, String password) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password))
            Toast.makeText(Login.this,"email or password cannot be empty.",Toast.LENGTH_LONG).show();
        else if(password.length()<6)
            Toast.makeText(Login.this,"Password should contain at least 6 characters..",Toast.LENGTH_LONG).show();
        else if (!name.contains("@") && !name.contains("."))
            Toast.makeText(Login.this,"Please enter valid email.",Toast.LENGTH_LONG).show();
        else
        {

            final ProgressDialog progressDialog=ProgressDialog.show(Login.this,"Logging in...", "Please Wait",true);

            firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        editor.putString("email",nameEdit.getText().toString().trim());
                        editor.putString("password",passEdit.getText().toString().trim());
                        editor.putString("type","owner");
                        editor.apply();
                        progressDialog.dismiss();
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        final String uid = firebaseUser.getUid();
                        databaseReferenceOwner.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(uid))
                                {
                                    Toast.makeText(Login.this, "Login successful ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, OwnerProfile.class);
                                    intent.putExtra("uid",uid);
                                    finish();
                                    startActivity(intent);
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this,"You are not a mess owner. Please login as a student",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Login unsuccessful. Please register yourself.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View view)
    {
        if(view == LoginButtOwner)
        {
            LoginUserOwner(nameEdit.getText().toString().trim(), passEdit.getText().toString().trim());
        }

        if(view == LoginButtStudent)
        {
            LoginUserStudent(nameEdit.getText().toString().trim(), passEdit.getText().toString().trim());
        }
        if(view==forgetPassword){
            Intent intent=new Intent(Login.this,forgetPassword.class);
            startActivity(intent);
        }
    }

    private void LoginUserStudent(String name, String password) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password))
            Toast.makeText(Login.this,"email or password cannot be empty.",Toast.LENGTH_LONG).show();
        else if(password.length()<6)
            Toast.makeText(Login.this,"Password should contain at least 6 characters..",Toast.LENGTH_LONG).show();
        else if (!name.contains("@") && !name.contains("."))
            Toast.makeText(Login.this,"Please enter valid email.",Toast.LENGTH_LONG).show();
        else
        {
            final ProgressDialog progressDialog=ProgressDialog.show(Login.this,"Logging in...", "Please Wait",true);

            firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        editor.putString("email",nameEdit.getText().toString().trim());
                        editor.putString("password",passEdit.getText().toString().trim());
                        editor.putString("type","student");
                        editor.apply();
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        final String uid = firebaseUser.getUid();
                        databaseReferenceStudent.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(uid))
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, "Login successful ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, StudentOptions.class);
                                    finish();
                                    startActivity(intent);
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this,"You are not a Student. Please login as a Owner",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Login unsuccessful. Please register yourself. ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}