package bigdata.algorithms;

import bigdata.algorithms.Entities.Algorithm;

import java.util.LinkedList;

/**
 * Implementation for the tendency - based prediction strategy.
 * 
 * @author Andreea
 */

public class Tendency_based extends Algorithm
{
	/**
	 * Increase tendency constant.
	 * 
	 * @type integer
	 * @access public 
	 * @static
	 */
	
	public static final int INCREASE = 0;
	
	/**
	 * Decrease tendency constant.
	 * 
	 * @type integer
	 * @access public 
	 * @static
	 */
	
	public static final int DECREASE = 1;
	
	/**
	 * The decrement value used in prediction.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double decrementValue;
	
	/**
	 * The increment value used in prediction.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double incrementValue;
	
	/**
	 * The time series tendency.
	 * 
	 * @type integer
	 * @access private 
	 * @static
	 */
	
	private static int tendency;
	
	/**
	 * The adapt degree used in prediction.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double adaptDegree = 0.75;
	
	/**
	 * Predicts the next value using the tendency - based prediction strategy.
	 * 
	 * @param series  the time series
	 * @return double  the next value
	 */
	
	public static double predict(LinkedList<Double> series)
	{
		double next = 0;
		int lenght = series.size();
		
		if(series.get(lenght-2) >= series.get(lenght-3))
		{
			tendency = Tendency_based.INCREASE;
		}
		else
		{
			tendency = Tendency_based.DECREASE;
		}
		
		if(tendency == Tendency_based.INCREASE)
		{
			incrementValue = adaptIncrementValue(series);
			next = series.get(lenght-2) + incrementValue;
		}
		else if(tendency == Tendency_based.DECREASE)
		{
			decrementValue = adaptDecrementValue(series);
			next = series.get(lenght-2) - decrementValue;
		}
		return next;
	}
	
	/**
	 * Adapts the increment value according to the tendency.
	 * 
	 * @param series  the real time series
	 * @return double  the new increment value
	 */
	
	private static double adaptIncrementValue(LinkedList<Double> series)
	{
		double mean = 0;
		for(int i = 0 ; i < series.size() - 1 ; i ++)
			mean += series.get(i);
		
		mean /= series.size() - 1;
		
		double realIncValue = series.get(series.size() - 1) - series.get(series.size() - 2);
		double normalInc = incrementValue + (realIncValue - incrementValue) * adaptDegree;
		if(series.get(series.size() - 1) < mean)
			incrementValue = normalInc;
		else
		{
			int pastGreater = 0;
			for(int i = 0 ; i < series.size() - 1 ; i ++)
				if(series.get(i) > series.get(series.size() - 2))
					pastGreater ++;
			pastGreater /= series.size() - 1;
			double turningPointInc = incrementValue * pastGreater;
			incrementValue = Math.min(Math.abs(normalInc), Math.abs(turningPointInc));
		}

		return incrementValue;
	}
	
	/**
	 * Adapts the decrement value according to the tendency.
	 * 
	 * @param series  the real time series
	 * @return double  the new decrement value
	 */
	
	private static double adaptDecrementValue(LinkedList<Double> series)
	{
		double mean = 0;
		for(int i = 0 ; i < series.size() - 1 ; i ++)
			mean += series.get(i);
		
		mean /= series.size() - 1;
		
		double realDecValue = series.get(series.size() - 2) - series.get(series.size() - 1);
		double normalDec = decrementValue + (realDecValue - decrementValue) * adaptDegree;
		if(series.get(series.size() - 1) > mean)
			decrementValue = normalDec;
		else
		{
			int pastSmaller = 0;
			for(int i = 0 ; i < series.size() - 1 ; i ++)
				if(series.get(i) < series.get(series.size() - 2))
					pastSmaller ++;
			pastSmaller /= series.size() - 1;
			double turningPointInc = decrementValue * pastSmaller;
			decrementValue = Math.min(Math.abs(normalDec), Math.abs(turningPointInc));
		}

		return decrementValue;
	}
	
	/**
	 * Returns the decrement value.
	 * 
	 * @return double the decrement value
	 */
	
	public double getDecrementValue()
	{
		return decrementValue;
	}
	
	/**
	 * Sets the decrement value.
	 * 
	 * @param d  the new decrement value
 	 */
	
	public void setDecrementValue(double d)
	{
		decrementValue = d;
	}
	
	/**
	 * Returns the increment value.
	 * 
	 * @return double the increment value
	 */
	
	public double getIncrementValue()
	{
		return incrementValue;
	}
	
	/**
	 * Sets the increment value.
	 * 
	 * @param i  the new increment value
	 */
	
	public void setIncrementValue(double i)
	{
		incrementValue = i;
	}
	
	/**
	 * Returns the adapt degree used in prediction.
	 * 
	 * @return double  the adapt degree
	 */
	
	public double getAdaptDegree()
	{
		return adaptDegree;
	}
	
	/**
	 * Sets the value of the adapt degree.
	 * 
	 * @param a  the new value of the adapt degree
	 */
	
	public void setAdaptDegree(double a)
	{
		adaptDegree = a;
	}
}
