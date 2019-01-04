package bigdata.algorithms.Entities;

import bigdata.algorithms.Entities.Dtos.PredictedSeriesDto;

import java.util.LinkedList;

/**
 * Class used to store informations about the results of a prediction algorithm.
 * 
 * @author Andreea
 */

public class PredictedSeries 
{
	/**
	 * The algorithm name.
	 * 
	 * @type String
	 * @access private 
	 */
	
	private String algorithm;
	
	/**
	 * The list of the predicted values.
	 * 
	 * @type LinkedList<Double>
	 * @access private 
	 */
	
	private LinkedList<Double> values;
	
	/**
	 * Creates an object with the specified algorithm name.
	 * 
	 * @param algorithm  the algorithm name 
	 */
	
	public PredictedSeries(String algorithm)
	{
		this.algorithm = algorithm;
		this.values = new LinkedList<Double>();
	}
	
	public PredictedSeriesDto toDto(LinkedList<Double> inputValues, double nmseValue, double crossValue, boolean multiStep){
		return new PredictedSeriesDto(algorithm, inputValues, values, nmseValue, crossValue, multiStep);
	}
	
	/**
	 * Adds a value into the results list.
	 * 
	 * @param value  the value to be added
	 */
	
	public void addValue(double value)
	{
		this.values.add(value);
	}
	
	/**
	 * Removes the first element of the list
	 */
	
	public void removeFirst()
	{
		this.values.remove(0);
	}
	
	/**
	 * Returns the algorithm name.
	 * 
	 * @return String  the algorithm name
	 */
	
	public String getAlgorithm()
	{
		return this.algorithm;
	}
	
	/**
	 * Returns the list of the predicted time series.
	 * 
	 * @return LinkedList<Double>  the list of the predicted time series
	 */
	
	public LinkedList<Double> getValues()
	{
		return this.values;
	}

	/**
	 * Sets the algorithm's name.
	 * 
	 * @param string new algorithm's name
	 */
	
	public void setAlgorithm(String string) 
	{
		this.algorithm = string;
	}
}
