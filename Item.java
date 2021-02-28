/*
Name: Scott Lonsdale
Course: Seng2200
Student Number: C3303788
*/

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayList;

public class Item
{
	private ArrayList<Double> waitTimes;
	private ArrayList<String> pathTaken;
    private double queueWaitStart;
    private String id;

    public Item(String startState, int idNum) 
    {
        waitTimes = new ArrayList<Double>();
        pathTaken = new ArrayList<String>();
        queueWaitStart = Double.NaN; //has not entered any queues yet
        id = Integer.toString(idNum) + startState.charAt(2);
    }

    public double getWaitTime(int queueIndex) {return waitTimes.get(queueIndex);}
    public void startQueue(double totalTime) {queueWaitStart = totalTime;}

    public void endQueue(double totalTime)
    {
    	waitTimes.add(totalTime - queueWaitStart);
        queueWaitStart = Double.NaN;
    }

    public String getID() {return id;}
    public void addStopOnPath(String stageName) {pathTaken.add(stageName);}
    public ArrayList getPath() {return pathTaken;}

    


}