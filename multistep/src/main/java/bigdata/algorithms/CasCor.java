package bigdata.algorithms;

import bigdata.algorithms.Entities.Algorithm;
import bigdata.algorithms.GA.Population;
import bigdata.utils.Constants;

import java.util.LinkedList;

/**
 * Class implementing the CasCor neural network used for time series prediction.
 * 
 * @author Andreea
 */

public class CasCor extends Algorithm
{
	/**
	 * The genetic population used to compute fittest weights for hidden units.
	 * 
	 * @access private
	 * @type Population
	 */
	
	private static Population population;
	
	/**
	 * Constant representing the state of a frozen weight.
	 * 
	 * @access private
	 * @type integer
	 * @static
	 */
	
	private static final int FROZEN = 0;
	
	/**
	 * Constant representing the state of a trained weight.
	 * 
	 * @access private
	 * @type integer
	 * @static
	 */
	
	private static final int TRAINED = 1;
	
	/**
	 * Constant representing the maximum number of candidate units that the network can contain.
	 * 
	 * @access private
	 * @type integer
	 * @static
	 */
	
	private static final int MAX_NO_HIDDEN_LAYERS = 200;
	
	/**
	 * Constant representing the output's position in the representation.
	 * 
	 * @access private
	 * @type integer
	 * @static
	 */
	
	private static final int OUTPUT = 0;
	
	/**
	 * Constant representing the inputs' positions in the representation.
	 * First input is the oldest and last input is the newest.
	 * 
	 * @type integer[]
	 * @static
	 * @access private
	 */
	
	private static int IN[];//in1, in2, in3, in4, in5 ...
	
	/**
	 * Matrix containing current weights for the CasCor network.
	 * 
	 * @type double[][]
	 * @static
	 * @access public
	 */
	
	public static double weights[][];
	
	/**
	 * Current states of connections in CasCor network. It can be frozen or trained.
	 * For candidate units, input weights are always frozen.
	 * 
	 * @type integer[][]
	 * @static
	 * @access public
	 */
	
	public static int states[][];//frozen or trained
	
	/**
	 * Current number of added candidate units.
	 * 
	 * @type integer
	 * @static
	 * @access public
	 */
	
	public static int no_layers;
	
	/**
	 * Learning speed.
	 * 
	 * @type double
	 * @access private
	 * @static
	 */
	
	private static double alpha = 0.1;
	
	/**
	 * List containing current errors obtained for current configuration.
	 * 
	 * @type LinkedList<Double>
	 * @access private
	 * @static
	 */
	
	private static LinkedList<Double> crtErrors;
	
	/**
	 * Default configuration file name.
	 * 
	 * @type String
	 * @access public
	 * @static
	 */
	
	public static final String defaultConfig = Constants.serverBasePath +
			"\\src\\main\\java\\aossi\\algorithms\\CasCorP\\cascor.config";
	/**
	 * Current percentage error obtain for current configuration.
	 * 
	 * @type double
	 * @access private
	 * @static
	 */
	
	private static double crtPercError;
	
	/**
	 * Last predicted value - used to adjust the network in the prediction stage.
	 * 
	 * @type double
	 * @static
	 * @access private
	 */
	
	private static double lastPredicted;
	
	/**
	 * Number of hidden layer for whom the network seems to converge.
	 * 
	 * @static
	 * @type integer
	 * @access private
	 */
	
	private static int no_convergence; 
	
	/**
	 * Initialize the network with default parameters.
	 */
	
