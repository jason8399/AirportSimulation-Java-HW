package jp;

/**
 * Created by JasonPan on 3/29/15.
 */
public class Flight implements Comparable<Flight>{
    private static int count;
    private int flightNo;
    private int fuel;
    private int waitTime;
    private boolean isArrival;
    private boolean isCrash;

    {
        count = 0;
    }

    Flight(){
        count++;
        this.flightNo = count;
        this.fuel = 25;
        this.waitTime = 0;
        this.isArrival = true;
        this.isCrash = false;
    }

    @Override
    public int compareTo(Flight flight) {
        if(this.fuel - flight.fuel > 0)
            return 1;
        if(this.fuel - flight.fuel == 0){
            if(flight.waitTime - this.waitTime >  0) return 1;
            if(flight.waitTime - this.waitTime == 0) return 0;
            if(flight.waitTime - this.waitTime <  0) return -1;
        }
        if(this.fuel - flight.fuel < 0)
            return -1;
        return 0;
    }

    public void incWaitTime(){ //add waitTime
        this.waitTime++;
    }

    public void decFuel(){ //
        this.fuel--;
    }

    public int getFlghitNo() {
        return flightNo;
    }

    public void setFlghitNo(int flghitNo) {
        this.flightNo = flghitNo;
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

    public boolean isCrash() {
        return isCrash;
    }

    public void setIsCrash(boolean isCrash) {
        this.isCrash = isCrash;
    }
}
