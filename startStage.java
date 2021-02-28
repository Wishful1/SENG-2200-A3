/*
Name: Scott Lonsdale
Course: Seng2200
Student Number: C3303788
*/
import java.util.Random;
import java.util.ArrayList;

public class startStage extends Stage
{
	private interStageQueue output; //this is the next storage queue
	private int idNumber; 

	//initalises with the user inputted variables and the needed output
	public startStage(String stageName, interStageQueue output, double mean, double range, int qmax)
	{
		blocked = false;
        starved = false;
        processing = false;
		totalTimeStarved = 0;
	    totalTimeBlocked = 0;
	    totalTimeProcessing = 0;
	    r = new Random();
	    this.output = output;
	    this.qmax = qmax;
	    this.mean = mean;
	    this.range = range;
	    this.stageName = stageName;
	    currentBlockStart = Double.NaN;
	    currentStarveStart = Double.NaN;
	    currentProcessingEnd = 0;
	    mostRecentItem = null;
	    idNumber = 0;
	    connectedStages = new ArrayList<Stage>();

	}

	@Override

	public Event processItem(double totalTime)
	{
        if (totalTime < currentProcessingEnd) {return null;} //checks if the current Stage is processing, and sets the processing to false if it isn't
        else {processing = false;}

        if(blocked && output.size() == qmax) {return null;} //checks if the next queue is full and the stage is currently blocked, returning null if it is

        if (mostRecentItem != null) //checks if this is the first process, if it isn't the most recent item is moved to the next storage queue
        {
        	if (output.size() == qmax) //if the next queue is full, a block is started switching the variable on
        	{
        		currentBlockStart = totalTime;
        		blocked = true;
        		return null;
        	} 
        	else {output.add(mostRecentItem, totalTime);} //if not, the most recect item is added to the next storage queue
        }
        
        
        mostRecentItem = new Item(stageName, idNumber); //creates a new item, with an id that iterates and adds the start state to the end of the ID
        mostRecentItem.addStopOnPath(stageName); //updates the path array inside Item
        idNumber++;
        
        if (blocked == true) //if the stage was originally blocked, itis switched back to not blocked and appended the total time blocked
        {
        	blocked = false;
        	totalTimeBlocked += totalTime - currentBlockStart;
        	currentBlockStart = Double.NaN;
        }
        
        double processTime = mean + (range * (r.nextDouble() - 0.5)); //calculates the processing time
        totalTimeProcessing += processTime; //adds to the total processing time
        currentProcessingEnd = totalTime + processTime; //creates the endpoint for the current processing time
        return new Event(this, totalTime, currentProcessingEnd); //returns the event of the process
	}
}