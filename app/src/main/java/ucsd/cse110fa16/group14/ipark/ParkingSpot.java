package ucsd.cse110fa16.group14.ipark;

/**
 * Created by yuyangchen on 11/4/16.
 */

public class ParkingSpot {

    String parkingNumber;
    String endTime;
    String startTime;
    String user;
    boolean occupied;
    boolean illegal;

    public ParkingSpot(String spotNumber) {
        parkingNumber = spotNumber;
    }

    public boolean setEnd(String end) {
        if (end == null) return false;

        endTime = end;
        return true;
    }

    public boolean setStart(String start) {
        if (start == null) return false;

        startTime = start;
        return true;
    }

    public boolean setUser(String userName) {
        if (userName == null) return false;

        user = userName;
        return true;
    }

    public boolean setIllegal(boolean ilState) {
        illegal = ilState;
        return true;
    }

    public boolean setOccupied(boolean ocState) {
        occupied = ocState;
        return true;
    }


}
