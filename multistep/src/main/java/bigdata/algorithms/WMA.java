package bigdata.algorithms;

import bigdata.algorithms.Entities.Algorithm;

import java.util.LinkedList;

/**
 * Implementation for the weighted moving average algorithm.
 * 
 * @author Andreea
 */

public class WMA extends Algorithm
{
	/**
	 * Computes the next value for the time series using the weighted moving average algorithm.
	 * 
	 * @param series  the real time series
	 * @static
	 * @return double  the predicted next value
	 */
	
	public static double predict(LinkedList<Double> series)
	{
		double nextValue = 0;
		for (int i=0; i<series.size(); i++)
			nextValue += (i+1)*series.get(i);
		nextValue /= series.size()*(series.size()+1)/2;
		return nextValue;
	}
}
