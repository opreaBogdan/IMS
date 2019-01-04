package bigdata.algorithms;

import bigdata.algorithms.Entities.Algorithm;

import java.util.LinkedList;

/**
 * Implementation for the exponential moving average algorithm.
 * 
 * @author Andreea
 */

public class EMA extends Algorithm
{
	/**
	 * The constant smoothing factor.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double alpha = 0.20;
	
	/**
	 * The last value of the time series.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double S0 = 0;
	
	/**
	 * Sets the value for alpha - constant smoothing factor.
	 * 
	 * @param alp  the new value for alpha
	 */

	public static void setAlpha(double alp)
	{
		alpha = alp;	
	}
	
	/**
	 * Returns the value for alpha.
	 * 
	 * @return double  the value for alpha
	 */
	
	public static double getAlpha()
	{
		return alpha;
	}
	
	/**
	 * Computes the next value for the time series using the exponential moving average algorithm.
	 * 
	 * @param series  the real time series
	 * @static
	 * @return double  the predicted next value
	 */

	public static double predict(LinkedList<Double> series)
	{
		double nextValue = 0;
		double lastValue = 0;
		if (S0 == 0)
		{
			for (int i=0; i<series.size()-1; i++)
				S0 += series.get(i);
			S0 /= series.size()-1;	
		}
		lastValue = series.get(series.size()-1);
		nextValue = S0 + alpha * (lastValue - S0);
		S0 = nextValue;
		return nextValue;
	}
}