	public static void initNull()
	{
		weights = new double[MAX_NO_HIDDEN_LAYERS][MAX_NO_HIDDEN_LAYERS];
		states = new int[MAX_NO_HIDDEN_LAYERS][MAX_NO_HIDDEN_LAYERS];
		IN = new int[MAX_NO_HIDDEN_LAYERS];
		for(int i = 0 ; i < MAX_NO_HIDDEN_LAYERS ; i ++)
			for(int j = 0 ; j < MAX_NO_HIDDEN_LAYERS ; j ++)
				weights[i][j] = 0;
		for(int i = 0 ; i < MAX_NO_HIDDEN_LAYERS ; i ++)
			states[i][OUTPUT] = TRAINED;
		for(int i = 0 ; i < MAX_NO_HIDDEN_LAYERS ; i ++)
			for(int j = 1 ; j < MAX_NO_HIDDEN_LAYERS ; j ++)
				states[i][j] = FROZEN;
		for(int i = 0 ; i < MAX_NO_HIDDEN_LAYERS ; i ++)
			for(int j = 0 ; j < MAX_NO_HIDDEN_LAYERS ; j ++)
				states[i][j] = TRAINED;
		for(int i = 0; i < Algorithm.window ; i ++)
			IN[i] = i + 1;
		no_layers = 0;//no hidden layers
		crtErrors = new LinkedList<Double>();
		crtPercError = -1;
		lastPredicted = -1;
		no_convergence = 0;
		population = new Population();
	}
	
	/**
	 * Constructs the CasCor neural network for the specified training examples.
	 * 
	 * @param series  the training examples
	 */
	
	public static void init(LinkedList<Double> series)
	{
		
		initNull();
		
		//weighted moving average initialization
		for(int i = 0; i < Algorithm.window ; i ++)
		{
			weights[IN[i]][OUTPUT] = (double) (i+1) / 15; //in5 is the last value of the time series
		}
		no_layers = 0;//no hidden layers
	
		//trains the weights for input neurons 
		initial_train(series);
		//if errors are acceptable - stop
		if(error_computation(series) == true)
			return;
		
		int i=0;
		for(;;)
		{
			if(no_layers >= Algorithm.window)
				break;
			
			i++;
			System.out.println("*************  "+i+"   ***************");
			
			//add a candidate and train its inputs
			add_candidate(series);
		
			//trains the candidate output and adds it to the network
			train_candidate_output(series);
		
			//if errors are acceptable - stop
			if(error_computation(series) == true)
			{
				System.err.println("Acceptable error "+i +" hidden units");
				return;
			}
			
			if(no_layers == MAX_NO_HIDDEN_LAYERS)
			{
				System.err.println("No Limit...");
				return;
			}
		}
	}
	
	/**
	 * Trains the input's weights using delta rule. 
	 * In current state there are no candidate units in the network.
	 * 
	 * @param series the training examples
	 */
	
	private static void initial_train(LinkedList<Double> series)
	{
		//System.out.println("initial train");
		//trains the initial weights (no candidate units)
		//delta rule 
		for(int ii = Algorithm.window; ii < series.size() ; ii ++)
		{
			for(int i = Algorithm.window; i < series.size() ; i ++)
			{
				LinkedList<Double> input = new LinkedList<Double>();
				for(int j = i - Algorithm.window; j < i ; j ++)
				{
					input.add(series.get(j));
				}
				double output = series.get(i);
				double netOut = compute_output(input);
				for(int j = 0; j < Algorithm.window ; j ++)
				{
					//recompute the weights - delta rule
					weights[IN[j]][OUTPUT] += weights[IN[j]][OUTPUT] * (output - netOut) * input.get(j) * alpha / (ii +1);
				}
			}
		}
	}
	
	/**
	 * Computes errors for current CasCor network configuration.  
	 * 
	 * @param series the training examples
	 * @return boolean if the convergence condition is satisfied or not
	 */
	
