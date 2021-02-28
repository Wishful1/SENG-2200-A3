/*
Name: Scott Lonsdale
Course: Seng2200
Student Number: C3303788
*/

public class Event implements Comparable<Event>
{
    private Stage stage;
	private double creationTime;
	private double readyTime;

	public Event(Stage stage, double creationTime, double readyTime) 
	{
		this.stage = stage;
		this.creationTime = creationTime;
		this.readyTime = readyTime;
	}

	public Stage getStage() {return stage;}
	public double getCreationTime() {return creationTime;}
	public double getReadyTime() {return readyTime;}

	@Override
	
	public int compareTo(Event cEvent) 
	{
		if (getReadyTime() < cEvent.getReadyTime()) {return -1;}
		else if (getReadyTime() > cEvent.getReadyTime()) {return 1;}
		else {return 0;}
	}

}