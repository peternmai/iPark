package ucsd.cse110fa16.group14.ipark;

import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 10/16/2016.
 */

// This is the map for user. It should allow them to view the map status but not change the status.
public class UserMapInteractive extends View {

    //Canvas
    Canvas cv;
    //different color states of parking spots
    Paint[] paints = new Paint[4];// green, yellow, red, white;

    //screen dimensions
    int screenHeight;
    int screenWidth;
    //determine the size of the rectangles
    int width;
    int height;
    int gap;

    //holds the index of the color of the rectangle
    int colorIndex = 0;
    //top left space's position
    int x_tl;
    int y_tl;

    int numSpaces = 80;

    //parking spaces
    Rect rectangle[] = new Rect[numSpaces];

    //map rectangles to color
    HashMap<Rect, Paint> recCol = new HashMap<Rect, Paint>();

    private void init(Context context) {


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;

        y_tl = screenHeight/25;
        x_tl = screenWidth/25;

        width = screenWidth /15;
        height = screenHeight /20;
        gap = screenWidth/ 50;
        setBackgroundColor(Color.LTGRAY);

        //initialize the paint colors
        paints[2] = new Paint();
        paints[2].setColor(Color.RED);
        paints[2].setStyle(Paint.Style.FILL);

        paints[1] = new Paint();
        paints[1].setColor(Color.GREEN);
        paints[1].setStyle(Paint.Style.FILL);

        paints[0] = new Paint();
        paints[0].setColor(Color.WHITE);
        paints[0].setStyle(Paint.Style.FILL);

        paints[3] = new Paint();
        paints[3].setColor(Color.YELLOW);
        paints[3].setStyle(Paint.Style.FILL);

        Random rand = new Random();

        //initialize the rectangles & map
        for(int i = 0; i < numSpaces; i++) {

            Rect r = new Rect();
            r.set(x_tl + gap, y_tl, x_tl + width, y_tl + height);
            rectangle[i] = r;

            x_tl += width + gap;

            //10 spaces per row
            if((i+1)%10== 0) {
                x_tl = screenWidth/25;
                y_tl += height + gap;

                //20 spaces per group
                if((i+1)%20 == 0) {
                    y_tl += 40;
                }
            }

            recCol.put(r, paints[ rand.nextInt(4) ]);
        }



    }


    public UserMapInteractive(Context context) {
        super(context);
        init(context);
    }

    public UserMapInteractive(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UserMapInteractive(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        cv = canvas;

        for(Map.Entry<Rect, Paint> r : recCol.entrySet()) {
            Rect key = r.getKey();
            Paint paint = r.getValue();

            canvas.drawRect(key, paint);
        }
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event) {
        // Do nothing for now

        return true;

    }

    void changeRectColor(Rect r) {

        Paint p = recCol.get(r);

        //index in array of paint
        int idx= 0;
        int newIdx;
        for(int i = 0; i < paints.length; i++) {
            if(paints[i] == p ) {
                idx = i;
            }
        }


        //bad attempt at hashing
        newIdx = (idx + 3) % 4;


        recCol.put(r, paints[newIdx]);
        invalidate();
    }

}
