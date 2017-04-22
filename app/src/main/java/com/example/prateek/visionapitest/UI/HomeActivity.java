package com.example.prateek.visionapitest.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.prateek.visionapitest.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button bChooseCamera, bChooseGallery;
    LinearLayout llJoy, llAnger, llSorrow, llSurprise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bChooseCamera = (Button) findViewById(R.id.bChooseCamera);
        bChooseGallery = (Button) findViewById(R.id.bChooseGallery);
        llJoy = (LinearLayout) findViewById(R.id.llJoy);
        llAnger = (LinearLayout) findViewById(R.id.llAnger);
        llSorrow = (LinearLayout) findViewById(R.id.llSorrow);
        llSurprise = (LinearLayout) findViewById(R.id.llSurprise);

        bChooseCamera.setOnClickListener(this);
        bChooseGallery.setOnClickListener(this);
        llJoy.setOnClickListener(this);
        llAnger.setOnClickListener(this);
        llSorrow.setOnClickListener(this);
        llSurprise.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bChooseCamera:
                Intent mainIntent1 = new Intent(HomeActivity.this, MainActivity.class);
                mainIntent1.putExtra("imageChoice", "Camera");
                startActivity(mainIntent1);
                break;

            case R.id.bChooseGallery:
                Intent mainIntent2 = new Intent(HomeActivity.this, MainActivity.class);
                mainIntent2.putExtra("imageChoice", "Gallery");
                startActivity(mainIntent2);
                break;

            case R.id.llJoy:
                break;

            case R.id.llAnger:
                break;

            case R.id.llSorrow:
                break;

            case R.id.llSurprise:
                break;
        }
    }
}
