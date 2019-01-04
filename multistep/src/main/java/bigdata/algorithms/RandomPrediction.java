package bigdata.algorithms;

import bigdata.algorithms.Entities.Algorithm;
import		javax.servlet.http.HttpServletRequest;
import		javax.servlet.http.HttpServletResponse;
import		javax.servlet.http.HttpServlet;
import java.io.*;
import java.util.LinkedList;

/**
 * Implementation for the random prediction algorithm.
 * 
 * @author Andreea
 */

public class RandomPrediction extends Algorithm
{


	/* Computes the next value for the time series using random prediction algorithm.
	 * 
	 * @param series  the real time series
	 * @static
	 * @return double  the predicted next value
	 */
	
	public static double predict(LinkedList<Double> series)
	{
		double average = 0, sigma = 0, nextValue = 0;
		
		for (int i=0; i<series.size(); i++)
			average += series.get(i);
		average /= series.size();
		
		for (int i=0; i<series.size(); i++)
			sigma += Math.pow((average - series.get(i)), 2);
		sigma /= series.size();
		sigma = Math.sqrt(sigma) ;
		
		nextValue = Math.random() * 2 * sigma + (average - sigma);
		return nextValue;
	}

}
