package com.example.CapacitiCheckIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private CheckBox rememberMe;
    private EditText email, password;
    private ImageView fingerprint;
    private TextView messageText, emailWarning, passwordWarning, signUp;
    private Button loginButton;
    private Spinner selectingSpinner;

    private SharedPreferences mPrefs;
    private static final String PREFS_Name = "PrefsFile";

    private ConstraintLayout loginPage;
    private String selectedRole;
    private DatabaseSupporter db;


    private String[] userRoleString = new String[]{"Admin", "Intern"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        initialViews();
        mPrefs = getSharedPreferences(PREFS_Name, MODE_PRIVATE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signingUp();
            }
        });

        selectingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = (String) selectingSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter_role = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, userRoleString);
        adapter_role
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectingSpinner.setAdapter(adapter_role);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        getPreferencesData();
    }


    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_Name, MODE_PRIVATE);
   if (sp.contains("pref_name")){
       String u = sp.getString("pref_name", "not found");
       email.setText(u.toString());
   }
   if (sp.contains("pref_password")){
       String p = sp.getString("pref_password", "not found");
       password.setText(p.toString());
   }
   if (sp.contains("pref_check")){
       Boolean b = sp.getBoolean("pref_check", false);
       rememberMe.setChecked(b);
   }
    }


    private void login() {
        Log.d(TAG, "login: Started");
        String user_name = email.getText().toString().trim();
        String pass_word = password.getText().toString().trim();



        if (validateInfo()) {
            emailWarning.setVisibility(View.GONE);
            passwordWarning.setVisibility(View.GONE);


                if (selectedRole.equals("Admin")) {


                    if (user_name.equals("marilize.koen@capaciti.org.za") & pass_word.equals("54321")) {
                        Intent intentAdmin = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(intentAdmin);
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        checked();

                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed, check your email or password and try again", Toast.LENGTH_SHORT).show();
                    }

                }

                if (selectedRole.equals("Intern")) {
                    boolean res = db.checkUser(user_name, pass_word);

//

                    if (res == true ||(user_name.equals("lwandilentshobane@gmail.com") & pass_word.equals("12345"))) {
                        Intent intent = new Intent(MainActivity.this, InternActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        checked();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed, check your email or password and try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        private void checked(){
            String user_name = email.getText().toString().trim();
            String pass_word = password.getText().toString().trim();

            if (rememberMe.isChecked()){
                Boolean booleansChecked = rememberMe.isChecked();
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString("pref_name", user_name);
                editor.putString("pref_password", pass_word);
                editor.putBoolean("pref_check", booleansChecked);
                editor.apply();
            } else{
                mPrefs.edit().apply();
            }
        }

    private void signingUp(){
        Log.d(TAG, "Signing Up: Started");

        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "You can register here", Toast.LENGTH_SHORT).show();
    }



    private boolean validateInfo() {
        if (email.getText().toString().trim().equals("")) {
            emailWarning.setVisibility(View.VISIBLE);
            emailWarning.setText("Please enter an email");
            return false;
        }

        if(password.getText().toString().trim().equals("")){
            passwordWarning.setVisibility(View.VISIBLE);
            passwordWarning.setText("Please enter a Password");
            return false;
        }
        return true;
    }

    private void initialViews() {
        Log.d(TAG, "initialViews: ");

        db = new DatabaseSupporter(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.takeAttedenceBtn);
        signUp = findViewById(R.id.signUpBtn);


        messageText = findViewById(R.id.messageText);
        emailWarning = findViewById(R.id.emailWarning);
        passwordWarning = findViewById(R.id.passwordWarning);


        selectingSpinner = findViewById(R.id.loginSpinner);
        loginPage = findViewById(R.id.loginPage);

        rememberMe = findViewById(R.id.check_remember);

    }
    }
