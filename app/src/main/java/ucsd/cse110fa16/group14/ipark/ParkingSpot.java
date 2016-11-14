package ucsd.cse110fa16.group14.ipark;

/**
 * Created by yuyangchen on 11/4/16.
 */

public class ParkingSpot {

    public boolean setEnd(String end) {
        return end != null;

    }

    public boolean setStart(String start) {
        return start != null;

    }

    public boolean setUser(String userName) {
        return userName != null;

    }

    public boolean setIllegal(boolean ilState) {
        return true;
    }

    public boolean setOccupied(boolean ocState) {
        return true;
    }


}
