/*
Name: Scott Lonsdale
Course: Seng2200
Student Number: C3303788
*/
import java.util.Random;
import java.util.ArrayList;

public class endStage extends Stage
{
	private interStageQueue input; // this is the prev storage queue
	private ArrayList<Item> output; //this is the final output array

	//initalises with the user inputted variables and the needed input and output
	public endStage(String stageName, interStageQueue input, ArrayList<Item> output, double mean, double range, int qmax)  
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

        if(starved && input.size() == 0) {return null;} //checks if the prev queue is empty and the stage is currently starved, returning null if it is

        if (mostRecentItem != null) {output.add(mostRecentItem);} //as the output array is infinte, the process will never block

        if (input.size() == 0) //if the prev queue is empty, a starve is started switching the variable on
        { 
			currentStarveStart = totalTime;
			mostRecentItem = null;
			starved = true;
			return null;
		}
        else {mostRecentItem = input.remove(totalTime);} //esle the next item is taken fromt eh previous queue

        mostRecentItem.addStopOnPath(stageName); //updates the path array inside Item

        
        if (starved == true) //if the stage was originally starved, it is switched back to not starved and appended the total time starved
        {
        	starved = false;
        	totalTimeStarved += totalTime - currentStarveStart;
        	currentStarveStart = Double.NaN;
        }

        double processTime = mean + (range * (r.nextDouble() - 0.5)); //calculates the processing time and updates the totalProcessing and processing end point values
        totalTimeProcessing += processTime;
        currentProcessingEnd = totalTime + processTime;
        return new Event(this, totalTime, currentProcessingEnd); //returns an event of the process 
	}
}