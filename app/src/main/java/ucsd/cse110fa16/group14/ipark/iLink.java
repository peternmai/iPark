package ucsd.cse110fa16.group14.ipark;

import android.util.Log;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class iLink {
    private static String usersNode = "https://ipark-e243b.firebaseio.com/Users/";
    private static String parkingLot = "https://ipark-e243b.firebaseio.com/ParkingLot/";
    private static FirebaseAuth auth;
    private static long gap = 0;
    private static int eStep = 2;
    private static int sStep = 3;

    public static void setGap(long newGap){
        gap = newGap;
    }

    private static String generateNewInsertSpotReservationData( String curDataStr, long startTime, String userName, long endTime ){
        if (curDataStr == null || startTime < 0 || endTime < 0 || userName == "" || userName == null)
            return curDataStr;

        // add reservation in beginning
        if (curDataStr == ""){
            curDataStr = curDataStr + "" + startTime + " " + userName + " " + endTime;
            return curDataStr;
        }

        List<String> split = new ArrayList<String>(Arrays.asList(curDataStr.split("\\s+")));

        int beg = -1;

        // find space to put in reservation
        for (int i = 0; i < split.size(); i = i + sStep) {
            if (i == 0){
                long csTime = Long.parseLong(split.get(0));
                if (csTime > startTime && csTime >= endTime){
                    beg = 0;
                    break;
                }
            }
            long peTime = Long.parseLong(split.get(i + eStep));
            if ((i + sStep) >= split.size()){
                beg = i + sStep;
                break;
            }
            long nsTime = Long.parseLong(split.get(i + sStep));
            if (peTime + gap <= startTime && nsTime >= endTime){
                beg = i + sStep;
                break;
            }
        }

        // add reservation
        if (beg >= 0){
            split.add(beg, "" + startTime);
            split.add(beg + 1, "" + userName);
            split.add(beg + eStep, "" + endTime);
            //create string
            StringBuilder sb = new StringBuilder();
            for (String s : split) {
                sb.append(s);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }
        return curDataStr;
    }

    private static boolean checkSpotAvailability(String curDataStr, long startTime, long endTime ){
        if (curDataStr == null || startTime < 0 || endTime < 0)
            return false;
        // no one reserve that specific spot
        if (curDataStr == "")
            return true;
        List<String> split = new ArrayList<String>(Arrays.asList(curDataStr.split("\\s+")));

        // check if spot is available
        for (int i = 0; i < split.size(); i=i+3){
            long sTime = Long.parseLong(split.get(i)) + gap;
            long eTime = Long.parseLong(split.get(i + eStep)) + gap;
            if (sTime == startTime || (sTime < startTime && eTime > startTime) ||
                    (sTime < endTime && eTime > endTime) || (sTime > startTime && sTime < endTime))
                return false;
        }
        return true;
    }

    public static Task changePassword(String username, String newPassword) {
        auth = FirebaseAuth.getInstance();
        Task task = auth.getCurrentUser().updatePassword(newPassword);
        if (task.isSuccessful()) {
            Firebase passwordRef = new Firebase(usersNode + username + "/password");
            passwordRef.setValue(newPassword);
        }
        return task;
    }

    public static Task changeEmail(String username, String newEmail) {
        auth = FirebaseAuth.getInstance();
        Task task = auth.getCurrentUser().updatePassword(newEmail);
        if (task.isSuccessful()) {
            Firebase emailRef = new Firebase(usersNode + username + "/email");
            emailRef.setValue(newEmail);
            DriverRegistration.uMapEmail.put(username, newEmail);
        }
        return task;
    }

    public static void changeStartTime(String spot, long newStartTime) {
        Firebase startTimeRef = new Firebase(parkingLot + spot + "/StartTime");
        startTimeRef.setValue(newStartTime);
    }

    public static void changeEndTime(String spot, long newEndTime) {
        Firebase endTimeRef = new Firebase(parkingLot + spot + "/EndTime");
        endTimeRef.setValue(newEndTime);
    }

    public static void setOrder(String spot,  long startTime, long endTime) {
        Firebase scheduleRef = new Firebase(parkingLot + spot + "/Schedule");
        auth = FirebaseAuth.getInstance();
        String userName = auth.getCurrentUser().getDisplayName();

        // TODO: need to get the schedule of spot
        //scheduleRef.setValue(generateNewInsertSpotReservationData(schedule, startTime,userName,endTime ));
    }

    public static void changePrice(long newPrice) {
        Firebase priceRef = new Firebase(parkingLot + "SpotDefaultPrice");
        priceRef.setValue(newPrice);
    }

    public static void changeLegalStatus(String spot, boolean newStatus) {
        Firebase legalRef = new Firebase(parkingLot + spot + "/Illegal");
        legalRef.setValue(newStatus);
    }

    public static void changeReserveStatus(String spot, boolean newStatus) {
        Firebase reserveRef = new Firebase(parkingLot + spot + "/Reserved");
        reserveRef.setValue(newStatus);
    }

    /**
     * Returns a random spot that is available as a string. Eg. "Spot051". If all spots are full, it
     * will return null
     */
    public static String getSpot(int startTime, int endTime) {

        getParkingLotStatus(startTime, endTime);
        Vector<String> spotsAvailable = new Vector<String>();
        for(int i = 0; i < NUM_SPOTS; i++) {
            if( spotStatus[i] == AVAILABLE ) {
                spotsAvailable.add( "Spot" + String.format("%03d", i) );
            }
        }

        if( spotsAvailable.size() == 0 )
            return null;

        Random rand = new Random();
        int spotAssign = rand.nextInt( spotsAvailable.size() );

        return spotsAvailable.elementAt( spotAssign );
    }

    // TODO: This function inserts a new data field rather than updates
    public static boolean reportOther(int spot) {

        // If reporting invalid spot, return false
        if( spot < 0 || spot >= NUM_SPOTS )
            return false;

        // Can only report parking spots that should be open
        //getParkingLotStatus();
        String spotText = "Spot" + String.format("%03d", spot);
        System.out.println(spotText);
        if( spotStatus[spot] == AVAILABLE ) {
            //TODO: changeLegalStatus(spotText, true);
            return true;
        }
        else
            return false;
    }

    private static long curTime;
    public static int[] spotStatus = new int[80];
    public static final int AVAILABLE = 0;
    public static final int RESERVED = 1;
    public static final int OCCUPIED = 2;
    public static final int ILLEGAL = 3;
    public static final int NUM_SPOTS = 80;
    public static final int STARTINDEX = 0;
    public static final int ENDINDEX = 2;

    public static int[] getParkingLotStatus(final long startTime, final long endTime) {

        /*Date date = new Date();                               // given date
        Calendar calendar = GregorianCalendar.getInstance();  // creates a new calendar instance
        calendar.setTime(date);                               // assigns calendar to given date
        curTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);*/


        // initialize the parking lot status
        for (int i = 0; i < NUM_SPOTS; i++){
            spotStatus[i] = OCCUPIED;
        }
        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot");

        // parkingLotLink.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
        parkingLotLink.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot)
            {
                Iterable<com.firebase.client.DataSnapshot> parkingSpot = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> iterator = parkingSpot.iterator();
                //long startTime = 0;
                //long endTime = 0;

                //Getting individual parking spot
                for( int count = 0; count < NUM_SPOTS; count++)
                {

                    // the index of this spot
                    int index;
                    com.firebase.client.DataSnapshot node = iterator.next();
                    // get the index from "SpotXXX"
                    index = Integer.valueOf(node.getKey().substring(4, 8));
                    //System.out.print(node.getKey());

                    // get the field variables of spot
                    Iterable<com.firebase.client.DataSnapshot> spotInfo = node.getChildren();
                    Iterator<com.firebase.client.DataSnapshot> iterator1 = spotInfo.iterator();

                    boolean illegal = false;
                    boolean reserved = false;
                    String schedule = null;

                    //Getting start Time
                    while (iterator1.hasNext()) {
                        com.firebase.client.DataSnapshot innerNode = iterator1.next();
                        String innerKey = innerNode.getKey();

                        /*if (innerKey.equals("StartTime")) {
                            startTime = innerNode.getValue(long.class);
                        }
                        if(innerKey.equals("EndTime")) {
                            endTime = innerNode.getValue(long.class);
                        }*/

                        if (innerKey.equals("Schedule"))  {
                            schedule = innerNode.getValue(String.class);
                        }
                        if(innerKey.equals("Illegal"))  {
                            illegal = ((innerNode.getValue(boolean.class)) ? true : false);
                        }
                        if(innerKey.equals("Reserved")) {
                            reserved = ((innerNode.getValue(boolean.class)) ? true : false);
                        }
                    }

                    if(illegal)
                        spotStatus[index] = ILLEGAL;
                    else if(reserved)
                        spotStatus[index] = RESERVED;
                    else
                    {

                        if (schedule == null) {
                            spotStatus[index] = AVAILABLE;
                            continue;
                        }
                        // parse the Schedule into "startTime/spot/endTime"
                        String[] orders = schedule.split("[ ]+");
                        // create an 2D array of start and end time;
                        int[][] startAndEnd = new int[orders.length][2];

                        // read all the orders of this spot into 2D array
                        for(int i = 0; i < orders.length; i++)
                        {

                            String[] orderInfo = orders[i].split("[/]");
                            int orderStartTime = Integer.parseInt(orderInfo[STARTINDEX]);
                            int orderEndTime = Integer.parseInt(orderInfo[ENDINDEX]);
                            startAndEnd[i][0] = orderStartTime;
                            startAndEnd[i][1] = orderEndTime;

                        }

                        //check availability
                        for (int i = 0; i < orders.length; i++)
                        {
                            if(endTime <= startAndEnd[i][0])
                            {
                                if (i == 0)
                                {
                                    spotStatus[index] = AVAILABLE;
                                    break;
                                }
                                else if(startTime >= startAndEnd[i-1][1])
                                {
                                    spotStatus[index] = AVAILABLE;
                                    break;
                                }
                            }
                            else if(startTime >= startAndEnd[i][1])
                            {
                                if (i == orders.length - 1 )
                                {
                                    spotStatus[index] = AVAILABLE;
                                    break;
                                }
                            }

                        }

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });

        System.out.print("ParkingLotStatus: ");
        for(int i = 0; i < NUM_SPOTS; i++)
            System.out.print(spotStatus[i]);
        System.out.println();

        return spotStatus;
    }

    /**
     * getDataFromFirebase returns a HashMap with the name of the first child node as the key
     * and the value of the inner child as the value.
     *
     * @param mainKey  "Folder" in the firebase you want to access. For example, Users or ParkingLot
     * @param innerKey specific data you want to access. For example, username or the end time of a parking spot.
     * @return HashMap with the name of the data we are accessing to as key and the value of what we want as a value.
     */
    protected static HashMap getDataMapFromFirebase(String mainKey, final String innerKey) {

        Firebase fReference = new Firebase("https://ipark-e243b.firebaseio.com/" + mainKey);
        final HashMap<String, String> map = new HashMap<>();

        fReference.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> mainNodeData = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> iterator = mainNodeData.iterator();

                //Getting mainNode keys
                while (iterator.hasNext()) {
                    com.firebase.client.DataSnapshot node = iterator.next();
                    String mainNodeKey = node.getKey();

                    Iterable<com.firebase.client.DataSnapshot> innerNodeData = node.getChildren();
                    Iterator<com.firebase.client.DataSnapshot> iterator1 = innerNodeData.iterator();

                    //Getting innerNodeKey's value
                    while (iterator1.hasNext()) {
                        com.firebase.client.DataSnapshot innerNode = iterator1.next();
                        String currentKey = innerNode.getKey();
                        if (currentKey.equals(innerKey)) {
                            String innerNodeValue = innerNode.getValue(String.class);
                            map.put(mainNodeKey, innerNodeValue);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
        return map;
    }

    /**
     * Takes the root and child and returns a map with all the information of that child.
     *
     * @param root  Users, ParkingLot, or History
     * @param child username, spot, or the whole date/time thing
     * @return the map with child's values. Example: (username, admin),(email, www123@gmail.com)
     */
    protected static HashMap<String, String> getChildInfo(String root, final String child) {

        String ref = "https://ipark-e243b.firebaseio.com/" + root +
                "/" + child + "/";
        Firebase fReference = new Firebase(ref);
        final HashMap<String, String> map = new HashMap<>();
        fReference.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> firstChildData = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = firstChildData.iterator();

                while (iterator.hasNext()) {
                    DataSnapshot data = iterator.next();

                    if (data.getKey() != "History") {
                        String key = data.getKey();
                        String val = data.getValue(String.class);
                        map.put(key, val);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
        return map;
    }

}
