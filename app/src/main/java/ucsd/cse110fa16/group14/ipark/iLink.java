package ucsd.cse110fa16.group14.ipark;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 * Part of these codes are made with references to the Firebase API
 * https://firebase.google.com/docs/database/android/read-and-write
 */

public class iLink {
    private static String usersNode = "https://ipark-e243b.firebaseio.com/Users/";
    private static String parkingLot = "https://ipark-e243b.firebaseio.com/ParkingLot/";
    private static FirebaseAuth auth;


    public static final int AVAILABLE = 0;
    public static final int OWNER_RESERVED = 1;
    public static final int OCCUPIED = 2;
    public static final int ILLEGAL = 3;
    public static final int NUM_SPOTS = 80;

    protected static double defaultPrice;
    protected static double userPrice;

    private static int[] spotStatus = new int[NUM_SPOTS];


    // Used to access user's reservation data. Call getUserReservationStatus() beforehand
    protected static String userReservationSpot = "";
    protected static long userReservationStartTime = 0;
    protected static long userReservationEndTime = 0;
    protected static double userReservationSpotRate = 0;
    protected static boolean newMessages = false;

    private static String generateNewInsertSpotReservationData
            (String curDataStr, long startTime, String userName, long endTime) {

        if (startTime < 0 || endTime < 0 || userName == "" || userName == null) {
            if (curDataStr == null || curDataStr == "") return "";
            else return curDataStr;
        }
        //System.out.println("test_1");

        if (curDataStr == null || curDataStr == "") {
            return Long.toString(startTime) + "/" + userName + "/" + Long.toString((endTime));
        }

        String[] orders = curDataStr.split("[ ]+");
        //System.out.println("test_2");
        int orderNum = orders.length;
        // create a 2D array of startTime and endTime;
        int[][] orderTime = new int[orderNum][2];

        // parse each order and get startTime and endTime
        for (int i = 0; i < orderNum; i++) {
            String[] currOrder = orders[i].split("[/]");
            orderTime[i][0] = Integer.valueOf(currOrder[0]);
            orderTime[i][1] = Integer.valueOf(currOrder[2]);
        }

        //System.out.println("test_3");
        int stopIndex;

        for (stopIndex = 0; stopIndex < orderNum; stopIndex++) {

            if (endTime <= orderTime[stopIndex][0]) {
                if (stopIndex == 0) break;
                else if (startTime >= orderTime[stopIndex - 1][1])
                    break;
            }

        }

        //System.out.println("test_4");

        String result = "";
        // insert new order into schedule
        for (int i = 0; i < orderNum; i++) {
            if (i == stopIndex) {
                if (i == 0) {
                    result = result + Long.toString(startTime)
                            + "/" + userName + "/" + Long.toString(endTime);
                    if (orderNum > 0)
                    {
                        result = result + " ";
                    }
                } else result = result + " " + Long.toString(startTime)
                        + "/" + userName + "/" + Long.toString(endTime);


                stopIndex = i - 2;
                i--;
            } else {
                if (i == 0) {
                    result = result + orders[i];
                } else result = result + " " + orders[i];
            }

            //System.out.println(i+" ");
        }

        if (stopIndex == orderNum) {
            result = result + " " + Long.toString(startTime)
                    + "/" + userName + "/" + Long.toString(endTime);
        }

        //System.out.println("test_5");
        return result;


    }


    private static boolean checkSpotAvailability(String curDataStr, long startTime, long endTime) {

        // error input
        if (startTime < 0 || endTime < 0)
            return false;
        // no one reserve that specific spot
        if (curDataStr == null || curDataStr == "")
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
        int[][] orderTime = new int[orderNum][2];

        // parse each order and get startTime and endTime
        for (int i = 0; i < orderNum; i++) {
            //orders[i] = "2012/trump/2016";
            String[] currOrder = orders[i].split("[/]");
            //System.out.println("what is the order: "+ orders[i]);
            //System.out.println("length is "+ currOrder.length);
            orderTime[i][0] = Integer.parseInt(currOrder[0]);
            //System.out.println("\nPRINTING CURR ORDER 2: " + " " +currOrder[2]+"\n\n");
            orderTime[i][1] = Integer.parseInt(currOrder[2]);
        }

        // compare and check
        for (int i = 0; i < orderNum; i++) {


            if (endTime <= orderTime[i][0]) {
                // endTime earlier than first order
                if (i == 0) {
                    return true;
                }
                // startTime later than previous order
                else if (startTime >= orderTime[i - 1][1]) {
                    return true;
                }
            }

            // if startTime later than last order
            if (startTime >= orderTime[i][1]) {
                if (i == orderNum - 1) {
                    return true;
                }
            }

        }

        return false;

    }


