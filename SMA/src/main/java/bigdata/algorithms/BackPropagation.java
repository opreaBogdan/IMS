package bigdata.algorithms;

import bigdata.algorithms.Entities.Algorithm;
import bigdata.utils.Constants;

import java.util.LinkedList;
import java.util.Random;
/*
 * myBp cross=10.623081537164518 nmse=0.29331515309473927
 * pentru test_rav.txt
 */

/**
 * Class implementing a backpropagation neural network. 
 * 
 * @author Andreea
 */

public class BackPropagation extends Algorithm
{
	public static int division = 20;
	
	/**
	 * The default configuration file name used to load a network structure.
	 * 
	 * @type String
	 * @access public
	 * @static
	 */
	
	public static final String defaultConfig = Constants.serverBasePath + 
			"\\src\\main\\java\\aossi\\algorithms\\Backprop\\backprop.config";
	 /**
	 * The maxim number of neurons for one layer.
	 * 
	 * @type integer
	 * @access private 
	 * @static
	 */
	 
	private static int MAX = 1000;
	
	/**
	 * The neural network configuration - 5 neurons for input, 1 neuron for output, 
	 * 26 neurons for the first hidden layer and 6 for the second hidden layer.
	 * 
	 * @type integer[]
	 * @access private 
	 * @static
	 */
	
	public static int nr_neuroni[]={5,26,6,1};
	
	/**
	 * Learning speed.
	 * 
	 * @type double
	 * @static
	 * @access private
	 */
	
	private static double delta =0.99;
	
	/**
	 * Neural network's weights.
	 * weights1 - input layer -> first hidden layer
	 * weights2 - first hidden layer -> second hidden layer
	 * weights3 - second hidden layer -> output layer
	 * 
	 * @type double 
	 * @static
	 * @access public
	 */
	
	public static double weights1[][], weights2[][],weights3[][];
	private static double iesire1[], iesire2[], iesire3[], iesire4[];
	private static double erori2[], erori3[], eroare_iesire[];
	private static double theta1[], theta2[], theta3[], theta0[];
	private static double max_intrare = 1;
	
	int n;
	public static int N = Algorithm.window;
	double valori[]=new double[MAX];
	double prezis[]=new double[MAX];
	
	/**
	 * Constructs a backpropagation neural network for specified training examples.
	 * 
	 * @param series the training examples
	 */
	
	public BackPropagation(LinkedList<Double> series)
	{
		System.out.println("bp");
		this.n = series.size();
		//init();
		for(int i = 0 ; i < n ; i ++)
			valori[i]=series.get(i)/division;	
		set_antrenare();
		testare();
		afisare();
	}
	
	/**
	 * Predicts the next value for specified time series.
	 * 
	 * @param series the time series we want to predict
	 * @return double the predicted next value
	 */
	
	public static double predict(LinkedList<Double> series)
	{
		double intrare[]=new double[MAX];
		double next = 0;
		for(int j = 0 ; j < N ; j ++)
			intrare[j] = series.get(j);
		calculeaza_iesire(intrare);
		next = iesire4[0];
		if(Math.abs(max_intrare) > 1)
			next *= max_intrare;
		
//		next *= division;
		
		return next;
	}
	
	/**
	 * Initializes the backpropagation neural network with default parameters.
	 */
	
	public static void init()
	{
		System.out.println("init");
		weights1 = new double[MAX][MAX];
		weights2 = new double[MAX][MAX];
		weights3 = new double[MAX][MAX];
		
		iesire1 = new double[MAX];
		iesire2 = new double[MAX];
		iesire3 = new double[MAX];
		iesire4 = new double[MAX];
		
		erori2 = new double[MAX];
		erori3 = new double[MAX];
		eroare_iesire = new double[MAX];
		
		theta0 = new double[MAX];
		theta1 = new double[MAX];
		theta2 = new double[MAX];
		theta3 = new double[MAX];
		
		for(int i = 0 ; i < nr_neuroni[0] ; i ++)
			for(int j = 0 ; j < nr_neuroni[1]; j ++)
			{
				Random rand = new Random();
				weights1[i][j] =(double)(Math.abs(rand.nextInt())%10)/20;
			}
		
		for(int i = 0 ; i < nr_neuroni[1] ; i ++)
			for(int j = 0 ; j < nr_neuroni[2]; j ++)
			{
				Random rand = new Random();
				weights2[i][j] = (double)(Math.abs(rand.nextInt())%10)/20;
			}
		
		for(int i = 0 ; i < nr_neuroni[2] ; i ++)
			for(int j = 0 ; j < nr_neuroni[3]; j ++)
			{
				Random rand = new Random();
				weights3[i][j] = (double)(Math.abs(rand.nextInt())%10)/10;	
			}
	}
	
	/**
	 * Trains the backpropagation neural network for the training examples.
	 */
	
	private void set_antrenare()
	{
		double intrare[]=new double[MAX];
		double iesire;
		
		for(int k = 0 ; k <  100 * n; k ++)
		{
			for(int i = N ; i < n ; i ++)
			{
				iesire = valori[i];
				for(int j = 0 ; j < N ; j ++)
					intrare[j] = valori[i-N+j];
				antrenare(intrare,iesire);
			}
		}
	}
	
