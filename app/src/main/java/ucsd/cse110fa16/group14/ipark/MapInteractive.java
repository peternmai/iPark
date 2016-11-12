package ucsd.cse110fa16.group14.ipark;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;

/**
 * Created by jasonallen/Peter on 10/8/16.
 */


/*In calling view or activity:
*  MapInteractive mp;
*  mp = new MapInteractive(this);
*  setContentView(mp);*/

// Map for owner
public class MapInteractive extends View {

    private static int[] parkingLotStatus;

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

    Context activityContext;
    Rect activityRect;

    private void init(Context context) {

        activityContext = context;

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
        paints[iLink.AVAILABLE] = new Paint();
        paints[iLink.AVAILABLE].setColor(Color.WHITE);
        paints[iLink.AVAILABLE].setStyle(Paint.Style.FILL);

        paints[iLink.RESERVED] = new Paint();
        paints[iLink.RESERVED].setColor(Color.YELLOW);
        paints[iLink.RESERVED].setStyle(Paint.Style.FILL);

        paints[iLink.OCCUPIED] = new Paint();
        paints[iLink.OCCUPIED].setColor(Color.GREEN);
        paints[iLink.OCCUPIED].setStyle(Paint.Style.FILL);

        paints[iLink.ILLEGAL] = new Paint();
        paints[iLink.ILLEGAL].setColor(Color.RED);
        paints[iLink.ILLEGAL].setStyle(Paint.Style.FILL);

        Firebase parkingLotDB = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot");
        Date date = new Date();                               // given date
        Calendar calendar = GregorianCalendar.getInstance();  // creates a new calendar instance
        calendar.setTime(date);                               // assigns calendar to given date
        long curTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        parkingLotStatus = iLink.getParkingLotStatus(curTime, curTime);

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

            recCol.put(r, paints[ parkingLotStatus[i] ]);
        }

        // Update parking lot status
        parkingLotDB.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Date date = new Date();                               // given date
                Calendar calendar = GregorianCalendar.getInstance();  // creates a new calendar instance
                calendar.setTime(date);                               // assigns calendar to given date
                long curTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
                parkingLotStatus = iLink.getParkingLotStatus(curTime, curTime);
                updateMapDisplay();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });


    }


    public MapInteractive(Context context) {
        super(context);
        init(context);
    }

    public MapInteractive(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MapInteractive(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cv = canvas;

        for (Map.Entry<Rect, Paint> r : recCol.entrySet()) {
            Rect key = r.getKey();
            Paint paint = r.getValue();

            canvas.drawRect(key, paint);
        }
    }

    final Handler handler = new Handler();
    Runnable longPressed = new Runnable() {
        public void run() {

            // Reset color change
            for (int i = 0; i < (paints.length - 1); i++)
                changeRectColor(activityRect);

            System.out.println("Long Press");

            AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);

            builder.setTitle("Advance Setting");

            final EditText input = new EditText(activityContext);
            input.setInputType(InputType.TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_DECIMAL);
            builder.setView(input);

            builder.setMessage("Current Rate:  $2.50/hr\n" +
                    "Enter New Parking Rate:");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Todo: handle the OK

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.postDelayed(longPressed, 500);
                for (Rect rect : rectangle) {
                    if (rect.contains((int) x, (int) y)) {
                        System.out.println("Touched Rectangle, change color");
                        changeRectColor(rect);
                        activityRect = rect;
                    }
                }
                break;
            /*
            case MotionEvent.ACTION_MOVE:
                handler.removeCallbacks(longPressed);
                break;
            */
            case MotionEvent.ACTION_UP:
                handler.removeCallbacks(longPressed);
                break;
        }

        return true;

    }

    void updateMapDisplay() {

        for( int i = 0; i < numSpaces; i++) {
            recCol.put(rectangle[i], paints[ parkingLotStatus[i] ]);
            invalidate();
        }

    }

    void changeRectColor(Rect r) {

        Paint p = recCol.get(r);

        //index in array of paint
        int idx = 0;
        int newIdx;
        for (int i = 0; i < paints.length; i++) {
            if (paints[i] == p) {
                idx = i;
            }
        }


        //bad attempt at hashing
        newIdx = (idx + 3) % 4;


        recCol.put(r, paints[newIdx]);
        invalidate();
    }

}