    public static void checkout(final String spot, final long startTime) {

        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot/" + spot);

        parkingLotLink.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> parkingSpot = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> iterator = parkingSpot.iterator();

                String schedule = "";
                Firebase scheduleRef = new Firebase(parkingLot + spot + "/Schedule");
                while (iterator.hasNext()) {
                    com.firebase.client.DataSnapshot innerNode = iterator.next();
                    String innerKey = innerNode.getKey();

                    if (innerKey.equals("Schedule")) {
                        schedule = innerNode.getValue(String.class);
                        break;
                    }
                }

                String newSchedule = remove(schedule, startTime);
                //System.out.println("  New Schedule:      " + newSchedule);
                if (newSchedule != null) scheduleRef.setValue(newSchedule);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });


    }

    private static String remove(String curDataStr, long startTime) {

        //System.out.println("test_1");

        String result = "";
        if (curDataStr == null || curDataStr == "") {
            return result;
        }

        if (startTime < 0) {
            return curDataStr;
        }

        String[] orders = curDataStr.split("[ ]+");

        int orderNum = orders.length;
        // create a 2D array of startTime and endTime;
        int[] orderTime = new int[orderNum];

        int stopIndex = 0;
        // parse each order and get startTime and endTime
        for (stopIndex = 0; stopIndex < orderNum; stopIndex++) {
            //orders[i] = "2012/trump/2016";
            String[] currOrder = orders[stopIndex].split("[/]");
            //System.out.println("what is the order: "+ orders[i]);
            //System.out.println("length is "+ currOrder.length);
            if (Integer.parseInt(currOrder[0]) == startTime) {
                break;
            }

        }

        if (stopIndex == orderNum) return result;
        else {
            for (int i = (stopIndex + 1); i < orderNum; i++) {
                result = result + orders[i];
                if (i != (orderNum - 1)) {
                    result = result + " ";
                }
            }

            return result;
        }

    }

    // Place the order. Take in spot, startTime and endTime in seconds
    public static void setOrder(final String spot, final long startTime, final long endTime) {

        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot/" + spot);

        //Firebase scheduleRef = new Firebase(parkingLot + spot + "/Schedule");
        auth = FirebaseAuth.getInstance();
        String userName = auth.getCurrentUser().getDisplayName();

        updateUserReservationStatus(userName, spot, startTime, endTime);
        parkingLotLink.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {

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

                    if (innerKey.equals("Schedule")) {
                        schedule = innerNode.getValue(String.class);
                    }
                }

                //TODO: Remove the print statements
                System.out.println("=================================");
                System.out.println("Assigning New Parking Spot");
                System.out.println("=================================");
                System.out.println("  Current Schedule:  " + schedule);
                System.out.println("  Assigned Spot:     " + spot);
                //System.out.println("  User Registering:  " + userName);
                System.out.println("  Start Time In Sec: " + startTime);
                System.out.println("  End   Time In Sec: " + endTime);

                // Update old schedule data field with new reservation data
                String newSchedule = generateNewInsertSpotReservationData(schedule, startTime, userName, endTime);
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

    /**
     * Changes the price in Firebase
     * @param newPrice new price
     */
    public static void changePrice(double newPrice) {
        Firebase priceRef = new Firebase(parkingLot + "SpotDefaultPrice" + "/Price");
        priceRef.setValue(newPrice);
    }

    /**
     * Changes the Schedule in firebase
     * @param spot Spot whose schedule needs ot be changed
     * @param newScheduleData new schedule
     */
    public static void changeSchedule(String spot, String newScheduleData) {
        Firebase scheduleRef = new Firebase(parkingLot + spot + "/Schedule");
        scheduleRef.setValue(newScheduleData);
    }

    /**
     * Changes legal status in firebase
     * @param spot spot whose status needs to be changed
     * @param newStatus boolean to set the new status
     */
    public static void changeLegalStatus(String spot, boolean newStatus) {
        Firebase legalRef = new Firebase(parkingLot + spot + "/Illegal");
        legalRef.setValue(newStatus);
    }

    /**
     * Changes reserve status in firebase
     * @param spot spot whose status needs to be changed
     * @param newStatus boolean to set the new status
     */
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
        for (int i = 0; i < NUM_SPOTS; i++) {
            if (spotStatus[i] == AVAILABLE) {
                spotsAvailable.add("Spot" + String.format("%03d", i));
            }
        }

        if (spotsAvailable.size() == 0)
            return null;

        Random rand = new Random();
        int spotAssign = rand.nextInt(spotsAvailable.size());

        return spotsAvailable.elementAt(spotAssign);
    }

    // Report other user
    public static boolean reportOther(int spot) {

        // If reporting invalid spot, return false
        if (spot < 0 || spot >= NUM_SPOTS)
            return false;

        // Can only report parking spots that should be open
        //getParkingLotStatus();
        String spotText = "Spot" + String.format("%03d", spot);
        System.out.println(spotText);
        if (spotStatus[spot] == AVAILABLE) {
            changeLegalStatus(spotText, true);
            return true;
        } else
            return false;
    }

    // Returns the current time in seconds
    protected static long getCurTimeInSec() {
        Date date = new Date();                               // given date
        Calendar calendar = GregorianCalendar.getInstance();  // creates a new calendar instance
        calendar.setTime(date);                               // assigns calendar to given date

        long curTimeInSec = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60;
        curTimeInSec += calendar.get(Calendar.MINUTE) * 60;
        curTimeInSec += calendar.get(Calendar.SECOND);

        return curTimeInSec;
    }


    // Get the parking lot status for a given time and store it in an array
    protected static int[] getParkingLotStatus(final long startTime, final long endTime) {

        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot");

        parkingLotLink.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> parkingSpot = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> iterator = parkingSpot.iterator();


                //Getting individual parking spot
                for (int count = 0; count < NUM_SPOTS; count++) {
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

                        if (innerKey.equals("Schedule")) {
                            schedule = innerNode.getValue(String.class);
                        }
                        if (innerKey.equals("Illegal")) {
                            illegal = ((innerNode.getValue(boolean.class)));
                        }
                        if (innerKey.equals("OwnerReserved")) {
                            reserved = ((innerNode.getValue(boolean.class)));
                        }
                    }

                    // Getting parking spot status and storing it into spotStatus
                    //long curTimeInSec = getCurTimeInSec();
                    if (illegal)
                        spotStatus[count] = ILLEGAL;
                    else if (reserved)
                        spotStatus[count] = OWNER_RESERVED;
                    else {
                        //System.out.println("Spot " + count + ": " + startTime + " " + endTime + " " + schedule);
                        if (checkSpotAvailability(schedule, startTime, endTime) == true)
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

        /**
        System.out.print("ParkingLotStatus: ");
        for (int i = 0; i < NUM_SPOTS; i++)
            System.out.print(spotStatus[i]);
        System.out.println();
         **/

        return spotStatus;
    }

    // Get user reservation status (spot, start time, end time, etc)
    protected static void getUserReservationStatus() {

        auth = FirebaseAuth.getInstance();
        String userName = auth.getCurrentUser().getDisplayName();

        String ref = "https://ipark-e243b.firebaseio.com/Users/" + userName + "/ReservationStatus";
        Firebase fReference = new Firebase(ref);
        fReference.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> firstChildData = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = firstChildData.iterator();
                String innerKey;

                while (iterator.hasNext()) {
                    DataSnapshot data = iterator.next();
                    innerKey = data.getKey();

                    if (innerKey.equals("AssignedSpot")) {
                        userReservationSpot = data.getValue(String.class);
                    }

                    if (innerKey.equals("EndTime")) {
                        userReservationEndTime = data.getValue(Long.class);
                    }

                    if (innerKey.equals("StartTime")) {
                        userReservationStartTime = data.getValue(Long.class);
                    }

                    if (innerKey.equals("SpotRate")) {
                        userReservationSpotRate = data.getValue(Double.class);
                    }
                    if (innerKey.equals("NewMessages") ) {
                        newMessages = data.getValue(Boolean.class);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
    }

    // Get current time: Ex. February 1, 2016 = 20160201
    private static long getCurrentDate() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);

        String yearStr = String.format("%04d", year);
        String monthStr = String.format("%02d", month);
        String dayStr = String.format("%02d", day);

        // Generate currentDateString. Ex. February 1st, 2016 = 20160201
        String currentDateStr = yearStr + monthStr + dayStr;

        return Long.parseLong(currentDateStr);
    }

    // If last user activity was registered the day before, reset the map and user parking status
    // If last user activity was earlier today, update the time to current time in seconds
    protected static void updateUserActivity() {
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

                    if (innerKey.equals("LastActiveUserDate")) {
                        lastActiveUserDate = innerNode.getValue(Long.class);
                        break;
                    }
                }

                // If last login was yesterday, reset the database
                long currentDate = getCurrentDate();
                if (lastActiveUserDate < currentDate)
                    resetDataBaseForNewDay();

                // Update lastActivityUserTime to now
                Firebase lastActiveUserRef = new Firebase(usersNode + "LastActiveUserDate");
                lastActiveUserRef.setValue(getCurrentDate());
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
        for (int i = 0; i < NUM_SPOTS; i++) {
            spot = "Spot" + String.format("%03d", i);
            changeLegalStatus(spot, false);
            changeReserveStatus(spot, false);
            changeSchedule(spot, "");
        }

        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/Users");
        parkingLotLink.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> user = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> userIter = user.iterator();

                long lastActiveUserDate = 0;

                //Getting each user
                while (userIter.hasNext()) {
                    com.firebase.client.DataSnapshot innerNode = userIter.next();
                    String username = innerNode.getKey();

                    if (username.equals("LastActiveUserDate")) {
                        lastActiveUserDate = innerNode.getValue(Long.class);
                        continue;
                    }

                    Firebase userAssignedSpot = new Firebase(usersNode + username + "/ReservationStatus/AssignedSpot");
                    Firebase userSpotEndTime = new Firebase(usersNode + username + "/ReservationStatus/EndTime");
                    Firebase userSpotStartTime = new Firebase(usersNode + username + "/ReservationStatus/StartTime");
                    Firebase userSpotRate = new Firebase(usersNode + username + "/ReservationStatus/SpotRate");

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

    protected static void alertUserNewMessages() {
        System.out.println("Alerting");
        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/Users");
        parkingLotLink.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> user = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> userIter = user.iterator();

                long lastActiveUserDate = 0;

                //Getting each user
                while (userIter.hasNext()) {
                    com.firebase.client.DataSnapshot innerNode = userIter.next();
                    String username = innerNode.getKey();

                    if (username.equals("LastActiveUserDate")) {
                        lastActiveUserDate = innerNode.getValue(Long.class);
                        continue;
                    }

                    Firebase newMessagesField = new Firebase(usersNode + username + "/ReservationStatus/NewMessages");
                    System.out.println("alert "+username);
                    newMessagesField.setValue(true);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
    }


    protected static void resetUserReservation() {

        auth = FirebaseAuth.getInstance();
        String username = auth.getCurrentUser().getDisplayName();

        Firebase userAssignedSpot = new Firebase(usersNode + username + "/ReservationStatus/AssignedSpot");
        Firebase userSpotEndTime = new Firebase(usersNode + username + "/ReservationStatus/EndTime");
        Firebase userSpotStartTime = new Firebase(usersNode + username + "/ReservationStatus/StartTime");
        Firebase userSpotRate = new Firebase(usersNode + username + "/ReservationStatus/SpotRate");

        userAssignedSpot.setValue("");
        userSpotEndTime.setValue(0);
        userSpotStartTime.setValue(0);
        userSpotRate.setValue(0);
    }

    // Store user reservation details in under each user's ReservationStatus field
    private static void updateUserReservationStatus(final String userName, String spot, long startTime, long endTime) {

        System.out.println(spot + " " + startTime + " " + endTime);

        Firebase userAssignedSpot = new Firebase(usersNode + userName + "/ReservationStatus/AssignedSpot");
        Firebase userSpotEndTime = new Firebase(usersNode + userName + "/ReservationStatus/EndTime");
        Firebase userSpotStartTime = new Firebase(usersNode + userName + "/ReservationStatus/StartTime");

        userAssignedSpot.setValue(spot);
        userSpotEndTime.setValue(endTime);
        userSpotStartTime.setValue(startTime);

        Firebase parkingLotLink = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot/SpotDefaultPrice");
        parkingLotLink.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> parkingLotPrice = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> parkingLotPriceIter = parkingLotPrice.iterator();

                double parkingLotRate = 0;

                //Getting each user
                while (parkingLotPriceIter.hasNext()) {
                    com.firebase.client.DataSnapshot innerNode = parkingLotPriceIter.next();
                    String parkingLotData = innerNode.getKey();

                    if (parkingLotData.equals("Price")) {
                        parkingLotRate = innerNode.getValue(Double.class);
                        break;
                    }
                }
                Firebase userSpotRate = new Firebase(usersNode + userName + "/ReservationStatus/SpotRate");
                userSpotRate.setValue(parkingLotRate);
                System.out.println("Rate: " + parkingLotRate);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
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
                "/" + child;
        Firebase fReference = new Firebase(ref);
        final HashMap<String, String> map = new HashMap<>();
        fReference.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> firstChildData = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = firstChildData.iterator();

                while (iterator.hasNext()) {
                    DataSnapshot data = iterator.next();
                    if (!data.hasChildren()) {
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

    /**
     * Gets the default price of parking spots from firebase
     * @return current price from firebase
     */
    protected static double getDefaultPrice() {
        String ref = "https://ipark-e243b.firebaseio.com/ParkingLot/SpotDefaultPrice/Price";
        Firebase fReference = new Firebase(ref);
        fReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double val = dataSnapshot.getValue(Double.class);
                defaultPrice = val;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
        return defaultPrice;
    }


}
