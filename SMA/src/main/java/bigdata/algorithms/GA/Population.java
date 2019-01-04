package bigdata.algorithms.GA;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class implementing a population used to find best weights for CasCor neural network.
 * 
 * @author Andreea
 */

public class Population 
{
	/**
	 * The fixed population chromosome number. 
	 * 
	 * @type integer
	 * @access private 
	 */
	
	private int chromosome_no;
	
	/**
	 * The fixed parameters number for a chromosome. 
	 * 
	 * @type integer
	 * @access private 
	 */
	
	private int chromosome_length;
	
	/**
	 * The current CasCor neural network errors. 
	 * 
	 * @type LinkedList<Double>
	 * @access private 
	 */
	
	private LinkedList<Double> errors;
	
	/**
	 * The current CasCor neural network inputs. 
	 * 
	 * @type LinkedList<Double>
	 * @access private 
	 */
	
	private LinkedList<Double> inputs;
	
	/**
	 * The current chromosome list. 
	 * 
	 * @type LinkedList<Double>
	 * @access private 
	 */
	
	private LinkedList<Chromosome> chromosome_list;
	
	/**
	 * The best chromosome obtained in population.
	 * 
	 * @type Chromosome
	 * @access private 
	 */
	
	private Chromosome elite;
	
	/**
	 * The number of evolution steps.
	 * 
	 * @type integer
	 * @access private 
	 */
	
	private int steps;
	
	/**
	 * Creates a new population with default parameters.
	 */
	
	public Population()
	{
		this.chromosome_no = 0;
		this.chromosome_length = 0;
		this.elite = new Chromosome();
		this.steps = 0;
		
		this.errors = new LinkedList<Double>();
		this.inputs = new LinkedList<Double>();
		this.chromosome_list = new LinkedList<Chromosome>();
	}
	
	/**
	 * Creates a new population with the specified parameters.
	 * 
	 * @param no number of chromosomes
	 * @param length number of parameters for chromosomes
	 * @param steps number of evolution steps
	 * @param in the current CasCor neural network inputs
	 * @param err the current CasCor neural network errors
	 */
	
	public Population(int no, int length, int steps, LinkedList<Double> in, LinkedList<Double> err)
	{
		this.chromosome_no = no;
		this.chromosome_length = length;
		this.elite = new Chromosome();
		this.steps = steps;
		
		this.errors = new LinkedList<Double>();
		for(int i = 0 ; i < err.size() ; i ++)
			this.errors.add(err.get(i));
		
		this.inputs = new LinkedList<Double>();
		for(int i = 0 ; i < in.size() ; i ++)
			this.inputs.add(in.get(i));
		
		this.chromosome_list = new LinkedList<Chromosome>();
		
		for(int i = 0 ; i < this.chromosome_no ; i ++)
		{
			Chromosome ch = new Chromosome(this.chromosome_length);
			ch.computeFitness(this.inputs, this.errors);
			this.chromosome_list.add(ch);
		}
		System.out.println("create population");
		this.run();
	}
	
	/**
	 * Implements the chromosome selection. 
	 * (Roulette wheel selection + CHC)
	 */
	
