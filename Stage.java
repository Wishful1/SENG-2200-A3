/*
Name: Scott Lonsdale
Course: Seng2200
Student Number: C3303788
*/
import java.util.Random;
import java.util.ArrayList;

public abstract class Stage
{
	protected String stageName;
	protected boolean blocked;
	protected boolean starved;
	protected boolean processing;
    protected double mean;
    protected double range;
    protected double totalTimeStarved;
	protected double totalTimeBlocked;
	protected double totalTimeProcessing;
	protected double currentBlockStart;
	protected double currentStarveStart;
    protected double currentProcessingStart;
	protected double currentProcessingEnd;
	protected int qmax;
	protected Item mostRecentItem;
	protected Random r;
	protected ArrayList<Stage> connectedStages;

	//the main difference between each of the inherited classes if how they handle proccesing items, so it is the only abstract method
	public abstract Event processItem(double totalTime); 

	
    //basic getters
	public String getName() {return stageName;}
	public ArrayList<Stage> getConnectedStages() {return connectedStages;}

	
    //returns the total time the stage is starved, unless the stage is currently starved then it also adds the time after the curerntStarveStart
	public double getTotalTimeStarved(double totalTime) 
	{
		if (starved == true) {return totalTimeStarved + (totalTime - currentStarveStart);}
		return totalTimeStarved;
	}

	//returns the total time the stage is blocked, unless the stage is currently blocked then it also adds the time after the curerntBlockStart
	public double getTotalTimeBlocked(double totalTime) 
	{
		if (blocked == true) {return totalTimeBlocked + (totalTime - currentBlockStart);}
		return totalTimeBlocked;
	}

	//returns the total average time the stage is processing, unless the stage is currently processing then it also removes the time predicted till the end of the process
	public double getTotalTimeProcessingPercent(double totalTime)
	{
        double finalProcessTime = totalTimeProcessing;
		if (processing == true) {finalProcessTime -= currentProcessingEnd - totalTime;}
		return (finalProcessTime / (totalTimeProcessing + this.getTotalTimeBlocked(totalTime) + this.getTotalTimeStarved(totalTime))) * 100;
	}

	//adds a connected stages to the current stage
	public void connectStage(Stage stageToConnect) {connectedStages.add(stageToConnect);}





}