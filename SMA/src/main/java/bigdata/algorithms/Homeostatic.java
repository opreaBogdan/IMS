package bigdata.algorithms;

import bigdata.algorithms.Entities.Algorithm;

import java.util.LinkedList;

/**
 * Implementation for the homeostatic prediction strategy.
 * 
 * @author Andreea
 */

public class Homeostatic extends Algorithm
{
	/**
	 * Independent static strategy constant.
	 * 
	 * @type integer
	 * @access public 
	 * @static
	 */
	
	public static final int INDEPENDENT_STATIC = 0;
	
	/**
	 * Independent dynamic strategy constant.
	 * 
	 * @type integer
	 * @access public 
	 * @static
	 */
	
	public static final int INDEPENDENT_DYNAMIC = 1;
	
	/**
	 * Relative static strategy constant.
	 * 
	 * @type integer
	 * @access public 
	 * @static
	 */
	
	public static final int RELATIVE_STATIC = 2;
	
	/**
	 * Relative dynamic strategy constant.
	 * 
	 * @type integer
	 * @access public 
	 * @static
	 */
	
	public static final int RELATIVE_DYNAMIC = 3;
	
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
	 * The prediction strategy.
	 * 
	 * @type integer
	 * @access private 
	 * @static
	 */
	
	private static int strategy;
	
	/**
	 * The decrement constant used in prediction.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double decrementConstant = 0.15;
	
	/**
	 * The increment constant used in prediction.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double incrementConstant = 0.15;
	
	/**
	 * The decrement factor used in prediction.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double decrementFactor = 0.75;
	
	/**
	 * The increment factor used in prediction.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double incrementFactor = 0.75;
	
	/**
	 * The adapt degree used in prediction.
	 * 
	 * @type double
	 * @access private 
	 * @static
	 */
	
	private static double adaptDegree = 0.75;
	
	/**
	 * Creates an object with the default strategy.
	 */
		
	public Homeostatic()
	{
		strategy = Homeostatic.INDEPENDENT_STATIC;
	}
	
	/**
	 * Creates an object with the specified strategy.
	 * 
	 * @param strateg  the new strategy
	 */
	
	public Homeostatic(int strateg)
	{
		strategy = strateg;
	}
		
	/**
	 * Computes the next value for the time series using the homeostatic method.
	 * 
	 * @param series  the real time series
	 * @static
	 * @return double  the predicted next value
	 */
	
	public static double predict(LinkedList<Double> series)
	{
		double next;
		double sum=0, mean;
		
		for(int i = 0 ; i < series.size() - 1 ; i ++)
			sum += series.get(i);
		
		mean = sum / series.size() - 1;
		
		if(series.get(series.size()-2) > mean)
		{
			decrementValue = adaptDecrementValue(series.get(series.size() - 2), series.get(series.size() - 1));
			next = series.get(series.size() - 2) - decrementValue;
		}
		else if(series.get(series.size()-2) < mean)
		{
			incrementValue = adaptIncrementValue(series.get(series.size() - 2), series.get(series.size() - 1));
			next = series.get(series.size() - 2) + incrementValue;
		}
		else
			next = series.get(series.size() - 2);
		
		return next;
	}
	
	/**
	 * Adapts the decrement value according the last and the current value and the strategy.
	 * 
	 * @param last  the last (measured) value 
	 * @param current  the current (measured) value 
	 * @return double  the new decrement value
	 */
	
	private static double adaptDecrementValue(double last, double current)
	{
		if(strategy == Homeostatic.INDEPENDENT_STATIC)
		{
			return decrementConstant;
		}
		else if(strategy == Homeostatic.INDEPENDENT_DYNAMIC)
		{
			double realDecrementValue = last - current;
			decrementConstant += (realDecrementValue - decrementConstant) * adaptDegree;
			return decrementConstant;
		}
		else if(strategy == Homeostatic.RELATIVE_STATIC)
		{
			return last * decrementFactor;
		}
		else if(strategy == Homeostatic.RELATIVE_DYNAMIC)
		{
			
		}
		
		return 0;
	}
	
	/**
	 * Adapts the increment value according the last and the current value and the strategy.
	 * 
	 * @param last  the last (measured) value 
	 * @param current  the current (measured) value 
	 * @return double  the new increment value
	 */
	
	private static double adaptIncrementValue(double last, double current)
	{			
		if(strategy == Homeostatic.INDEPENDENT_STATIC)
		{
			return incrementConstant;
		}
		else if(strategy == Homeostatic.INDEPENDENT_DYNAMIC)
		{
			double realIncrementValue = current - last;
			incrementConstant += (realIncrementValue - incrementConstant) * adaptDegree;
			return incrementConstant;
		}
		else if(strategy == Homeostatic.RELATIVE_STATIC)
		{
			return last * incrementFactor;
		}
		else if(strategy == Homeostatic.RELATIVE_DYNAMIC)
		{
			
		}
		
		return 0;
	}
	
	/**
	 * Returns the adapt degree.
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
	 * @param a  the value of the adapt degree
	 */
	
	public void setAdaptDegree(double a)
	{
		adaptDegree = a;
	}
	
	/**
	 * Returns the value of the decrement factor.
	 * 
	 * @return double  the value of the decrement factor
	 */
	
	public double getDecrementFactor()
	{
		return decrementFactor;
	}
	
	/**
	 * Sets the value of the decrement factor.
	 * 
	 * @param d  the new value of the decrement factor
	 */
	
	public void setDecrementFactor(double d)
	{
		decrementFactor = d;
	}
	
	/**
	 * Returns the value of the increment factor.
	 * 
	 * @return double  the value of the increment factor
	 */
	
	public double getIncrementFactor()
	{
		return incrementFactor;
	}
	
	/**
	 * Sets the value of the increment factor.
	 * 
	 * @param d  the new value of the increment factor
	 */
	
	public void setIncrementFactor(double d)
	{
		incrementFactor = d;
	}
	
	/**
	 * Returns the value of the decrement constant.
	 * 
	 * @return double  the value of the decrement constant
	 */

	public double getDecrementConstant()
	{
		return decrementConstant;
	}

	/**
	 * Sets the value of the decrement constant.
	 * 
	 * @param d  the new value of the decrement constant
	 */
	
	public void setDecrementConstant(double d)
	{
		decrementConstant = d;
	}
	
	/**
	 * Returns the value of the increment constant.
	 * 
	 * @return double  the value of the increment constant
	 */
	
	public double getIncrementConstant()
	{
		return incrementConstant;
	}
	
	/**
	 * Sets the value of the increment constant.
	 * 
	 * @param d  the new value of the increment constant
	 */
	
	public void setIncrementConstant(double i)
	{
		incrementConstant = i;
	}
	
	/**
	 * Returns the prediction strategy.
	 * 
	 * @return integer  the strategy 
	 */
	
	public int getStrategy()
	{
		return strategy;
	}
	
	/**
	 * Sets the prediction strategy.
	 * 
	 * @param s  the new prediction strategy
	 */
	
	public void setStrategy(int s)
	{
		strategy = s;
	}

}
