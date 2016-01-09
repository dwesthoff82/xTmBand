package com.andhowstudios.xtmband;

import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class xTmBand extends AppCompatActivity {

    final int SELECT_PHOTO = 99;
    final int TAKE_PHOTO = 999;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_tm_band);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        char[] pictureTakeIcon = {0xf030};
        char[] pictureSelectIcon = {0xf03e};
        char[] pictureSaveIcon= {0xf0c7};



        mImageView = (ImageView) findViewById(R.id.imageView);
        TextView sp = (TextView) findViewById(R.id.savePicture);
        Typeface fontAwesomeTF = Typeface.createFromAsset(this.getAssets(), "fontawesome-webfont.ttf");
        sp.setTypeface(fontAwesomeTF);
        sp.setText(pictureSaveIcon, 0, 1);

        TextView selectPicture = (TextView) findViewById(R.id.selectPicture);
        selectPicture.setTypeface(fontAwesomeTF);
        selectPicture.setText(pictureSelectIcon, 0, 1);

        selectPicture.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //We are going to launch the intent to pick an image
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);

                return false;
            }


        });


        TextView takePicture = (TextView) findViewById(R.id.takePicture);
        takePicture.setTypeface(fontAwesomeTF);
        takePicture.setText(pictureTakeIcon, 0, 1);

        takePicture.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //We are going to launch the intent to pick an image
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, TAKE_PHOTO);
                }

                return false;
            }


        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();



            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }else if(requestCode == SELECT_PHOTO && resultCode == RESULT_OK){
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                mImageView.setImageBitmap((yourSelectedImage));

            }catch(FileNotFoundException ex){

                //Didnt find the file

            }
        }


        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_x_tm_band, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
