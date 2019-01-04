package bigdata.algorithms.Parsers;

/**
 * @author Andreea
 */

import java.util.LinkedList;

public class ARFFParameters 
{
	/**
	 * The parameters number.
	 * 
	 * @type integer
	 * @access public
	 */
	
	public int no;
	
	/**
	 * The parameters list.
	 * 
	 * @type LinkedList<Object>
	 * @access public
	 */
	
	public LinkedList<Object> paramList;
	
	/**
	 * Creates a default object.
	 */
	
	public ARFFParameters()
	{
		this.no = 0;
		this.paramList = new LinkedList<Object>();
	}
	
	/**
	 * Sets the number of parameters.
	 * 
	 * @param no  the number of parameters
	 */
	
	public void setNo(int no)
	{
		this.no = no;
	}
	
	/**
	 * Sets the list of the values for the parameters.
	 * 
	 * @param list  the list of the values for the parameters 
	 */
	
	public void setParamList(LinkedList<Object> list)
	{
		for(int i = 0 ; i < this.no ; i ++)
			this.paramList.add(list.get(i));
	}
	
	/**
	 * Adds a value for a parameter.
	 * 
	 * @param obj  the value for the parameter
	 */
	
	public void addParam(Object obj)
	{
		this.paramList.add(obj);
	}
	
	/**
	 * Prints a string representation of the object.
	 */
	
	public void print()
	{
		for(int i = 0 ; i < this.no ; i ++)
			System.out.print((String)this.paramList.get(i)+" ");
		System.out.println();	
	}
	
	/**
	 * Return the value for the parameter specified by position
	 * 
	 * @param position  the position of the parameter
	 * @return Object  the value for the parameter specified
	 */
	
	public Object getValue(int position)
	{
		return this.paramList.get(position);
	}
}
