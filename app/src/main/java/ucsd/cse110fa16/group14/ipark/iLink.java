package ucsd.cse110fa16.group14.ipark;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class iLink {
    private static String usersNode = "https://ipark-e243b.firebaseio.com/Users/";
    private static String parkingLot = "https://ipark-e243b.firebaseio.com/ParkingLot/";
    private static FirebaseAuth auth;
    private static long gap = 0;
    private static int eStep = 2;
    private static int sStep = 3;

    public static final int AVAILABLE = 0;
    public static final int OWNER_RESERVED = 1;
    public static final int OCCUPIED = 2;
    public static final int ILLEGAL = 3;
    public static final int NUM_SPOTS = 80;
    protected static double defaultPrice;
    protected static double userPrice;
    private static int[] spotStatus = new int[NUM_SPOTS];
    //private static String schedule = "";

    public static void setGap(long newGap){
        gap = newGap;
    }

    private static String generateNewInsertSpotReservationData
            (String curDataStr, long startTime, String userName, long endTime )
    {

        if ( startTime < 0 || endTime < 0 || userName == "" || userName == null)
        {
            if (curDataStr == null || curDataStr == "" )return "";
            else return curDataStr;
        }

        if (curDataStr == null || curDataStr == "")
        {
            return Long.toString(startTime)+"/"+userName+"/"+Long.toString((endTime));
        }

        String[] orders = curDataStr.split("[ ]+");
        int orderNum = orders.length;
        // create a 2D array of startTime and endTime;
        int [][] orderTime = new int[orderNum][2];

        // parse each order and get startTime and endTime
        for(int i = 0; i < orderNum; i++)
        {
            String[] currOrder = orders[i].split("[/]");
            orderTime[i][0] = Integer.valueOf(currOrder[0]);
            orderTime[i][1] = Integer.valueOf(currOrder[2]);
        }

        int stopIndex = 0;
        for(stopIndex = 0; stopIndex < orderNum; stopIndex++)
        {

            if(endTime <= orderTime[stopIndex][0])
            {
                if (stopIndex == 0) break;
                else if (startTime >= orderTime[stopIndex - 1][1])
                    break;
            }

        }

        String result = "";
        // insert new order into schedule
        for (int i = 0; i < orderNum; i++)
        {
            if (i == stopIndex )
            {
                if (i == 0)
                {
                    result = result + Long.toString(startTime)
                            + "/" + userName + "/" + Long.toString(endTime);
                }
                else result = result + " " + Long.toString(startTime)
                        + "/" + userName + "/" + Long.toString(endTime);

                i--;
            }
            else
            {
                if (i == 0 )
                {
                    result = result + orders[i];
                }
                else result = result + " " + orders[i];
            }

        }

        if (stopIndex == orderNum){
            result = result + " " + Long.toString(startTime)
                    + "/" + userName + "/" + Long.toString(endTime);
        }

        return result;



        // add reservation in beginning
        /*if (curDataStr == ""){
            curDataStr = curDataStr + "" + startTime + " " + userName + " " + endTime;
            return curDataStr;
        }*/

        /*
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
        return curDataStr;*/


    }

    private static boolean checkSpotAvailability(String curDataStr, long startTime, long endTime ){

        // error input
        if ( startTime < 0 || endTime < 0)
            return false;
        // no one reserve that specific spot
        if (curDataStr == null ||curDataStr == "")
            return true;
        /*List<String> split = new ArrayList<String>(Arrays.asList(curDataStr.split("\\s+")));

        // check if spot is available
        for (int i = 0; i < split.size(); i=i+3){
            long sTime = Long.parseLong(split.get(i)) + gap;
            long eTime = Long.parseLong(split.get(i + eStep)) + gap;
            if (sTime == startTime || (sTime < startTime && eTime > startTime) ||
                    (sTime < endTime && eTime > endTime) || (sTime > startTime && sTime < endTime))
                return false;
        }
        return true;*/

        // split the schedule into an array of orders
        String[] orders = curDataStr.split("[ ]+");

        int orderNum = orders.length;
        // create a 2D array of startTime and endTime;
        int [][] orderTime = new int[orderNum][2];

        // parse each order and get startTime and endTime
        for(int i = 0; i < orderNum; i++)
        {
            //orders[i] = "2012/trump/2016";
            String[] currOrder = orders[i].split("[/]");
            orderTime[i][0] = Integer.valueOf(currOrder[0]);
            orderTime[i][1] = Integer.valueOf(currOrder[2]);
        }

        // compare and check
        for(int i = 0; i < orderNum; i++){


            if (endTime <= orderTime[i][0])
            {
                // endTime earlier than first order
                if (i == 0)
                {
                    return true;
                }
                // startTime later than previous order
                else if(startTime >= orderTime[i - 1][1])
                {
                    return true;
                }
            }

            // if startTime later than last order
            if (startTime >= orderTime[i][1])
            {
                if (i == orderNum - 1)
                {
                    return true;
                }
            }

        }

        return false;

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

    // Place the order. Take in spot, startTime and endTime in seconds
    public static void setOrder(final String spot, final long startTime, final long endTime) {

        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot/" + spot);

        Firebase scheduleRef = new Firebase(parkingLot + spot + "/Schedule");
        auth = FirebaseAuth.getInstance();
        String userName = auth.getCurrentUser().getDisplayName();

        updateUserReservationStatus( userName, spot, startTime, endTime );
        parkingLotLink.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener()
        {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> parkingSpot = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> iterator = parkingSpot.iterator();

                String schedule = "";
                Firebase scheduleRef = new Firebase(parkingLot + spot + "/Schedule");
                auth = FirebaseAuth.getInstance();
                String userName = auth.getCurrentUser().getDisplayName();

                //Getting parking spot information from Firebase
                while (iterator.hasNext()) {
                    com.firebase.client.DataSnapshot innerNode = iterator.next();
                    String innerKey = innerNode.getKey();

                    if (innerKey.equals("Schedule"))  {
                        schedule = innerNode.getValue(String.class);
                    }
                }

                System.out.println("=================================");
                System.out.println("Assigning New Parking Spot");
                System.out.println("=================================");
                System.out.println("  Current Schedule:  " + schedule);
                System.out.println("  Assigned Spot:     " + spot);
                //System.out.println("  User Registering:  " + userName);
                System.out.println("  Start Time In Sec: " + startTime);
                System.out.println("  End   Time In Sec: " + endTime);

                // Update old schedule data field with new reservation data
                String newSchedule = generateNewInsertSpotReservationData(schedule, startTime,userName,endTime );
                System.out.println("  New Schedule:      " + newSchedule);
                if (newSchedule != null) scheduleRef.setValue(newSchedule);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });

        //String newSchedule = generateNewInsertSpotReservationData(schedule, startTime,userName,endTime );
        //if (newSchedule != null) scheduleRef.setValue(newSchedule);
    }

    public static void changePrice(double newPrice) {
        Firebase priceRef = new Firebase(parkingLot + "SpotDefaultPrice" + "/Price");
        priceRef.setValue(newPrice);
    }

    public static void changeSchedule(String spot, String newScheduleData) {
        Firebase scheduleRef = new Firebase(parkingLot + spot + "/Schedule");
        scheduleRef.setValue( newScheduleData );
    }

    public static void changeLegalStatus(String spot, boolean newStatus) {
        Firebase legalRef = new Firebase(parkingLot + spot + "/Illegal");
        legalRef.setValue(newStatus);
    }

    public static void changeReserveStatus(String spot, boolean newStatus) {
        Firebase reserveRef = new Firebase(parkingLot + spot + "/OwnerReserved");
        reserveRef.setValue(newStatus);
    }

    /**
     * Returns a random spot that is available as a string. Eg. "Spot051". If all spots are full, it
     * will return null
     */
    public static String getSpot(long startTimeInSec, long endTimeInSec) {

        spotStatus = getParkingLotStatus(startTimeInSec, endTimeInSec);

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
            changeLegalStatus(spotText, true);
            return true;
        }
        else
            return false;
    }

    public static long getCurTimeInSec() {
        Date date = new Date();                               // given date
        Calendar calendar = GregorianCalendar.getInstance();  // creates a new calendar instance
        calendar.setTime(date);                               // assigns calendar to given date

        long curTimeInSec = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60;
        curTimeInSec += calendar.get(Calendar.MINUTE) * 60;
        curTimeInSec += calendar.get(Calendar.SECOND);

        return curTimeInSec;
    }


    public static int[] getParkingLotStatus(final long startTime, final long endTime) {




        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot");

        parkingLotLink.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot)
            {
                Iterable<com.firebase.client.DataSnapshot> parkingSpot = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> iterator = parkingSpot.iterator();


                //Getting individual parking spot
                for( int count = 0; count < NUM_SPOTS; count++)
                {
                    // the index of this spot
                    com.firebase.client.DataSnapshot node = iterator.next();

                    // get the field variables of spot
                    Iterable<com.firebase.client.DataSnapshot> spotInfo = node.getChildren();
                    Iterator<com.firebase.client.DataSnapshot> iterator1 = spotInfo.iterator();

                    boolean illegal = false;
                    boolean reserved = false;
                    String schedule = "";

                    //Getting parking spot information from Firebase
                    while (iterator1.hasNext()) {
                        com.firebase.client.DataSnapshot innerNode = iterator1.next();
                        String innerKey = innerNode.getKey();

                        if (innerKey.equals("Schedule"))  {
                            schedule = innerNode.getValue(String.class);
                        }
                        if(innerKey.equals("Illegal"))  {
                            illegal = ((innerNode.getValue(boolean.class)) ? true : false);
                        }
                        if(innerKey.equals("OwnerReserved")) {
                            reserved = ((innerNode.getValue(boolean.class)) ? true : false);
                        }
                    }

                    // Getting parking spot status and storing it into spotStatus
                    //long curTimeInSec = getCurTimeInSec();
                    if(illegal)
                        spotStatus[count] = ILLEGAL;
                    else if(reserved)
                        spotStatus[count] = OWNER_RESERVED;
                    else {
                        //System.out.println("Spot " + count + ": " + startTime + " " + endTime + " " + schedule);
                        if( checkSpotAvailability(schedule, startTime, endTime) == true )
                            spotStatus[count] = AVAILABLE;
                        else
                            spotStatus[count] = OCCUPIED;
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

    private static long getCurrentDate() {
        Calendar now = Calendar.getInstance();
        int year  = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day   = now.get(Calendar.DAY_OF_MONTH);

        String yearStr  = String.format("%04d", year );
        String monthStr = String.format("%02d", month );
        String dayStr   = String.format("%02d", day );

        // Generate currentDateString. Ex. February 1st, 2016 = 20160201
        String currentDateStr = yearStr + monthStr + dayStr;

        return Long.parseLong( currentDateStr );
    }

    // If last user activity was registered the day before, reset the map and user parking status
    // If last user activity was earlier today, update the time to current time in seconds
    public static void updateUserActivity() {
        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/Users");
        parkingLotLink.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> user = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> iterator = user.iterator();

                long lastActiveUserDate = 0;

                //Getting last active user time from Firebase
                while (iterator.hasNext()) {
                    com.firebase.client.DataSnapshot innerNode = iterator.next();
                    String innerKey = innerNode.getKey();

                    if (innerKey.equals("LastActiveUserDate"))  {
                        lastActiveUserDate = innerNode.getValue(Long.class);
                        break;
                    }
                }

                // If last login was yesterday, reset the database
                long currentDate = getCurrentDate();
                if( lastActiveUserDate < currentDate )
                    resetDataBaseForNewDay();

                // Update lastActivityUserTime to now
                Firebase lastActiveUserRef = new Firebase(usersNode + "LastActiveUserDate");
                lastActiveUserRef.setValue( getCurrentDate() );
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
    }

    // Reset each parking lot and user reservation data field to its default value
    private static void resetDataBaseForNewDay() {

        String spot;

        // Reset all parking spots
        for(int i = 0; i < NUM_SPOTS; i++) {
            spot = "Spot" + String.format( "%03d", i );
            changeLegalStatus(spot, false);
            changeReserveStatus(spot, false);
            changeSchedule(spot, "3500/testUser/4500");
        }

        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/Users");
        parkingLotLink.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> user = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> userIter = user.iterator();

                long lastActiveUserDate = 0;

                //Getting each user
                while (userIter.hasNext()) {
                    com.firebase.client.DataSnapshot innerNode = userIter.next();
                    String username = innerNode.getKey();

                    if (username.equals("LastActiveUserDate"))  {
                        lastActiveUserDate = innerNode.getValue(Long.class);
                        continue;
                    }

                    Firebase userAssignedSpot   = new Firebase(usersNode + username + "/ReservationStatus/AssignedSpot");
                    Firebase userSpotEndTime    = new Firebase(usersNode + username + "/ReservationStatus/EndTime");
                    Firebase userSpotStartTime  = new Firebase(usersNode + username + "/ReservationStatus/StartTime");
                    Firebase userSpotRate       = new Firebase(usersNode + username + "/ReservationStatus/SpotRate");

                    userAssignedSpot.setValue("");
                    userSpotEndTime.setValue(0);
                    userSpotStartTime.setValue(0);
                    userSpotRate.setValue(0);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
    }

    // Store user reservation details in under each user's ReservationStatus field
    private static void updateUserReservationStatus(final String userName, String spot, long startTime, long endTime) {

        System.out.println(spot + " " + startTime + " " + endTime );

        Firebase userAssignedSpot   = new Firebase(usersNode + userName + "/ReservationStatus/AssignedSpot");
        Firebase userSpotEndTime    = new Firebase(usersNode + userName + "/ReservationStatus/EndTime");
        Firebase userSpotStartTime  = new Firebase(usersNode + userName + "/ReservationStatus/StartTime");

        userAssignedSpot.setValue( spot );
        userSpotEndTime.setValue( endTime );
        userSpotStartTime.setValue( startTime );

        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot/SpotDefaultPrice/");
        parkingLotLink.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> parkingLotPrice = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> parkingLotPriceIter = parkingLotPrice.iterator();

                double parkingLotRate = 0;

                //Getting each user
                while (parkingLotPriceIter.hasNext()) {
                    com.firebase.client.DataSnapshot innerNode = parkingLotPriceIter.next();
                    String parkingLotData = innerNode.getKey();

                    if (parkingLotData.equals("Price"))  {
                        parkingLotRate = innerNode.getValue(Double.class);
                        break;
                    }
                    Firebase userSpotRate = new Firebase(usersNode + userName + "/ReservationStatus/SpotRate");
                    userSpotRate.setValue( parkingLotRate );
                    System.out.println("Rate: " + parkingLotRate);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
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
                "/" + child ;
        Firebase fReference = new Firebase(ref);
        final HashMap<String, String> map = new HashMap<>();
        fReference.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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

    protected static double getDefaultPrice() {

        String ref = "https://ipark-e243b.firebaseio.com/ParkingLot/SpotDefaultPrice" ;
        Firebase fReference = new Firebase(ref);
        fReference.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> firstChildData = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = firstChildData.iterator();

                while (iterator.hasNext()) {
                    DataSnapshot data = iterator.next();

                    if (data.getKey() == "Price") {
                        double val = data.getValue(Double.class);
                        defaultPrice = val;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
        return defaultPrice;
    }


}
