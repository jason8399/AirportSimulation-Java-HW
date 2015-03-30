package jp;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by JasonPan on 3/29/15.
 */
public class Airport {
    private PriorityQueue<Flight> arrival = new PriorityQueue<>();
    private LinkedBlockingQueue<Flight> departure = new LinkedBlockingQueue<>();
    private ArrayList<Integer> arrivingList = new ArrayList<>();
    private ArrayList<Integer> departureList = new ArrayList<>();
    private int count;
    private int crashed;
    private int time;
    private double successTakeOff;
    private double successLand;
    private double totalLengthArrival;
    private double totalLengthDeparture;
    private double totalLandTime;
    private double totalTakeOffTime;

    Airport(){
        this.count = 100;
        this.crashed = 0;
        this.time = 0;
        this.successLand = 0;
        this.successTakeOff = 0;
        this.totalLengthArrival = 0;
        this.totalLengthDeparture = 0;
        this.totalLandTime = 0;
        this.totalTakeOffTime = 0;
    }

    Airport(int count) {
        this.count = count;
        this.crashed = 0;
        this.time = 0;
        this.successLand = 0;
        this.successTakeOff = 0;
        this.totalLengthArrival = 0;
        this.totalLengthDeparture = 0;
        this.totalLandTime = 0;
        this.totalTakeOffTime = 0;
    }

    public void start(){
        for(time = 0; time < this.count; time++){
            lengthCal();
            makeFlight();
            for(int j = 0; j < 1; j++)
                landTakeOffProcess();
            incAllWaitTime();
            decAllFuel();
            printInfo();
            clean();
        }
        //For the flights in Queue
        for(;!arrival.isEmpty() || !departure.isEmpty();){
            this.time++;
            lengthCal();
            for(int j = 0; j < 1; j++)
                landTakeOffProcess();
            incAllWaitTime();
            decAllFuel();
            printInfo();
            clean();
        }
        endPrint();
    }

    private void makeFlight(){
        //random make 0 ~ 5 flight
        Random rand = new Random();
        int num = rand.nextInt(6);
        Flight flight;
        for(int i = 0; i < num; i++){
            flight = new Flight();
            arrival.add(flight);
            arrivingList.add(flight.getFlghitNo());
        }
    }

    private void incAllWaitTime(){
        //increase 'ALL' waitTime in queue
        for(Flight flight: arrival){
            flight.incWaitTime();
        }

        for(Flight flight: departure){
            flight.incWaitTime();
        }
    }

    private void decAllFuel(){
        //decrease 'ALL' waitTime in arrival queue
        ArrayList<Flight> col = new ArrayList<>();
        for(Flight flight: arrival) {
            flight.decFuel();
            if(flight.getFuel() <= 0){
                crashed++;
                col.add(flight);
            }
        }
        arrival.removeAll(col);
    }

    private void landTakeOffProcess(){
        //handle land and take off.
        if(!this.departure.isEmpty()){
            if(this.arrival.isEmpty() || this.arrival.peek().getFuel() >= 2) {
                Flight takeOff;
                takeOff = departure.poll();
                Flight.incTotalSuccessTakeOff();
                departureList.add(takeOff.getFlghitNo());
                successTakeOff++;
                this.totalTakeOffTime += takeOff.getWaitTime();

                if(!this.departure.isEmpty()) {
                    takeOff = departure.poll();
                    Flight.incTotalSuccessTakeOff();
                    departureList.add(takeOff.getFlghitNo());
                    successTakeOff++;
                    this.totalTakeOffTime += takeOff.getWaitTime();
                }
            }
            else{
                Flight takeOff;
                takeOff = departure.poll();
                Flight.incTotalSuccessTakeOff();
                departureList.add(takeOff.getFlghitNo());
                successTakeOff++;
                this.totalTakeOffTime += takeOff.getWaitTime();

                Flight landed = this.arrival.poll();
                this.totalLandTime += landed.getWaitTime();
                landed.setWaitTime(0);
                this.departure.add(landed);
                Flight.incTotalSuccessLand();
                successLand++;
            }
        }
        else{
            if(!this.arrival.isEmpty()) {
                Flight landed = this.arrival.poll();
                this.totalLandTime += landed.getWaitTime();
                landed.setWaitTime(0);
                this.departure.add(landed);
                Flight.incTotalSuccessLand();
                successLand++;
                if(!this.arrival.isEmpty()) {
                    landed = this.arrival.poll();
                    this.totalLandTime += landed.getWaitTime();
                    landed.setWaitTime(0);
                    this.departure.add(landed);
                    Flight.incTotalSuccessLand();
                    successLand++;
                }
            }
        }
    }

    private void printInfo(){
        System.out.println("/////////////////////////////////////////////////////");
        System.out.println("Interval number: " + this.time);
        System.out.println("Landing Size: " + this.arrival.size());
        System.out.println("Landing Queue: ");
        for(Flight flight: arrival){
            System.out.print("(" + flight.getFlghitNo() + " : " + flight.getFuel() + ") ");
        }
        System.out.println("\nDeparting Size: " + this.departure.size());
        System.out.println("Departing Queue: ");
        for(Flight flight: departure){
            System.out.print("(" + flight.getFlghitNo() + ") ");
        }
        System.out.println("\nSuccessful landing: " + successLand);
        System.out.println("Arriving flight: ");
        for(Integer flightNo: arrivingList){
            System.out.print("(" + flightNo + ") ");
        }
        System.out.println("\nSuccessful departed: " + successTakeOff);
        System.out.println("Departure flight: ");
        for(Integer flightNo: departureList){
            System.out.print("(" + flightNo + ") ");
        }
        System.out.println("\nCrashed: " + crashed);
    }

    private void clean(){
        //add crashed to totalCrash and clean crashed
        Flight.addToTotalCrashed(this.crashed);
        this.crashed = 0;
        //clean Airport.successLand and Airport.successTakeOff
        successLand = successTakeOff = 0;
        //clean arrivingList and departureList
        arrivingList.clear();
        departureList.clear();
    }

    private void endPrint(){
        //calculate average and print out
        System.out.println("////////////////////////end//////////////////////////");
        System.out.println("Average Depart length: " + (this.totalLengthDeparture / this.time));
        System.out.println("Average Arrival length: " + (this.totalLengthArrival / this.time) + "\n");
        System.out.println("Average TakeOff time: " + (this.totalTakeOffTime / Flight.getTotalSuccessTakeOff()));
        System.out.println("Average Land time: " + (this.totalLandTime / Flight.getTotalSuccessLand()) + "\n");
        System.out.println("Total Success TakeOff: " + Flight.getTotalSuccessTakeOff());
        System.out.println("Total Success Land: " + Flight.getTotalSuccessLand());
        System.out.println("Total Success Crash: " + Flight.getTotalCrashed());

    }

    private void lengthCal(){
        this.totalLengthArrival += arrival.size();
        this.totalLengthDeparture += departure.size();
    }

    public PriorityQueue<Flight> getArrival() {
        return arrival;
    }

    public void setArrival(PriorityQueue<Flight> arrival) {
        this.arrival = arrival;
    }

    public LinkedBlockingQueue<Flight> getDeparture() {
        return departure;
    }

    public void setDeparture(LinkedBlockingQueue<Flight> departure) {
        this.departure = departure;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
