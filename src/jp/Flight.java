package jp;

import java.util.Random;

/**
 * Created by JasonPan on 3/29/15.
 */
public class Flight implements Comparable<Flight>{
    private static int count;
    private static int totalSuccessTakeOff;
    private static int totalSuccessLand;
    private static int totalCrashed;
    private int flightNo;
    private int fuel;
    private int waitTime;

    static{
        count = 0;
        totalSuccessLand = 0;
        totalSuccessTakeOff = 0;
        totalCrashed = 0;
    }

    Flight(){
        count++;
        Random num = new Random();
        this.flightNo = count;
        this.fuel = num.nextInt(25) + 1;
        this.waitTime = 0;
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

    public static void incTotalSuccessTakeOff(){
        totalSuccessTakeOff++;
    }

    public static int getTotalSuccessTakeOff() {
        return totalSuccessTakeOff;
    }

    public static void incTotalSuccessLand(){
        totalSuccessLand++;
    }

    public static int getTotalSuccessLand() {
        return totalSuccessLand;
    }

    public static void addToTotalCrashed(int crashed){
        totalCrashed += crashed;
    }

    public static int getTotalCrashed() {
        return totalCrashed;
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
}
