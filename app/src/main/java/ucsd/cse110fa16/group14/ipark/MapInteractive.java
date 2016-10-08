package ucsd.cse110fa16.group14.ipark;

import android.content.Context;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jasonallen on 10/8/16.
 */


/*In calling view or activity:
*  MapInteractive mp;
*  mp = new MapInteractive(this);
*  setContentView(mp);*/

public class MapInteractive extends View {


    //Canvas
    Canvas cv;
    //different color states of parking spots
    Paint[] paints = new Paint[4];// green, yellow, red, white;

    //determine the size of the rectangles
    int width = 100;
    int height = 180;
    int gap = 10;

    //holds the index of the color of the rectangle
    int colorIndex = 0;
    //top left space's position
    int x_tl = 150;
    int y_tl = 150;

    int numSpaces = 80;

    //parking spaces
    Rect rectangle[] = new Rect[numSpaces];

    //map rectangles to color
    HashMap<Rect, Paint> recCol = new HashMap<Rect, Paint>();

    public MapInteractive(Context context) {
        super(context);

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

        //initialize the rectangles & map
        for(int i = 0; i < numSpaces; i++) {

            Rect r = new Rect();
            r.set(x_tl + gap, y_tl, x_tl + width, y_tl + height);
            rectangle[i] = r;

            x_tl += width + gap;

            //10 spaces per row
            if((i+1)%10== 0) {
                x_tl = 150;
                y_tl += height + gap;

                //20 spaces per group
                if((i+1)%20 == 0) {
                    y_tl += 40;
                }
            }

            recCol.put(r, paints[0]);
        }



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

        // for( Rect r : rectangle)
        //   canvas.drawRect(r, white);
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                for(Rect rect : rectangle){

                    if(rect.contains((int)x,(int)y)){
                        System.out.println("Touched Rectangle, change color");
                        changeRectColor(rect);
                    }
                }

                break;

            case MotionEvent.ACTION_UP:

                System.out.println("Touched up");
                break;
        }

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
