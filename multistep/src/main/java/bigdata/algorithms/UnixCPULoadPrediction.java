package bigdata.algorithms;

import bigdata.algorithms.Entities.Algorithm;

import java.util.LinkedList;

/**
 * Implementation of the prediction according to sched.h (UNIX kernel).
 * 
 * @author Andreea
 */

public class UnixCPULoadPrediction extends Algorithm
{
	/**
	 * The constant smoothing factor.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double ex = (double)12 / 13;//Math.exp(-5/60);
	
	/**
	 * The second constant smoothing factor.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double dif_ex = 1 - (double) 12 / 13;//Math.exp(-5/60);
	
	
	/**
	 * Computes the next value for the time series using the information offered in the UNIX kernel.
	 * 
	 * @param series  the real time series
	 * @static
	 * @return double  the predicted next value
	 */
	
	public static double predict(LinkedList<Double> series)
	{
		double next = 0, sum;
	
		EMA.setAlpha(0.55);
		sum = EMA.predict(series);
		EMA.setAlpha(0.50);	
		next+=  series.get(series.size() - 1) * ex + sum * dif_ex;
		return next;
	}
		
}
