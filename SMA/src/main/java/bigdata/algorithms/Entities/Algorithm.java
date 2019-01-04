package bigdata.algorithms.Entities;

import java.util.LinkedList;

/**
 * Generic class for a prediction algorithm.
 * 
 * @author Andreea
 */

public class Algorithm 
{
	/**
	 * The window's length used for multi-step ahead prediction. (number of future values that we want to predict)
	 * 
	 * @type integer 
	 * @access public 
	 * @static 
	 */
	
	public static int multiStepWindow = 5;
	
	/**
	 * The window's length used for prediction.
	 * 
	 * @type integer 
	 * @access public 
	 * @static
	 */
	
	public static int window = 5;
	
	/**
	 * Computes the normalized mean squared error - NMSE. 
	 * 
	 * @param real  the measured time series 
	 * @param predicted  the predicted time series
	 * @static
	 * @return double  the normalized mean squared error
	 */
	
	public static double computeNMSE(LinkedList<Double> real, LinkedList<Double> predicted)
	{
		double error = 0;
		double sample_variance = 0; 
		double mean = 0;
		for(int i = 0 ; i < real.size() ; i ++)
		{
			error += Math.pow(real.get(i) - predicted.get(i),2);
			mean += real.get(i);
		}
		
		mean /= real.size();
		for(int i = 0 ; i < real.size() ; i ++)
		{
			sample_variance += Math.pow(real.get(i) - mean, 2);
		}
		
		sample_variance /= real.size();
		
		error /= real.size() * sample_variance;
		return error;
	}
	
	/**
	 * Computes the cross product for the real and predicted time series.
	 * 
	 * @param real  the measured time series 
	 * @param predicted  the predicted time series
	 * @static
	 * @return double  the normalized mean squared error
	 */

	public static double crossProduct(LinkedList<Double> real, LinkedList<Double> predicted)
	{
		double sum = 0;
		double mean = 0;
		for(int i = 0 ; i < real.size() ; i ++)
		{
			mean += real.get(i);
		}
		mean /= real.size();
		for(int i = 0 ; i < real.size() ; i ++)
		{
			sum += Math.abs(predicted.get(i) - real.get(i)) * Math.abs(mean - real.get(i)) * Algorithm.weight(i);
		}
		return sum;
	}
	
	/**
	 * Computes the weight for a specified position used in cross product.
	 * 
	 * @param position  the position
	 * @static
	 * @return double   the weight for the specified position
	 */
	
	public static double weight(int position)
	{
		return 1;
	}
	
	/**
	 * Computes the error for the real and predicted values.
	 * 
	 * @param real  the real value
	 * @param predicted  the predicted value
	 * @static
	 * @return double   the error
	 */
	
	public static double getError(double real, double predicted)
	{
		return Math.abs((real - predicted)/real);
	}
	
	/**
	 * Computes the prediction accuracy.
	 * 
	 * @param real  the real value
	 * @param predicted	 the predicted value
	 * @static
	 * @return double the prediction accuracy
	 */
	
	public static double getAccuracy(double real, double predicted)
	{
		double e = getError(predicted, real);
		
		if ( e >= 1 ) 
			return 0.0;
		else
			return 1 - e;
	}
	
	/**
	 * Computes the general accuracy for a real and a predicted time series.
	 * 
	 * @param real  the real time series
	 * @param predicted  the predicted time series
	 * @static
	 * @return double  the general accuracy
	 */
	
	public static double getGeneralAccuracy(LinkedList<Double> real, LinkedList<Double> predicted)
	{
		double sum = 0;
		for(int i = 0 ; i < real.size() ; i ++)
		{
			sum += getAccuracy(real.get(i), predicted.get(i));
		}
		return sum;
	}
	
	/**
	 * Computes the percent error for a real and a predicted value.
	 * 
	 * @param real  the real value
	 * @param predicted	 the predicted value
	 * @static
	 * @return double  the percent error
	 */
	
	public static double getPercentError(double real, double predicted)
	{
		return Math.abs((real - predicted)/real)*100;
	}
	
	/**
	 * Computes the percent accuracy for a real and a predicted value.
	 * 
	 * @param real  the real value
	 * @param predicted  the predicted value
	 * @static
	 * @return double  the percent accuracy
	 */
	
	public static double getPercentAccuracy(double real, double predicted)
	{
		double e = getPercentError(predicted, real);
		
		if ( e >= 100 ) 
			return 0.0;
		else
			return 100 - e;
	}
	
	/**
	 * Computes the number of points where the tendency prediction is wrong.
	 * 
	 * @param real  the real value
	 * @param predicted  the predicted value
	 * @static
	 * @return integer  the number of tendency prediction mistakes
	 */
	
	public static int getTendencyMistakes(LinkedList<Double>real, LinkedList<Double> predicted)
	{
		int mistakes = 0;
		double realDelta, predictedDelta;
		
		for(int i = 0 ; i < real.size() - 1; i ++)
		{
			realDelta = real.get(i+1) - real.get(i);
			predictedDelta = predicted.get(i+1) - predicted.get(i);
			if((realDelta > 0 && predictedDelta < 0) || (realDelta < 0 && predictedDelta > 0))
				mistakes ++;
		}
		
		return mistakes;
	}
	
	/**
	 * General method use for time series prediction.
	 * 
	 * @param series the real time series
	 * @return double the predicted future value
	 */
	
	public static double predict(LinkedList<Double> series)
	{
		return 0;
	}
}
