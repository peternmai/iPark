package ucsd.cse110fa16.group14.ipark;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 10/16/2016.
 * Part of these codes were made with reference to Android/Java's canvas API
 * Source: https://developer.android.com/reference/android/graphics/Canvas.html
 */


// This is the map for user. It should allow them to view the map status but not change the status.
public class UserMapInteractive extends View {

    private static int[] parkingLotStatus;

    //different color states of parking spots
    Paint[] paints = new Paint[4];// green, yellow, red, white;

    //screen dimensions
    int screenHeight;
    int screenWidth;
    //determine the size of the rectangles
    int width;
    int height;
    int gap;

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

        y_tl = screenHeight / 25;
        x_tl = screenWidth / 25;

        width = screenWidth / 15;
        height = screenHeight / 20;
        gap = screenWidth / 50;
        setBackgroundColor(Color.LTGRAY);

        //initialize the paint colors
        paints[iLink.OWNER_RESERVED] = new Paint();
        paints[iLink.OWNER_RESERVED].setColor(Color.WHITE);
        paints[iLink.OWNER_RESERVED].setStyle(Paint.Style.FILL);

        paints[iLink.OCCUPIED] = new Paint();
        paints[iLink.OCCUPIED].setColor(Color.YELLOW);
        paints[iLink.OCCUPIED].setStyle(Paint.Style.FILL);

        paints[iLink.AVAILABLE] = new Paint();
        paints[iLink.AVAILABLE].setColor(Color.GREEN);
        paints[iLink.AVAILABLE].setStyle(Paint.Style.FILL);

        paints[iLink.ILLEGAL] = new Paint();
        paints[iLink.ILLEGAL].setColor(Color.RED);
        paints[iLink.ILLEGAL].setStyle(Paint.Style.FILL);

        Firebase parkingLotDB = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot");
        long curTimeInSec = iLink.getCurTimeInSec();
        parkingLotStatus = iLink.getParkingLotStatus(curTimeInSec, curTimeInSec);

        //initialize the rectangles & map
        for (int i = 0; i < numSpaces; i++) {

            Rect r = new Rect();
            r.set(x_tl + gap, y_tl, x_tl + width, y_tl + height);
            rectangle[i] = r;

            x_tl += width + gap;

            //10 spaces per row
            if ((i + 1) % 10 == 0) {
                x_tl = screenWidth / 25;
                y_tl += height + gap;

                //20 spaces per group
                if ((i + 1) % 20 == 0) {
                    y_tl += 40;
                }
            }

            recCol.put(r, paints[parkingLotStatus[i]]);
        }

        // Update parking lot status
        parkingLotDB.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                long curTimeInSec = iLink.getCurTimeInSec();
                parkingLotStatus = iLink.getParkingLotStatus(curTimeInSec, curTimeInSec);
                updateMapDisplay();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Map.Entry<Rect, Paint> r : recCol.entrySet()) {
            Rect key = r.getKey();
            Paint paint = r.getValue();

            canvas.drawRect(key, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Do nothing for now

        return true;

    }


    void updateMapDisplay() {

        for (int i = 0; i < numSpaces; i++) {
            recCol.put(rectangle[i], paints[parkingLotStatus[i]]);
            invalidate();
        }

    }


}
