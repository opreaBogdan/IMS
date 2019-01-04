package bigdata.algorithms.GA;

import java.util.Random;

/**
 * Class implementing basic operations with chromosomes: crossover and mutation.
 * 
 * @author Andreea
 */

public class Evolution 
{
	
	/**
	 * Applies the mutation operation to a chromosome. A selected weight is random modified.
	 * 
	 * @param ch  the chromosome we want to mutate
	 * @return Chromosome  the new chromosome configuration
	 */
	
	public static Chromosome mutation(Chromosome ch)
	{
		Random rand = new Random();
		int iff =(int)(Math.abs(rand.nextInt())%100);
		
		if(iff < 50)
			//no mutation
			return ch;
		
		rand = new Random();
		//the weight position we'll modify
		int position =(int)(Math.abs(rand.nextInt())%(ch.getConfiguration().size()));
		rand = new Random();
		double value =(double)(Math.abs(rand.nextInt())%10)/30;
		double lastValue = ch.getConfiguration().get(position);
		ch.changeValue(position, lastValue - value);
		return ch;
	}
	
	/**
	 * Creates a new chromosome by crossover between the specified parents.
	 * (one - point crossover) - random generated
	 * 
	 * @param parent1 First parent
	 * @param parent2 Second parent
	 * @return Chromosome  the offspring
	 */
	
	public static Chromosome crossover(Chromosome parent1, Chromosome parent2)
	{
		Chromosome offspring = new Chromosome();
		Random rand = new Random();
		int position =(int)(Math.abs(rand.nextInt())%(parent1.getConfiguration().size()));
		for(int i = 0 ; i < position ;  i ++)
			offspring.addValue(i, parent1.getConfiguration().get(i));
		
		for(int i = position ; i < parent2.getConfiguration().size() ; i ++)
			offspring.addValue(i, parent2.getConfiguration().get(i));
		
		return offspring;
	}
}
