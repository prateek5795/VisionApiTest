package com.example.prateek.visionapitest.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prateek.visionapitest.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivImage;
    Button bProcess, bCamera, bGallery, bTemp;

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        bCamera.setOnClickListener(this);
        bGallery.setOnClickListener(this);
        bProcess.setOnClickListener(this);
        bTemp.setOnClickListener(this);
    }

    private void initialize() {
        ivImage = (ImageView) findViewById(R.id.ivImage);
        bProcess = (Button) findViewById(R.id.bProcess);
        bCamera = (Button) findViewById(R.id.bCamera);
        bGallery = (Button) findViewById(R.id.bGallery);
        bTemp = (Button) findViewById(R.id.bTemp);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivImage.setImageBitmap(photo);
        }

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ivImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bCamera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.bTemp:
                Intent tabIntent = new Intent(MainActivity.this, TabData.class);
                startActivity(tabIntent);
                break;

            case R.id.bGallery:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
                break;

            case R.id.bProcess:

                if (ivImage.getDrawable() != null) {
                    final Bitmap myBitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();

                    final Paint rectPaint = new Paint();
                    rectPaint.setStrokeWidth(5);
                    rectPaint.setColor(Color.RED);
                    rectPaint.setStyle(Paint.Style.STROKE);

                    final Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
                    final Canvas canvas = new Canvas(tempBitmap);
                    canvas.drawBitmap(myBitmap, 0, 0, null);

                    FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                            .setTrackingEnabled(false)
                            .setLandmarkType(FaceDetector.FAST_MODE)
                            .setProminentFaceOnly(true)
                            .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                            .build();

                    if (!faceDetector.isOperational()) {
                        Toast.makeText(MainActivity.this, "Facedetector could not be set up", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                    SparseArray<Face> sparseArray = faceDetector.detect(frame);
                    float smileProb = 0;

                    for (int i = 0; i < sparseArray.size(); ++i) {
                        Face face = sparseArray.valueAt(i);
                        float x1 = face.getPosition().x;
                        float y1 = face.getPosition().y;
                        float x2 = x1 + face.getWidth();
                        float y2 = y1 + face.getHeight();

                        RectF rectF = new RectF(x1, y1, x2, y2);
                        canvas.drawRoundRect(rectF, 2, 2, rectPaint);
                        smileProb = face.getIsSmilingProbability();
                    }
                    ivImage.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
                    String s;
                    if (smileProb < 0.4)
                        s = "Not Smiling";
                    else if (smileProb > 0.4 && smileProb < 0.8)
                        s = "Probably Smiling";
                    else
                        s = "Smiley Face";
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
        }
    }
}
