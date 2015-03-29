package jp;

import java.util.*;

/**
 * Created by JasonPan on 3/29/15.
 */
public class Airport {
    private PriorityQueue<Flight> arrival = new PriorityQueue<Flight>();
    private PriorityQueue<Flight> departure = new PriorityQueue<Flight>();
    private int count;
    private int crashed;
    private int totalCrash;

    Airport(){
        this.count = 100;
        this.crashed = 0;
        this.totalCrash = 0;
    }

    Airport(int count) {
        this.count = count;
        this.crashed = 0;
        this.totalCrash = 0;
    }

    public void start(){


    }

    public void makeFlight(){
        //random make 0 ~ 5 flight
        Random rand = new Random();
        int num = rand.nextInt(5);

        for(int i = 0; i < num; i++){
            arrival.add(new Flight());
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
        ArrayList<Flight> col = new ArrayList<Flight>();
        for(Flight flight: arrival) {
            flight.decFuel();
            if(flight.getFuel() <= 0){
                crashed++;
                col.add(flight);
            }
        }
        arrival.removeAll(col);
    }

    private void cleanCrash(){
        //add crashed to totalCrash and clean crashed
        this.totalCrash += this.crashed;
        this.crashed = 0;
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
