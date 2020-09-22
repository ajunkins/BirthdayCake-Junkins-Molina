package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

public class CakeView extends SurfaceView {

    private CakeModel reference;

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint textPaint = new Paint();
    Paint balloonPaint = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 40.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;
    //initialize x and y touch values
    float touchedx = 0f;
    float touchedy = 0f;


    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFFC755B5);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(30f);
        balloonPaint.setColor(Color.BLUE);

        setBackgroundColor(Color.WHITE);  //better than black default

        this.reference = new CakeModel();

    }

    /**
     * writes the coordinates of where the screen was touched in the lower-right hand corner
     */
    public void setCoords(float xvalue, float yvalue){
        Log.d("touch", "CakeView was touched");
        touchedx = xvalue;
        touchedy = yvalue;
        this.invalidate();
    }

    public void drawCoords(Canvas canvas){
        if (touchedx != 0f && touchedy != 0f){
            canvas.drawText("Touched Coordinates: " + touchedx + ", " + touchedy, 1285.33f, 684.23f, textPaint);
        }
    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        if (!reference.hasCandles) { return; }

        canvas.drawRect(left, bottom - candleHeight + 50, left + candleWidth, bottom, candlePaint);

        if (reference.isLit == true){
            //draw the outer flame
            float flameCenterX = left + candleWidth/2;
            float flameCenterY = bottom - wickHeight - candleHeight + 50 - outerFlameRadius/3;
            canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

            //draw the inner flame
            flameCenterY += outerFlameRadius/3;
            canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);
        }

        //draw the wick
        float wickLeft = left + candleWidth/2 - wickWidth/2;
        float wickTop = bottom - wickHeight - candleHeight + 50;
        canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);

    }

    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */

    public void drawBalloon(Canvas canvas, float x, float y) {
        if (x==0 && y==0) {

        }
        else {
            canvas.drawRect(x - 7, y, x + 7, y + 120, wickPaint);
            canvas.drawCircle(x, y, 50, balloonPaint);
            Path tri = new Path();
            tri.moveTo(x - 40, y + 30);
            tri.lineTo(x + 40, y + 30);
            tri.lineTo(x, y + 80);
            tri.lineTo(x - 40, y + 30);
            canvas.drawPath(tri, balloonPaint);
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {

        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);


        //draw evenly spaced candles
        int numCandles = reference.numCandles;

        for (int i = 1; i <= numCandles; i++){
            drawCandle(canvas, cakeLeft + i*cakeWidth/(numCandles+1) - candleWidth/2, cakeTop);
        }

        //draw the balloon
        drawBalloon(canvas, reference.x, reference.y);

        //draw coordinate text
        drawCoords(canvas);

    }//onDraw

    public CakeModel getReference(){
        return this.reference;
    }

}//class CakeView

