package bigdata.algorithms.GA;

import java.util.LinkedList;
import java.util.Random;

/**
 * Class implementing basic operations for a chromosome.
 * 
 * @author Andreea
 */

public class Chromosome 
{
	/**
	 * The chromosome configuration representing the weights in the CasCor neural network. 
	 * 
	 * @type LinkedList<Double>
	 * @access private 
	 */
	
	private LinkedList<Double> configuration;
	
	/**
	 * The number of inputs. 
	 * 
	 * @type integer
	 * @access private 
	 */
	
	private int window;
	
	/**
	 * The chromosome fitness. 
	 * 
	 * @type double
	 * @access private 
	 */
	
	private double fitness;
	
	/**
	 * Constructs and returns a chromosome with default parameters.
	 */
	
	public Chromosome()
	{
		this.configuration =  new LinkedList<Double>();
		this.window = 0;
		this.fitness = -1;
	}
	
	/**
	 * Constructs and returns a chromosome with the specified configuration.
	 * 
	 * @param conf  the specified configuration
	 */
	
	public Chromosome(LinkedList<Double> conf)
	{
		this.configuration = new LinkedList<Double>();
		
		for(int i = 0 ; i < conf.size() ; i ++)
			this.configuration.add(conf.get(i));
		this.window = 0;
		this.fitness = -1;
	}
	
	/**
	 * Generates and returns a chromosome with the specified number of parameters (weights).
	 * 
	 * @param size  the number of inputs
	 */
	
	public Chromosome(int size)
	{
		this.configuration = new LinkedList<Double>();
		this.fitness = 100;
		
		for(int i = 0 ; i < size ; i ++)
		{
			Random rand = new Random();
			//values between -0.1 and 0.1
			double value =(double)(Math.abs(rand.nextInt())%10)/10;
			
			rand = new Random();
			int sign = (int)(Math.abs(rand.nextInt())%3);
			if(sign == 1)
				value = 0 - value;
			
			this.configuration.add(value);
		}
	}
	
	/**
	 * Returns the chromosome configuration.
	 * 
	 * @return LinkedList<Double> the chromosome configuration
	 */
	
	public LinkedList<Double> getConfiguration()
	{
		return this.configuration;
	}
	
	/**
	 * Changes the specified configuration value with a new value.
	 * 
	 * @param position  the position of the value we want to modify
	 * @param value  the new value
	 */
	
	public void changeValue(int position, double value)
	{
		this.configuration.remove(position);
		this.configuration.add(position, value);
	}
	
	/**
	 * Adds a new value in the chromosome configuration.
	 * 
	 * @param position  the position of the new value
	 * @param value  the added value 
	 */
	
	public void addValue(int position, double value)
	{
		this.configuration.add(position, value);
	}
	
	/**
	 * Computes the chromosome fitness according to the CasCor neural network inputs and the error. 
	 * 
	 * @param in  the CasCor network inputs
	 * @param err  the CasCor network errors
	 * @return double  the chromosome fitness
	 */
	
	public double computeFitness(LinkedList<Double> in, LinkedList<Double> err)
	{
		double fitness = 0, sum = 0;
		this.window = this.configuration.size();//in.size() - err.size();
		
		for(int i = 0 ; i < err.size() ; i ++)
		{
			LinkedList<Double> inputs = new LinkedList<Double>();
			for(int j = 0 ; j < this.window ; j ++)
				inputs.add(in.get(i + j));
			sum = computeCorrelation(inputs);
			fitness += Math.pow(sum - err.get(i),2);
		}
		 
		this.fitness = fitness;
		return fitness;
	}
	
	/**
	 * Computes the correlation between chromosome and inputs.
	 * 
	 * @param in  the CasCor network inputs
	 * @return double  the correlation value 
	 */
	
	private double computeCorrelation(LinkedList<Double> in)
	{
		double sum = 0;
		
		for(int i = 0 ; i < in.size() ; i ++)
		{
			sum += in.get(i) * this.configuration.get(i);
		}
		return sum;
	}
	
	/**
	 * Return the chromosome fitness.
	 * 
	 * @return double  the chromosome fitness
 	 */
	
	public double getFitness()
	{
		return this.fitness;
	}
	
	/**
	 * Sets the chromosome fitness.
	 * 
	 * @param fitness  the new chromosome fitness value
	 */
	
	public void setFitness(double fitness)
	{
		this.fitness = fitness;
	}
	
	/**
	 * Prints the chromosome parameters.
	 */
	
	public void print()
	{
		System.out.print(""+fitness+" ( ");
		for(int i = 0 ; i < this.configuration.size() ; i ++)
			System.out.print(this.configuration.get(i)+" ");
		System.out.println(")");
	}
	
	/**
	 * Clones the specified chromosome.
	 * 
	 * @param ch the chromosome we want to clone
	 */
	
	public void clone(Chromosome ch)
	{
		this.fitness = ch.fitness;
		this.window = ch.window;
		this.configuration = new LinkedList<Double>();
		for(int i = 0 ; i < ch.getConfiguration().size() ; i ++)
			this.configuration.add(ch.getConfiguration().get(i));
	}
}