	@SuppressWarnings("unchecked")
	private void selection()
	{
		//System.out.println("selection");
		double sum = 0;
		for(int i = 0 ; i < this.chromosome_list.size() ; i ++)
			sum += this.chromosome_list.get(i).computeFitness(this.inputs, this.errors);
		
		double [] total_fitness = new double[this.chromosome_list.size()];
		total_fitness[0] = this.chromosome_list.get(0).getFitness() / sum;
		
		for(int i = 1 ; i < this.chromosome_no ; i ++)
			total_fitness[i] = total_fitness[i-1] + this.chromosome_list.get(i).getFitness() / sum;
		
		LinkedList<Chromosome> selected = new LinkedList<Chromosome>();
		
		//roulette selection
		
		for(int i = 0 ; i < this.chromosome_no ; i ++)
		{
			Random rand = new Random();
			double value =(double)(Math.abs(rand.nextInt())%10)/10;
			if(value <= total_fitness[0])
			{
				selected.add(this.chromosome_list.get(0));
			}
			else
			{
				for(int j = 0 ; j < this.chromosome_no -1 ; j ++)
					if(value > total_fitness[j] && value <= total_fitness[j+1])
						selected.add(this.chromosome_list.get(0));
				if(value > total_fitness[this.chromosome_no-1])
				{
					selected.add(this.chromosome_list.getLast());
				}
			}
		}
		
		//CHC
		
		Random rand1, rand2;
		int poz1, poz2;
		
		for(int i = 0 ; i < this.chromosome_no ; i++)
		{
			rand1 = new Random();
			poz1 =(int)(Math.abs(rand1.nextInt())%this.chromosome_no);
			
			for(;;)
			{
				rand2 = new Random();
				poz2 =(int)(Math.abs(rand2.nextInt())%this.chromosome_no);
				if(poz2 != poz1)
					break;
			}
			
			Chromosome parent1 = this.chromosome_list.get(poz1);
			Chromosome parent2 = this.chromosome_list.get(poz2);
			Chromosome offspring = Evolution.crossover(parent1, parent2);
			
			offspring.computeFitness(this.inputs, this.errors);
			
			selected.add(offspring);
		}
		
		Collections.sort(selected, new MyComparator());
		
		this.chromosome_list = new LinkedList<Chromosome>();
		for(int i = 0 ; i < this.chromosome_no ; i ++)
			this.chromosome_list.add(selected.get(i));
		
	}
	
	/**
	 * Evaluate the performance for the current population and computes the best chromosome. 
	 */
	
	@SuppressWarnings("unchecked")
	private void evaluation()
	{
		//System.out.println("evaluation");
		Collections.sort(this.chromosome_list, new MyComparator());
		double best = this.chromosome_list.getFirst().getFitness();
		
		if(best < this.elite.getFitness() || this.elite.getFitness() < 0)
		{
			this.elite.clone(this.chromosome_list.getFirst());
		}
	}
	
	/**
	 * Implements the population evolution for the specified number of steps.
	 */
	
	private void run()
	{
		System.out.println("run population");
		for(int k = 0 ; k < this.steps ; k ++)
		{
			evaluation();
			selection();
			for(int i = 0 ; i < this.chromosome_no ; i ++)
			{
				Chromosome next = Evolution.mutation(this.chromosome_list.remove(i));
				next.computeFitness(this.inputs, this.errors);
				this.chromosome_list.add(i, next);
			}
			
		}
	}
	
	/**
	 * Returns the configuration of the best chromosome of the current population.
	 * 
	 * @return LinkedList<Double>  the configuration of the best chromosome
	 */
	
	@SuppressWarnings("unchecked")
	public LinkedList<Double> getBest()
	{
		//finds the best chromosome
		Collections.sort(this.chromosome_list, new MyComparator());
		return this.chromosome_list.getFirst().getConfiguration();
	}
	
	/**
	 * Returns the best chromosome ever obtained.
	 * 
	 * @return LinkedList<Double>  the configuration of the best individual 
	 */
	
	public LinkedList<Double> getElite()
	{
		this.elite.print();
		return this.elite.getConfiguration();
	}
	/**
	 * Entry point used for testing the genetic algorithm.
	 * 
	 * @param args ignored
	 */
/*	
	public static void main(String [] args)
	{
		LinkedList<Double> in = new LinkedList<Double>();
		for(int i = 0 ; i < 5; i ++)
			in.add(new Double(i));
		LinkedList<Double> err = new LinkedList<Double>();
		for(int i = 0 ; i < 2; i ++)
			err.add(new Double(i));
		new Population(20,3,4,in, err); 
	}*/
}
