package com.example.InfiniTeam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    EditText passwordField1;
    EditText passwordField2;
    Button submit;
    String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        passwordField1 = findViewById(R.id.password1);
        passwordField2 = findViewById(R.id.password2);
        submit = findViewById(R.id.change_password_submit);

        ParseUser currentUser = ParseUser.getCurrentUser();
        String currentUserString = currentUser.getUsername();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordField1.getText().toString().equals(passwordField2.getText().toString())) {
                    password = passwordField1.getText().toString();
                    Log.d("PASSWORD", password);

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("password", password);
                    params.put("username", currentUserString);
                    ParseCloud.callFunctionInBackground("resetPassword", params, new FunctionCallback<String>() {
                        @Override
                        public void done(String response, ParseException e) {
                            if (e == null) {
                                Snackbar.make(v, "Password changed successfully.", Snackbar.LENGTH_LONG).show();
                                ParseUser.logOut();
                                Intent sendToLogin = new Intent(getApplicationContext(), Login.class);
//                                previousActivity.putExtra("username",currentUserString);
                                startActivity(sendToLogin);
                                finish();
                            } else {
                                Log.d("RESPONSE", e.getMessage());
                                Snackbar.make(v, "Error " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Snackbar.make(v, "Passwords do not match. Please try again.", Snackbar.LENGTH_LONG).show();
                }
//                currentUser.setPassword("new_password");
//                currentUser.saveInBackground();
//                notice.saveInBackground();
            }
        });
    }
}
