package com.example.leonid.jetpack;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Objects.DataBaseManager;
import Objects.UserAdmin;

public class LoginActivity extends AppCompatActivity {

    final static String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    EditText et_email;
    EditText et_pass;
    Button sign_up;
    Button sign_in_butt;
    String password;
    String email;
    Boolean sign_in = true;
    String name;
byte bitmask_edit = 0;

    FirebaseUser user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
         Log.d(TAG,"OnCreate");
      //  FirebaseApp.initializeApp(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        mAuth = FirebaseAuth.getInstance();
        // mAuth.signOut();
        updateUI(mAuth.getCurrentUser());


        et_email = findViewById(R.id.edittext_email);
        et_pass = findViewById(R.id.edittext_pass);
       final EditText et_name = findViewById(R.id.edittext_name);
        sign_in_butt = findViewById(R.id.button_sign_in);
        sign_up = findViewById(R.id.button_sign_up);
        sign_up.setClickable(true);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sign_in)
                {
                    sign_in = false;
                    et_name.setVisibility(View.VISIBLE);
                    sign_up.setText("הכנס");
                    sign_in_butt.setText("הרשם");
                }
                else
                {
                    sign_in = true;
                    et_name.setVisibility(View.GONE);
                    sign_up.setText("הרשם");
                    sign_in_butt.setText("הכנס");
                }
            }
        });



        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email = editable.toString();
                bitmask_edit |= (1 << 0);
                if (email.equals(""))
                {
                    bitmask_edit &= ~(1 << 0);
                }
                sign_up.setClickable(bitmask_edit == 7);
            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name = editable.toString();
                bitmask_edit |= (1 << 1);
                if (name.equals(""))
                {
                    bitmask_edit &= ~(1 << 1);
                }
                sign_up.setClickable(bitmask_edit == 7);
            }
        });

        et_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
                bitmask_edit |= (1 << 2);
                if (password.equals(""))
                {
                    bitmask_edit &= ~(1 << 2);
                }
                sign_up.setClickable(bitmask_edit == 7);
            }
        });
        sign_in_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "אנא הכנס סיסמא", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sign_in)
                {
                    sign_in(email,password);
                }
                else
                {
                    sign_up(email, password);
                }

            }
        });


    }
    void sign_in (final String email,final String password)
    {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

    }

    public void sign_up(final String email,final String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success  email:" + email);
                            Toast.makeText(LoginActivity.this, "Authentication succeded.",
                                    Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();
                            Log.d(TAG,"name: " + name);
                            UserAdmin um = new UserAdmin(name,email);
                            um.setEnable_notification(true);
                            DataBaseManager dbm = new DataBaseManager();
                            dbm.writeUserAdmin(um);
                            updateUI(user);

                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed." +
                                            task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }

    void create_user (final String email,final String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success  email:" + email);
                            Toast.makeText(LoginActivity.this, "Authentication succeded.",
                                    Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();
                            Log.d(TAG,"name: " + name);
                            UserAdmin um = new UserAdmin(name,email);
                            um.setEnable_notification(true);
                            DataBaseManager dbm = new DataBaseManager();
                            dbm.writeUserAdmin(um);
                            updateUI(user);

                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed." +
                                    task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });

    }
    void updateUI(FirebaseUser user)
    {
        if (user == null)
        {
            return;
        }
        else
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }
}
