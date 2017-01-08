package org.ville.randombackgroundcolor;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView backgroundImageView;

    private Bitmap backgroundBitmap;

    private Random rnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundImageView = (ImageView) findViewById(R.id.background_color);

        backgroundImageView.post(new Runnable() {
            @Override
            public void run() {
                backgroundBitmap = Bitmap.createBitmap(backgroundImageView.getWidth(), backgroundImageView.getHeight(), Bitmap.Config.ARGB_8888);
                backgroundImageView.setImageBitmap(backgroundBitmap);
            }
        });

        rnd = new Random();

        //TODO Show toast that tells the rgb value of a new background color
        //TODO Confirm that user really wants to change the background
        //TODO User can restore the previous background

    }

    public void randomizeColor(View v) {
        int red = rnd.nextInt(256);
        int green = rnd.nextInt(256);
        int blue = rnd.nextInt(256);
        int color = Color.rgb(red, green, blue);

        backgroundBitmap.eraseColor(color);
        backgroundImageView.setImageBitmap(backgroundBitmap);
    }

    /** Changes the phone's background.
     */
    public void changePhoneBackground(View v) throws IOException {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        wallpaperManager.setBitmap(backgroundBitmap);
    }

    //there might be use for this later:
    //Intent intent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    //System.out.println(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0));
}
