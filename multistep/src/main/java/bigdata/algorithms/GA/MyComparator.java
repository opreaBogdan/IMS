package bigdata.algorithms.GA;

import java.util.Comparator;

/**
 * Class implementing a fitness comparator for chromosomes.
 * 
 * @author Andreea
 */

@SuppressWarnings("unchecked")
public class MyComparator implements Comparator
{
	/**
	 * Compares its two arguments for order.
	 * Arguments are chromosomes and we want to sort them according to their fitness.
	 * 
	 * @param o1 the first object to be compared
	 * @param o2 the second object to be compared
	 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second. 
	 */
	
	public int compare(Object o1, Object o2)
	{
		Chromosome ch1 = (Chromosome)o1;
		Chromosome ch2 = (Chromosome)o2;
		double fitness1 = ch1.getFitness();
		double fitness2 = ch2.getFitness();
		if(fitness1 > fitness2)
			return 1;
		else if(fitness1 == fitness2)
			return 0;
		else 
			return -1;
	}
}
