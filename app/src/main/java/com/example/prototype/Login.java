package com.example.prototype;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.username_login);
        EditText password = findViewById(R.id.password_login);
        Button login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(v -> login(username.getText().toString(), password.getText().toString()));

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
    }

    private void showAlert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this)
                .setTitle("Successful Login")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, (parseUser, e) -> {
            if (parseUser != null) {
                showAlert("Welcome back " + username + " !");
            } else {
                ParseUser.logOut();
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /////////////////////////////////////////////////////
    // EXAMPLE - CREATE PARSE OBJECT AND SAVE TO SERVER//
    /////////////////////////////////////////////////////
    public void createObject() {
        ParseObject entity = new ParseObject("B4aVehicle");

        entity.put("name", "This is a test");
        entity.put("price", 999);
        entity.put("color", "Green");

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        entity.saveInBackground(e -> {
            // Here you can handle errors, if thrown. Otherwise, "e" should be null
        });
    }
}