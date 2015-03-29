package jp;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by JasonPan on 3/29/15.
 */
public class Airport {
    private PriorityQueue<Flight> arrival = new PriorityQueue<Flight>();
    private PriorityQueue<Flight> departure = new PriorityQueue<Flight>();
    private int count;

    Airport(){
        this.count = 100;
    }

    Airport(int count) {
        this.count = count;
    }

    public void start(){


    }

    public void makeFlight(){
        Random rand = new Random();
        int num = rand.nextInt(5);

        for(int i = 0; i < num; i++){
            arrival.add(new Flight());
        }
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