	/**
	 * Computes the sigmoid function.
	 * 
	 * @param x the parameter
	 * @return double the sigmoid function
	 */
	
	private static double sigmoid(double x)
	{
		return 1 / (1 + Math.exp(-x));
	}
	
	/**
	 * 
	 * 
	 * @param intrare
	 */
	
	//calculeaza iesirea retelei pentru intrarea specificata
	private static void calculeaza_iesire(double intrare[])
	{
		//calculeaza iesirea stratului de intrare
		for(int i = 0 ; i < nr_neuroni[0] ; i ++)
			iesire1[i] = intrare[i] - theta0[i];
		
		//calculeaza iesirea stratului ascuns 1
		for(int i = 0 ; i < nr_neuroni[1] ; i ++)
		{
			double suma = 0 ;
			for(int j = 0 ; j < nr_neuroni[0] ; j ++)
				suma += weights1[j][i] * iesire1[j];
			iesire2[i] = sigmoid(suma - theta1[i]);			
		}

		//calculeaza iesirea stratului ascuns 2
		for(int i = 0 ; i < nr_neuroni[2] ; i ++)
		{
			double suma = 0 ;
			for(int j = 0 ; j < nr_neuroni[1] ; j ++)
				suma += weights2[j][i] * iesire2[j];
			iesire3[i] = sigmoid(suma - theta2[i]);
		}

		//calculeaza iesirea retelei
		for(int i = 0 ; i < nr_neuroni[3] ; i ++)
		{
			double suma = 0 ;
			for(int j = 0 ; j < nr_neuroni[2] ; j ++)
				suma += weights3[j][i] * iesire3[j];
			iesire4[i] = suma - theta3[i];
		}
	}

	void antrenare(double intrare[], double iesire)
	{
		//calculeaza iesirea retelei
		calculeaza_iesire(intrare);		
		//calculeaza iesirea la nivelul stratului de iesire
		//iesire4 = iesirea retelei
		//iesire = iesirea corecta
		for(int k = 0 ; k < nr_neuroni[3] ; k ++)
			eroare_iesire[k] =  (iesire - iesire4[k]);//iesire4[k] * (1 - iesire4[k]) *

		//calculeaza erorile la nivelul stratului ascuns 2
		for(int j = 0 ; j < nr_neuroni[2] ; j ++)
		{
			double suma = 0 ;
			for(int k = 0 ; k < nr_neuroni[3] ; k ++)
				suma += weights3[j][k] * eroare_iesire[k];
			erori3[j] = iesire3[j] * (1 - iesire3[j]) * suma;
		}
		
		//calculeaza erorile la nivelul stratului ascuns 1
		for(int j = 0 ; j < nr_neuroni[1] ; j ++)
		{
			double suma = 0 ;
			for(int k = 0 ; k < nr_neuroni[2] ; k ++)
				suma += weights2[j][k] * erori3[k];
			erori2[j] = iesire2[j] * (1 - iesire2[j]) * suma;
		}
		
		//modifica weightsle
		for(int i = 0 ; i < nr_neuroni[0] ; i ++)
			for(int j = 0 ; j < nr_neuroni[1]; j ++)
				weights1[i][j] += delta * erori2[j] * iesire1[i];

		for(int i = 0 ; i < nr_neuroni[1] ; i ++)
			for(int j = 0 ; j < nr_neuroni[2]; j ++)
				weights2[i][j] += delta * erori3[j] * iesire2[i];

		for(int i = 0 ; i < nr_neuroni[2] ; i ++)
			for(int j = 0 ; j < nr_neuroni[3]; j ++)
				weights3[i][j] += delta * eroare_iesire[j] * iesire3[i];
	}

	public double[] getPredictedValues()
	{
		return prezis;
	}

	public double[] getRealValues()
	{
		return valori;
	}

	private void afisare()
	{
		int i;
		System.out.printf("\nPAS\t\tPREZIS\t\t\tREAL\t\tEROARE\n");
		System.out.printf("-------------------------------------------------------------------------\n");
		double eroare[]=new double[MAX];
		for(i = N ; i < n ; i ++)
		{
			if(Math.abs(max_intrare) > 1)
				valori[i] *= Math.abs(max_intrare);
			eroare[i] = Math.abs(valori[i] - prezis[i])/valori[i] * 100;
			System.out.println(" "+(i+1)+" \t"+prezis[i]+" \t\t"+valori[i]+" \t"+eroare[i]+"%");
		}
	}
	
	
	private void testare()
	{
		double intrare[]=new double[MAX];

		//se testeaza reteaua pentru n-N exemple
		for(int i = N ; i < n ; i ++)
		{
			for(int j = 0 ; j < N ; j ++)
				intrare[j] = valori[i-N+j];
			calculeaza_iesire(intrare);
			prezis[i] = iesire4[0];
			if(Math.abs(max_intrare) > 1)
				prezis[i] *= max_intrare;
		}
	}
	
}
