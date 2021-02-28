/*
Name: Scott Lonsdale
Course: Seng2200
Student Number: C3303788
*/

import java.util.Random;
import java.util.ArrayList;

public class midStage extends Stage
{
	private interStageQueue input; //this is the prev storage queue
	private interStageQueue output; //this is the next storage queue

	//initalises with the user inputted variables and the needed input and final ArrayList output
	public midStage(String stageName, interStageQueue input, interStageQueue output, double mean, double range, int qmax)
	{
		blocked = false;
        starved = true;
        processing = false;
		totalTimeStarved = 0;
	    totalTimeBlocked = 0;
	    totalTimeProcessing = 0;
	    r = new Random();
	    this.input = input;
	    this.output = output;
	    this.qmax = qmax;
	    this.mean = mean;
	    this.range = range;
	    this.stageName = stageName;
	    currentBlockStart = Double.NaN;
	    currentStarveStart = 0;
	    currentProcessingEnd = 0;
	    mostRecentItem = null;
	    connectedStages = new ArrayList<Stage>();

	}

	@Override

	public Event processItem(double totalTime)
	{
        if (totalTime < currentProcessingEnd) {return null;} //same as startState
        else {processing = false;}

        //check for both starved and blocked state and if the prev state is empty and next stage is full, returning null if it is
        if((starved && input.size() == 0) || (blocked && output.size() == qmax)) {return null;} 

        if (mostRecentItem != null) //exactly the same as startState
        {
        	if (output.size() == qmax) 
        	{
        		currentBlockStart = totalTime;
        		blocked = true;
        		return null;
        	} 
        	else {output.add(mostRecentItem, totalTime);}
        }

        if (input.size() == 0) //exactly the same as endState
        { 
			currentStarveStart = totalTime;
			mostRecentItem = null;
			starved = true;
			return null;
		}
        else {mostRecentItem = input.remove(totalTime);} //updates the path array inside Item

        mostRecentItem.addStopOnPath(stageName); //updates the path array inside Item
        
        if (starved == true) //if the process was orginally starved or blocked, the relevant boolean is updated and the relevant total value is updated
        {
        	starved = false;
        	totalTimeStarved += totalTime - currentStarveStart;
        	currentStarveStart = Double.NaN;
        }

        if (blocked == true)
        {
        	blocked = false;
        	totalTimeBlocked += totalTime - currentBlockStart;
        	currentBlockStart = Double.NaN;
        }
        
        double processTime = mean + (range * (r.nextDouble() - 0.5)); //calculates the processing time and updates the totalProcessing and processing end point values
        totalTimeProcessing += processTime;
        currentProcessingEnd = totalTime + processTime;
        return new Event(this, totalTime, currentProcessingEnd); //returns an event of the process 
	}
}