/*
Name: Scott Lonsdale
Course: Seng2200
Student Number: C3303788
*/

import java.util.Scanner;
import java.io.*;  

public class PA3
{
	public static void main(String[] args) throws IOException
	{
		Scanner input = new Scanner(System.in);
		double m;
		double n;
		int qmax;

		m = Double.parseDouble(args[0]); //parses m, n, and qmax from user input
        n = Double.parseDouble(args[1]);
        qmax = Integer.parseInt(args[2]);

        if (qmax < 1) {System.out.println("Invalid input, QMax must be greater than 1");}
        else
        {
        	Timeline tl = new Timeline(qmax, m, n, 10000000); //inputs the given values and a set time limit for the timeline
        	tl.runTimeline(); //runs then prints the desired outputs
        	tl.printStats();
 
        }

        input.close();
    }
}