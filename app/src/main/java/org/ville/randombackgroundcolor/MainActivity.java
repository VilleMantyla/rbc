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

        // TODO Create a bitmap which size matches the size of ImageView component (you may have to do this outside onCreate)
        backgroundBitmap = Bitmap.createBitmap(/*backgroundImageView.getWidth()*/ 200, 200 /*backgroundImageView.getHeight()*/, Bitmap.Config.ARGB_8888);
        backgroundBitmap.eraseColor(Color.BLACK);

        backgroundImageView.setImageBitmap(backgroundBitmap);

        rnd = new Random();

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
