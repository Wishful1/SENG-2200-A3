/*
Name: Scott Lonsdale
Course: Seng2200
Student Number: C3303788
*/
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayList;


public class Timeline 
{
    private double totalTime;
	private double timeLimit;
	private ArrayList<interStageQueue> tlQueues;
	private ArrayList<Item> finished; //the queue which will contain the Item objects that has finished production
	private ArrayList<Stage> tlStages;
	private PriorityQueue<Event> tlEvents;
	//private List<String> possiblePaths;
	//private List<String> possiblePathNames;

	public Timeline(int qmax, double m, double n, double timeLimit)
	{
		totalTime = 0;
		this.timeLimit = timeLimit;
		tlQueues = new ArrayList<interStageQueue>();
		tlStages = new ArrayList<Stage>();
		tlEvents = new PriorityQueue<Event>();
		finished = new ArrayList<Item>();
		interStageQueue q01 = new interStageQueue("q01");
		interStageQueue q12 = new interStageQueue("q12");
		interStageQueue q23 = new interStageQueue("q23");
		interStageQueue q34 = new interStageQueue("q34");
		interStageQueue q45 = new interStageQueue("q45");
		interStageQueue q56 = new interStageQueue("q56");
		Stage S0A = new startStage("S0A", q01, 2 * m, 2 * n, qmax);
		Stage S0B = new startStage("S0B", q01, m, n, qmax);
		Stage S1 = new midStage("S1", q01, q12, m, n, qmax);
		Stage S2 = new midStage("S2", q12, q23, m, n, qmax);
		Stage S3A = new midStage("S3A", q23, q34, 2 * m, 2 * n, qmax);
		Stage S3B = new midStage("S3B", q23, q34, 2 * m, 2 * n, qmax);
		Stage S4 = new midStage("S4", q34, q45, m, n, qmax);
		Stage S5A = new midStage("S5A", q45, q56, 2 * m, 2 * n, qmax);
		Stage S5B = new midStage("S5B", q45, q56, 2 * m, 2 * n, qmax);
		Stage S6 = new endStage("S6", q56, finished, m, n, qmax);
		tlQueues.add(q01);
		tlQueues.add(q12);
		tlQueues.add(q23);
		tlQueues.add(q34);
		tlQueues.add(q45);
		tlQueues.add(q56);
		tlStages.add(S0B);
		tlStages.add(S0A);
		tlStages.add(S1);
		tlStages.add(S2);
		tlStages.add(S3A);
		tlStages.add(S3B);
		tlStages.add(S4);
		tlStages.add(S5A);
		tlStages.add(S5B);
		tlStages.add(S6);
		S0A.connectStage(S1);
		S0B.connectStage(S1);
		S1.connectStage(S0A); 
		S1.connectStage(S0B);
		S1.connectStage(S2);
		S2.connectStage(S1);
		S2.connectStage(S3A);
		S2.connectStage(S3B);
		S3A.connectStage(S2);
		S3A.connectStage(S4);
		S3B.connectStage(S2);
		S3B.connectStage(S4);
		S4.connectStage(S3A);
		S4.connectStage(S3B);
		S4.connectStage(S5A);
		S4.connectStage(S5B);
		S5A.connectStage(S4);
		S5A.connectStage(S6);
		S5B.connectStage(S4);
		S5B.connectStage(S6);
		S6.connectStage(S5A);
		S6.connectStage(S5B);
	}

	public void runTimeline() 
	{
	
		tlEvents.add(tlStages.get(0).processItem(totalTime));
		while (true) 
		{
			Event mostRecentEvent = tlEvents.remove();
			if (mostRecentEvent.getReadyTime() >= timeLimit) 
			{
				//System.out.println("rgsgsdfgsdf");
				break;
			}
			else 
			{
				totalTime = mostRecentEvent.getReadyTime();
				//System.out.println("brrrrrrrr");
			}

			Event nextEvents = mostRecentEvent.getStage().processItem(totalTime); 
			if (nextEvents != null) 
			{
				//System.out.println("33333");
				tlEvents.add(nextEvents);
			}
			
			ArrayList<Stage> connectedStages = mostRecentEvent.getStage().getConnectedStages();
			for (int i = 0; i < connectedStages.size(); i++) 
			{ 
				nextEvents = connectedStages.get(i).processItem(totalTime);
				if (nextEvents != null) 
				{
					//System.out.println("5555553");
					tlEvents.add(nextEvents);
				}
			}
		}
	}

	public void printStats()
	{
		System.out.format("%-8s%-18s%-18s%-18s", "Stage:", "Work[%]", "Starve[t]", "Block[t]");
		for (int i = 0; i < tlStages.size(); i++) 
		{
            System.out.print("\n");
            Stage outputStage = tlStages.get(i);
			System.out.format("%-8s%-18.2f%-18.2f%-18.2f", outputStage.getName(), outputStage.getTotalTimeProcessingPercent(timeLimit), outputStage.getTotalTimeStarved(timeLimit), outputStage.getTotalTimeBlocked(timeLimit));
		}
		System.out.print("\n");
		System.out.print("\n");
		System.out.format("%-8s%-18s%-18s", "Store:", "AverageTime[t]", "AverageItems");
		for (int i = 0; i < tlQueues.size(); i++) 
		{
			interStageQueue currentQueue = tlQueues.get(i);
			double totalWaitTime = 0;
			for (int j = 0; j < finished.size(); j++) 
			{
				Item currentItem = finished.get(j);
				totalWaitTime += currentItem.getWaitTime(i);
			} 
			double averageWaitTime =  totalWaitTime / finished.size(); 
			System.out.print("\n");
			System.out.format("%-8s%-18.2f%-18.2f", currentQueue.getName(), averageWaitTime, currentQueue.getAverageItems(totalTime));
		}
		System.out.print("\n");
		System.out.print("\n");
		System.out.format("Production Paths");
		int aItems = 0;
		int bItems = 0;
		int s3As5AItems = 0;
		int s3As5BItems = 0;
		int s3Bs5AItems = 0;
		int s3Bs5BItems = 0;
		for (int i = 0; i < finished.size(); i++)
		{
            ArrayList path = finished.get(i).getPath();
            boolean a3 = false, b3 = false, a5 = false, b5 = false;
            for (int j = 0; j < path.size(); j++)
            {

                if (path.get(j) == "S0A") {aItems++;}
                else if (path.get(j) == "S0B") {bItems++;}

                if (path.get(j) == "S3A") {a3 = true;}
                if (path.get(j) == "S3B") {b3 = true;}
                if (path.get(j) == "S5B") {b5 = true;}
                if (path.get(j) == "S5A") {a5 = true;}

                if(a3 && a5) {s3As5AItems++;}
                if(a3 && b5) {s3As5BItems++;}
                if(b3 && a5) {s3Bs5AItems++;}
                if(b3 && b5) {s3Bs5BItems++;}
            }
		}
		System.out.print("\n");
		System.out.format("%-8s%-18s", "S0A:", aItems);
		System.out.print("\n");
		System.out.format("%-8s%-18s", "S0B:", bItems);
		System.out.print("\n");
		System.out.format("%-8s%-18s", "3a->5a:", s3As5AItems);
		System.out.print("\n");
		System.out.format("%-8s%-18s", "3a->5b:", s3As5BItems);
		System.out.print("\n");
		System.out.format("%-8s%-18s", "3b->5a:", s3Bs5AItems);
		System.out.print("\n");
		System.out.format("%-8s%-18s", "3b->5b:", s3Bs5BItems);
		System.out.print("\n");



	}
}