	private static boolean error_computation(LinkedList<Double> series)
	{
		double errors[] = new double[series.size() - Algorithm.window];
		
		crtErrors = new LinkedList<Double>();
		
		for(int i = Algorithm.window; i < series.size() ; i ++)
		{
			LinkedList<Double> input = new LinkedList<Double>();
			for(int j = i - Algorithm.window; j < i ; j ++)
			{
				input.add(series.get(j));
			}
			double output = series.get(i);
			double netOut = compute_output(input);
			errors[i - Algorithm.window] = Math.abs(output - netOut) / output;
			crtErrors.add(new Double(output - netOut));
		}
		
		if(crtPercError == -1)
		{
			double sum = 0;
			for(int i = 0; i < series.size() - Algorithm.window ; i ++)
				sum += errors[i];
			crtPercError = sum;
			no_convergence = 0;
			return false;
		}
		else
		{
			double sum = 0;
			for(int i = 0; i < series.size() - Algorithm.window ; i ++)
				sum += errors[i];
			
			if(sum > crtPercError && no_layers > 1)
			{
				//System.out.println("caz 1");
				
				return true;
			}
			else
			{
				if(sum < 0.05 * crtPercError)
				{
					crtPercError = sum;
					no_convergence ++;
					//if(no_convergence > Algorithm.window / 2 )
						return true;
					//else 
						//return false;
				}
				else
				{
					//System.out.println("caz 2");
					return false;
				}
			}
		}
	}
	
	/**
	 * Adds a candidate unit in the CasCor neural network.
	 * The candidate unit's inputs weights are determined using a genetic algorithm.
	 * The candidate unit's output weight is set at default value and in the next step will be trained.
	 * 
	 * @param series the training examples
	 */
	
	private static void add_candidate(LinkedList<Double> series)
	{
		
//		System.out.println("add_candidate");
		
		//adds a candidate unit and trains the input weights
		int start = 1 + Algorithm.window ;
		
		if(no_layers == 0)
		{
			//create first layer
			
			//find best weights using a genetic algorithm
			population = new Population(64, Algorithm.window, 10, series, crtErrors);
			LinkedList<Double> weigh = population.getElite();
			
			for(int i = 0; i < Algorithm.window ; i ++)
				weights[start][IN[i]] = weigh.get(i);
			weights[start][OUTPUT] = 0.1;
		}
		else
		{
			
			System.out.println("size initial = "+series.size());
			
			start = 1 + Algorithm.window + no_layers;
			//add new layers
					
			LinkedList<Double> in = new LinkedList<Double>();
			
			//construct "in"
			for(int i = 0 ; i < series.size() ; i ++)
			{
				//input layer
				if(i % Algorithm.window != Algorithm.window - 1)
				{
					in.add(series.get(i));
				}
				else //hidden layer
				{
					//compute the input for all (previously added) hidden layer 
					for(int j = 0 ; j < no_layers + 1 ; j ++)
					{
						double input = 0;
						
						for(int k = 0; k < Algorithm.window  ; k ++)
							input += series.get(i - Algorithm.window + k + 1) * weights[Algorithm.window + j][IN[k]];
							
						for(int k = 0 ; k < j ; k ++)
							input += in.get(in.size() - 1 - no_layers + k) * weights[1+ Algorithm.window + j][IN[Algorithm.window]+1+k];
						
						in.add(input);
					}
				}
			}
				
			//find best weights using a genetic algorithm
			Population pop = new Population(64, Algorithm.window+no_layers+1, 16, in, crtErrors);
			LinkedList<Double> weigh = pop.getElite();
			
			for(int i = 0; i < Algorithm.window + no_layers; i ++)
				weights[start][i + 1] = weigh.get(i);
			weights[start][OUTPUT] = 0.1;
		}
		
/*	
		for(int i = 0 ; i < start + 1 ; i++)
		{
			for(int j = 0 ; j < start + 1; j++)
				System.out.print(weights[i][j]+" ");
			System.out.println();
		}*/
	}
	
	/**
	 * Training the output weights for all inputs neural and for all candidate units according to current errors.
	 * 
	 * @param series the training examples
	 */
	
