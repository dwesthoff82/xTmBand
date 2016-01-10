package com.andhowstudios.xtmband;

import android.app.Application;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Bitmap;
import java.util.Date;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class xTmBand extends AppCompatActivity {

    final int SELECT_PHOTO = 99;
    final int TAKE_PHOTO = 999;
    ImageView mImageView;
    RelativeLayout mainLayout = null;
    View selectionRecatangle = null;
    boolean newSelection = true;
    String mCurrentPhotoPath;
    File photoFile = null;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_tm_band);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        char[] pictureTakeIcon = {0xf030};
        char[] pictureSelectIcon = {0xf03e};
        char[] pictureSaveIcon = {0xf0c7};


        mainLayout = (RelativeLayout) findViewById(R.id.rel);


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
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
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




                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));

                    }


                    startActivityForResult(takePictureIntent, TAKE_PHOTO);
                }

                return false;
            }


        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
           Uri  selectedImage = Uri.fromFile(photoFile);

            try {

                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                float width = yourSelectedImage.getWidth();
                float measuredWidth =  mImageView.getMeasuredWidth();
                float ratio = measuredWidth /width ;
                Bitmap scaled =  Bitmap.createScaledBitmap(yourSelectedImage,(int)measuredWidth,(int)(yourSelectedImage.getHeight()*ratio),false);
                mImageView.setImageBitmap(scaled);

            } catch (FileNotFoundException ex) {

                //Didnt find the file

            }



        } else if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            try {

                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                mImageView.setImageBitmap(yourSelectedImage);

            } catch (FileNotFoundException ex) {

                //Didnt find the file

            }



        }



        //If we have a good result regardless of the source
        if(resultCode == RESULT_OK){



            if(selectionRecatangle == null) {
                selectionRecatangle = new View(this);
                selectionRecatangle.setBackgroundResource(R.drawable.rectangle);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    //Step 1.
                    //Grab the intrinsic height/width of our image
                    BitmapDrawable bd = (BitmapDrawable)mImageView.getDrawable();
                    int intrinsicHeight = bd.getIntrinsicHeight();
                    int intrinsicWidth = bd.getIntrinsicWidth();

                    mImageView.getMeasuredHeight();
                    float[] f = new float[9];
                    mImageView.getImageMatrix().getValues(f);

                    // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
                    final float scaleX = f[Matrix.MSCALE_X];
                    final float scaleY = f[Matrix.MSCALE_Y];




                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)(310*scaleX),(int) (128*scaleY));


                    float leftPadding =   HelperMethods.convertDpToPixel(16, getApplicationContext());
                    float topPadding = HelperMethods.convertDpToPixel(50,getApplicationContext());

                  //  rect.setPadding((int)leftPadding, (int)topPadding, 0, 0);
                    if(newSelection)
                        mainLayout.addView(selectionRecatangle, lp);
                    selectionRecatangle.setLayoutParams(lp);
                    selectionRecatangle.setX(mImageView.getX() + leftPadding);
                    selectionRecatangle.setY(mImageView.getY() + topPadding);
                    newSelection = false;

                }
            });



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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "xTmBand Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.andhowstudios.xtmband/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }






    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "xTmBand Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.andhowstudios.xtmband/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
