package org.ville.randombackgroundcolor;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView backgroundImageView;

    private Bitmap backgroundBitmap;

    private BitmapDrawable bdrw;

    private Canvas canvas;

    private int[] displaySize;

    // Radiobuttons
    private boolean sierpinski;
    private boolean lines;
    private boolean colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundImageView = (ImageView) findViewById(R.id.background_color);

        /*backgroundImageView.post(new Runnable() {
            @Override
            public void run() {
                backgroundBitmap = Bitmap.createBitmap(backgroundImageView.getWidth(), backgroundImageView.getHeight(), Bitmap.Config.ARGB_8888);
                backgroundImageView.setImageBitmap(backgroundBitmap);
                //Let's set first background color
                backgroundBitmap.eraseColor(Color.MAGENTA);
            }
        });*/

        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(displaySize);
        backgroundBitmap = Bitmap.createBitmap(displaySize.x, displaySize.y, Bitmap.Config.ARGB_8888);

        int xxx = displaySize.x;
        int yyy = displaySize.y;

        this.displaySize = new int[]{xxx, yyy};

        bdrw = new BitmapDrawable(getResources(), backgroundBitmap);

        backgroundImageView.setBackground(bdrw);
        backgroundBitmap.eraseColor(Color.WHITE);

        canvas = new Canvas(backgroundBitmap);

        //TODO Show toast that tells background has changed
        //TODO Confirm that user really wants to change the background
        //TODO User can restore the previous background


    }

    public void randomizeColor(View v) {
        int red = (int)Math.round(Math.random()*255);
        int green = (int)Math.round(Math.random()*255);
        int blue = (int)Math.round(Math.random()*255);
        int color = Color.rgb(red, green, blue);

        backgroundBitmap.eraseColor(color);

        bdrw = new BitmapDrawable(getResources(), backgroundBitmap);
        backgroundImageView.setBackground(bdrw);

    }

    public int randomColorPure() {
        int[] rgb = new int[3];
        int shadeColor = (int)Math.round(Math.random()*2);
        int zeroColor;
        if(shadeColor == 2)
        {
            zeroColor = (int)Math.round(Math.random()*1);
        }
        else if(shadeColor == 1)
        {
            zeroColor = (int)Math.round(Math.random()*1)*2;
        }
        else
        {
            zeroColor = (int)Math.round(Math.random()*1)+1;
        }

        rgb[shadeColor] = 255;
        rgb[zeroColor] = (int)Math.round(Math.random()*110)+20; //or just 0
        rgb[3-shadeColor-zeroColor] = (int)Math.round(Math.random()*255);

        int color = Color.rgb(rgb[0], rgb[1], rgb[2]);

        return color;
    }

    /** Changes the phone's background.
     */
    public void changePhoneBackground(View v) throws IOException {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        wallpaperManager.setBitmap(backgroundBitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Draws lines randomly.
     * @param width The width of the line.
     * @param amount The amount of lines.
     */
    public void drawLines(int width, int amount)
    {
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(width);

        int maxX = backgroundBitmap.getWidth();
        int maxY = backgroundBitmap.getHeight();

        //To make sure the line starts from the side when lines's width is more than 1 pixel
        int lineOffset = Math.round(width/2);

        for(int i=0; i<amount; i++){

            //Random staring point for the line along the axis
            int xStart = (int)Math.round(Math.random()*1);
            int yStart = 1-xStart;
            if(xStart == 0)
            {
                yStart = (int)Math.round(Math.random()*maxY)-lineOffset;
                xStart -= lineOffset;
            }
            else
            {
                xStart = (int)Math.round(Math.random()*maxX)-lineOffset;
                yStart -= lineOffset;
            }

            //Random ending point for the line along the axis
            int xEnd = (int)Math.round(Math.random()*1);
            int yEnd;
            if(xEnd == 0)
            {
                yEnd = (int)Math.round(Math.random()*maxY)+lineOffset;
                xEnd = maxX+lineOffset;

            }
            else
            {
                xEnd = (int)Math.round(Math.random()*maxX)+lineOffset;
                yEnd = maxY+lineOffset;
            }

            //int xStart = (int)Math.round(Math.random()*maxX);
            //int yStart = (int)Math.round(Math.random()*maxY);
            //int xEnd = (int)Math.round(Math.random()*maxX);
            //int yEnd = (int)Math.round(Math.random()*maxY);

            canvas.drawLine(xStart, yStart, xEnd, yEnd, paint);
            //System.out.println("Start: " + xStart + "," + yStart + " End: " + xEnd + "," + yEnd);
        }

        //canvas.drawLine(0.0f, 0.0f, backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), paint);
    }

    /**
     * Gives screen coordinates for equilateral triangle's vertices according to the centroid.
     * @param centroid
     * @param sideLength
     * @return
     */
    public float[] coordinatesForEquilateralTriangle(float[] centroid, float sideLength){
        float height = (float)(sideLength*((Math.sqrt(3.0))/2.0));
        float[] peak = {centroid[0], centroid[1]-(height*2)/3};
        float[] left = {centroid[0]-sideLength/2, centroid[1]+height/3};
        float[] right = {centroid[0]+sideLength/2, centroid[1]+height/3};

        return new float[]{peak[0], peak[1], left[0], left[1], right[0], right[1]};
    }

    public void sierpinskiTriangle(EquilateralTriangle triangle, int bgColor, int trColor) {
        drawTriangle(triangle, trColor);

        if(triangle.getSideLength()<200.0) {
            return;
        }
        else {
            EquilateralTriangle[] triangles;
            triangles = divideTriangle(triangle);
            sierpinskiTriangle(triangles[1], bgColor, trColor);
            sierpinskiTriangle(triangles[2], bgColor, trColor);
            sierpinskiTriangle(triangles[3], bgColor, trColor);
            destroyTriangle(triangles[0], bgColor);
        }
    }

    //first triangle in the array is the middle triangle
    public EquilateralTriangle[] divideTriangle(EquilateralTriangle tr) {
        double newSideLength = tr.getSideLength()/2;
        int[] peak = tr.getPeak();
        double trHeight = tr.getHeight();
        EquilateralTriangle top    = new EquilateralTriangle(peak, newSideLength);
        EquilateralTriangle left = new EquilateralTriangle(new int[]{peak[0]-(int)(newSideLength/2), peak[1]+(int)(trHeight/2)}, newSideLength);
        EquilateralTriangle center = new EquilateralTriangle(createUpsideDownTriangle(new int[]{peak[0], (int)(peak[1]+trHeight)}, newSideLength));
        EquilateralTriangle right = new EquilateralTriangle(new int[]{peak[0]+(int)(newSideLength/2), peak[1]+(int)(trHeight/2)}, newSideLength);

        return new EquilateralTriangle[]{center, top,left, right};
    }

    public int[] createUpsideDownTriangle(int[] p, double sideLength) {
        double height = (float)(sideLength*(Math.sqrt(3.0)/2));
        int[] peak = {p[0], p[1]};
        int[] left = {(int)(p[0]-sideLength/2), (int)(p[1]-height)};
        int[] right = {(int)(p[0]+sideLength/2), (int)(p[1]-height)};
        return new int[]{peak[0], peak[1], left[0], left[1], right[0], right[1]};
    }

    public void destroyTriangle(EquilateralTriangle t, int color) {
        drawTriangle(t, color);
    }

    public void drawTriangle(EquilateralTriangle triangle, int col) {
        float[] coordinates = new float[triangle.getVertex().length];
        for(int i=0; i<coordinates.length; i++) {
            coordinates[i] = (float)triangle.getVertex()[i];
        }

        int[] color = new int[]{col, col, col, col, col, col};
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, coordinates.length, coordinates, 0, null, 0, color, 0, null, 0, 0, paint);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_sierpinski:
                if (checked)
                    sierpinski = true;
                lines = colors = false;
                break;
            case R.id.radio_lines:
                if (checked)
                    lines = true;
                sierpinski = colors = false;
                break;
            case R.id.radio_color:
                if(checked)
                    colors = true;
                sierpinski = lines = false;
                break;
        }
    }

    public void createNewBackground(View view) {
        if(sierpinski) {
            //backgroundBitmap.eraseColor(Color.BLACK);
            int bgColor = Color.BLACK;
            int trColor = randomColorPure();
            backgroundBitmap.eraseColor(bgColor);
            double ratio = 4.0/5.0;
            float[] coordinates = coordinatesForEquilateralTriangle(new float[]{(float) displaySize[0]/2f, (float) displaySize[1]/2f}, (float)(displaySize[0]*ratio));
            EquilateralTriangle triangle = new EquilateralTriangle(coordinates);
            sierpinskiTriangle(triangle, bgColor, trColor);
            bdrw = new BitmapDrawable(getResources(), backgroundBitmap);
            backgroundImageView.setBackground(bdrw);
        }
        else if(lines) {
            backgroundBitmap.eraseColor(randomColorPure());
            bdrw = new BitmapDrawable(getResources(), backgroundBitmap);
            backgroundImageView.setBackground(bdrw);
            drawLines(8, 7);
            bdrw = new BitmapDrawable(getResources(), backgroundBitmap);
            backgroundImageView.setBackground(bdrw);
        }
        else if(colors) {
            backgroundBitmap.eraseColor(randomColorPure());
            bdrw = new BitmapDrawable(getResources(), backgroundBitmap);
            backgroundImageView.setBackground(bdrw);
        }
        else {
            backgroundBitmap.eraseColor(randomColorPure());
            bdrw = new BitmapDrawable(getResources(), backgroundBitmap);
            backgroundImageView.setBackground(bdrw);
        }
    }

    //there might be use for this later:
    //Intent intent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    //System.out.println(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0));
}
