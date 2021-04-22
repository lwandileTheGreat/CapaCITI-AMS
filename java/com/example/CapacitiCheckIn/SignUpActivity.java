package com.example.CapacitiCheckIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    private DatabaseSupporter3 database;

    private DatabaseSupporter db;
    private Button signIn, register;
    private EditText email, password, confirmPassword;
    private CheckBox agreementCheck;
    private TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialViews();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStart();
            }
        });

    }

    private void registerStart() {
        Log.d(TAG, "registration: started ");

        String user = email.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        String cnf_pwd = confirmPassword.getText().toString().trim();

        boolean result = database.validateEmail(user);

        if (result == true) {
            if (user.equals("")) {
                warning.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Please fill in the empty space/(s)", Toast.LENGTH_SHORT).show();
            }
            else if(pwd.length()<8){
                warning.setVisibility(View.VISIBLE);
                warning.setTextColor(Color.rgb(225,0,0));
                warning.setText("Easy, Password must be 8 or more digits!") ;
            }
            else if (pwd.length()>=8) {

                if (pwd.equals(cnf_pwd)) {
                    boolean check = db.checkEmail(user);
                    warning.setVisibility(View.GONE);
                    if (check == false) {
                        if (agreementCheck.isChecked()) {
                            long val = db.addUser(user, pwd);
                            if (val > 0) {
                                Toast.makeText(getApplicationContext(), "Registration complete", Toast.LENGTH_SHORT).show();
                                Intent intentBackToLogin = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intentBackToLogin);
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "You need to agree to the license agreement", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (check == true) {
                        Toast.makeText(getApplicationContext(), "The Email already exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (result == false){
            Toast.makeText(getApplicationContext(), "The Email hasn't been registered", Toast.LENGTH_SHORT).show();
        }
    }

    private void backToLogin(){
        Log.d(TAG, "Login: Started");

        Intent intentBackToLogin = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intentBackToLogin);
        Toast.makeText(getApplicationContext(), "You can login here", Toast.LENGTH_SHORT).show();
    }



    private void initialViews() {
        Log.d(TAG, "Registration page: ");

        db = new DatabaseSupporter(this);
        database = new DatabaseSupporter3(this);
        signIn  = findViewById(R.id.signInButton);
        register = findViewById(R.id.takeAttedenceBtn);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        agreementCheck = findViewById(R.id.check_remember);
        warning = findViewById(R.id.warning);
    }
}