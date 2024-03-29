package com.example.InfiniTeam.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.InfiniTeam.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class Profile extends AppCompatActivity {

    String user = null;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        user = data.getStringExtra("username");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ShapeableImageView image = findViewById(R.id.profiler);
        TextView username = findViewById(R.id.username);
        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.email);
        Button change_password = findViewById(R.id.change_password);

//        image.setShapeAppearanceModel(image.getShapeAppearanceModel()
//                .toBuilder()
//                .setAllCorners(CornerFamily.ROUNDED, 20)
//                .build());

        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile fileObject = currentUser.getParseFile("image");
        fileObject.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    image.setImageBitmap(bmp);
                }
                else {
                    Log.d("test", "Problem load image the data.");
                }
            }
        });

        String currentUsername = currentUser.getUsername();
        username.setText(currentUsername);
        String firstName = currentUser.getString("firstName");
        String surname = currentUser.getString("surname");
        String currentName = firstName + " " + surname;
        name.setText(currentName);
        String currentEmail = currentUser.getEmail();
        email.setText(currentEmail);

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ChangePassword.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
