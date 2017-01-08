package org.ville.randombackgroundcolor;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
        backgroundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomizeColor(v);
            }
        });

        backgroundImageView.post(new Runnable() {
            @Override
            public void run() {
                backgroundBitmap = Bitmap.createBitmap(backgroundImageView.getWidth(), backgroundImageView.getHeight(), Bitmap.Config.ARGB_8888);
                backgroundImageView.setImageBitmap(backgroundBitmap);
            }
        });

        rnd = new Random();

        //TODO Show toast that tells the rgb value of a new background color

    }

    public void randomizeColor(View v)
    {
        int red = rnd.nextInt(256);
        int green = rnd.nextInt(256);
        int blue = rnd.nextInt(256);
        int color = Color.rgb(red, green, blue);

        backgroundBitmap.eraseColor(color);
        backgroundImageView.setImageBitmap(backgroundBitmap);
    }


}
