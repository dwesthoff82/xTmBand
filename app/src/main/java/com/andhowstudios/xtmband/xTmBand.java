package com.andhowstudios.xtmband;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class xTmBand extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_tm_band);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        char[] pictureTakeIcon = {0xf030};
        char[] pictureSelectIcon = {0xf03e};
        char[] pictureSaveIcon= {0xf0c7};
        TextView sp = (TextView) findViewById(R.id.savePicture);
        Typeface fontAwesomeTF = Typeface.createFromAsset(this.getAssets(), "fontawesome-webfont.ttf");
        sp.setTypeface(fontAwesomeTF);
        sp.setText(pictureSaveIcon, 0, 1);

        TextView selectPicture = (TextView) findViewById(R.id.selectPicture);
        selectPicture.setTypeface(fontAwesomeTF);
        selectPicture.setText(pictureSelectIcon,0,1);

        TextView takePicture = (TextView) findViewById(R.id.takePicture);
        takePicture.setTypeface(fontAwesomeTF);
        takePicture.setText(pictureTakeIcon,0,1);



        //String vy = "" + x;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