	private static void train_candidate_output(LinkedList<Double> series)
	{
		//trains the weights output for the candidate unit and adds it to the network
		
		//System.out.println("train candidate output");

		no_layers ++;
		
		//delta rule 
		for(int ii = Algorithm.window; ii < series.size() ; ii ++)
		{
			for(int i = Algorithm.window; i < series.size() ; i ++)
			{
				LinkedList<Double> input = new LinkedList<Double>();
				for(int j = i - Algorithm.window; j < i ; j ++)
				{
					input.add(series.get(j));
				}
				double output = series.get(i);
				double netOut = compute_output(input);
				
				for(int j = 0; j < Algorithm.window ; j ++)
				{
					//recompute the weights - delta rule
					weights[IN[j]][OUTPUT] += weights[IN[j]][OUTPUT] * (output - netOut) * input.get(j) * alpha / (ii +1);
				}
				for(int j = 0 ; j < no_layers ; j ++)
				{
					//train the candidate units output weights
					//delta rule
					weights[j + Algorithm.window][OUTPUT] += weights[j + Algorithm.window][OUTPUT] * (output - netOut) * input.get(j) * alpha / (ii +1);
			
				}
			}
		}
	}
	
	/**
	 * Predicts the next value for the specified time series according to current network configuration.
	 * 
	 * @param series the time series that we want to predict
	 * @return double the predicted next value
	 */
	
	public static double predict(LinkedList<Double> series)
	{
		double next = 0;
		next = compute_output(series);
		if(lastPredicted == -1)
		{
			//it's the first prediction => no adjustment
			lastPredicted = next;
		}
		else
		{
			//if unsatisfactory errors => adjust the network
			//lastPredicted corresponds to series.getLast();
	/*		double real = series.getLast();
			double error = Math.abs(real - lastPredicted) / real;
			if(error > 0.2)
			{
				System.err.println("unsatisfactory error !!!");
				//unsatisfactory error
				
				//recompute the weights
				train_candidate_output(series);
				
				//still unsatisfactory error
				if(error_computation(series) == false)
				{
					add_candidate(series);
					
					//trains the candidate output and adds it to the network
					train_candidate_output(series);
				}
			}
			else
			{
				//satisfactory error - continue using the current architecture
			}*/
		}
		return next;
	}
	
	/**
	 * Computes the CasCor neural network output for the specified input.
	 * 
	 * @param input the network's inputs
	 * @return double the netwotk's output
	 */
	
	private static double compute_output(LinkedList<Double> input)
	{
		double sum = 0;
		//sum for the input contribution
		for(int i = 0; i < Algorithm.window ; i ++)
			sum += input.get(i) * weights[IN[i]][OUTPUT];
		if(no_layers == 0)
		{
			return sum;
		}
		double output_layer [] = new double[no_layers];
		for(int i = 0 ; i < no_layers ; i ++)
			output_layer[i] = 0;
		//for every hidden layer computes the output and the contribution at the output value		
		int layer_pos = 1 + Algorithm.window;
		
		for(int i = 0 ; i < no_layers ; i ++)
		{
			double partialSum = 0;
			//computes the sum according to the inputs
			for(int j = 1; j < Algorithm.window + 1 ; j ++)
				partialSum += input.get(i) * weights[layer_pos+i][IN[i]];
			double partialSum2 = 0;
			for(int j = 0 ; j < i ; j ++)
				partialSum2 += output_layer[j] * weights[layer_pos+i][layer_pos+j];
			partialSum += partialSum2;
			output_layer[i] = partialSum * weights[layer_pos+i][OUTPUT];
			sum += output_layer[i];
		}
		return sum;
	}
	
	/**
	 * Entry point - CasCor testing
	 * 
	 * @param args ignored
	 */
	
//	public static void main(String [] args)
//	{
//		String fileName = Constants.serverBasePath + "\\Tests\\load10_1.txt";
//		LinkedList<Double> lst = PlainParser.parse(fileName);
//		while(lst.size() > 	100)
//			lst.removeLast();
//		CasCor.init(lst);
//		IOcascor.write(defaultConfig);
//	}
}
