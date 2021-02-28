/*
Name: Scott Lonsdale
Course: Seng2200
Student Number: C3303788
*/
import java.util.PriorityQueue;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.ArrayList;

public class interStageQueue //the storage queues between each state
{
    private Queue<Item> isqueue; 
	private double averageItems;
	private double totalTime;
	private String isQueueName;

	//constructor
	public interStageQueue(String isQueueName) 
	{
		averageItems = 0;
		totalTime = 0;
		isqueue = new LinkedList<Item>();
		this.isQueueName = isQueueName;
	}

	//basic getters
	public int size() {return isqueue.size();}
	public String getName() {return isQueueName;}

	public void add(Item input, double totalTime) 
	{
		isqueue.add(input);
		averageItems += (totalTime - this.totalTime) * (isqueue.size() - 1);
		this.totalTime = totalTime;
		input.startQueue(totalTime);
	}

	public Item remove(double totalTime)
	{
		Item output = isqueue.remove();
		averageItems += (totalTime - this.totalTime) * (isqueue.size() + 1);
		this.totalTime = totalTime;
		output.endQueue(totalTime);
		return output;
	}

	public double getAverageItems(double totalTime) {
		double totalAverage = averageItems + (totalTime - this.totalTime) * isqueue.size();
		totalAverage /= totalTime;
		return totalAverage;
	}

}