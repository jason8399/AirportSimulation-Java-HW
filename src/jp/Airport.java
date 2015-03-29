package jp;

import java.util.*;

/**
 * Created by JasonPan on 3/29/15.
 */
public class Airport {
    private PriorityQueue<Flight> arrival = new PriorityQueue<>();
    private PriorityQueue<Flight> departure = new PriorityQueue<>();
    private ArrayList<Integer> arrivingList = new ArrayList<>();
    private ArrayList<Integer> departureList = new ArrayList<>();
    private int count;
    private int crashed;
    private int time;
    private int successTakeOff;
    private int successLand;

    Airport(){
        this.count = 100;
        this.crashed = 0;
        this.time = 0;
        this.successLand = 0;
        this.successTakeOff = 0;
    }

    Airport(int count) {
        this.count = count;
        this.crashed = 0;
        this.time = 0;
        this.successLand = 0;
        this.successTakeOff = 0;
    }

    public void start(){
        for(time = 0; time < this.count; time++){
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
            for(int j = 0; j < 1; j++)
                landTakeOffProcess();
            incAllWaitTime();
            decAllFuel();
            printInfo();
            clean();
        }
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
            }
            else{
                Flight landed = this.arrival.poll();
                landed.setWaitTime(0);
                this.departure.add(landed);
                Flight.incTotalSuccessLand();
                successLand++;
            }
        }
        else{
            if(!this.arrival.isEmpty()) {
                Flight landed = this.arrival.poll();
                landed.setWaitTime(0);
                this.departure.add(landed);
                Flight.incTotalSuccessLand();
                successLand++;
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

    public void clean(){
        //add crashed to totalCrash and clean crashed
        Flight.addToTotalCrashed(this.crashed);
        this.crashed = 0;
        //clean Airport.successLand and Airport.successTakeOff
        successLand = successTakeOff = 0;
        //clean arrivingList and departureList
        arrivingList.clear();
        departureList.clear();
    }

    public void endPrint(){
        //TODO calculate average and print out
    }

    public PriorityQueue<Flight> getArrival() {
        return arrival;
    }

    public void setArrival(PriorityQueue<Flight> arrival) {
        this.arrival = arrival;
    }

    public PriorityQueue<Flight> getDeparture() {
        return departure;
    }

    public void setDeparture(PriorityQueue<Flight> departure) {
        this.departure = departure;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
