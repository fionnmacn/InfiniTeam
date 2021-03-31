package com.example.prototype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ShapeableImageView userImage = findViewById(R.id.profileButton);

        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseFile fileObject = currentUser.getParseFile("image");
        fileObject.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    userImage.setImageBitmap(bmp);
                }
                else {
                    Log.d("test", "Problem load image the data.");
                }
            }
        });
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openTasks(View view) {
        Intent intent = new Intent(this, Tasks.class);
        startActivity(intent);
    }

    public void openBreaks(View view) {
        Intent intent = new Intent(this, Hours.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, Products.class);
        startActivity(intent);
    }

    public void openNotices(View view) {
        Intent intent = new Intent(this, Notices.class);
        startActivity(intent);
    }
}