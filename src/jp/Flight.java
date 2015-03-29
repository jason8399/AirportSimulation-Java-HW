package jp;

/**
 * Created by JasonPan on 3/29/15.
 */
public class Flight implements Comparable<Flight>{
    private static int count;
    private int flghitNo;
    private int fuel;
    private int waitTime;
    private boolean isArrival;

    {
        count = 0;
    }

    Flight(){
        count++;
        this.flghitNo = count;
        this.fuel = 25;
        this.waitTime = 0;
        this.isArrival = true;
    }

    @Override
    public int compareTo(Flight flight) {
        return 0;
    }

    public int getFlghitNo() {
        return flghitNo;
    }

    public void setFlghitNo(int flghitNo) {
        this.flghitNo = flghitNo;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public boolean isArrival() {
        return isArrival;
    }

    public void setIsArrival(boolean isArrival) {
        this.isArrival = isArrival;
    }
}
