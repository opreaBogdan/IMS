package bigdata.algorithms.Parsers;

import java.util.LinkedList;

/**
 * Class representing the ARFF format and its properties.
 * 
 * @author Andreea
 */

public class ARFFFormat 
{
	/**
	 * The file relation.
	 * 
	 * @type String
	 * @access private
	 */
	
	private String relation;
	
	/**
	 * The attributes list for the input file.
	 * 
	 * @type LinkedList<ARFFAttribute>
	 * @access private
	 */
	
	private LinkedList<ARFFAttribute> attributeList;
	
	/**
	 * The attributes number for the input file.
	 * 
	 * @type integer
	 * @access private
	 */
	
	private int attNo;
	
	/**
	 * The data list for the input file.
	 * 
	 * @type LinkedList<ARFFParameters>
	 * @access private
	 */
	
	private LinkedList<ARFFParameters> data;
	
	/**
	 * The data number for the input file.
	 * 
	 * @type integer
	 * @access private
	 */
	
	private int dataNo;
	
	/**
	 * Creates a default object representing the format for an ARFF file
	 */
	
	public ARFFFormat()
	{
		this.relation = null;
		this.attributeList = null;
		this.data = new LinkedList<ARFFParameters>();
		this.setAttNo(0);
		this.dataNo = 0;
	}
	
	/**
	 * Sets the name of the relation
	 * 
	 * @param relation  the relation name
	 */
	
	public void setRelation(String relation)
	{
		this.relation = relation;
	}
	
	/**
	 * Sets the number of attributes 
	 * 
	 * @param no  the number of attributes
	 */
	
	public void setAttNo(int no)
	{
		this.attNo = no;
	}
	
	/**
	 * Sets the number of data
	 * 
	 * @param no  the number of experimental data
	 */
	
	public void setDataNo(int no)
	{
		this.dataNo = no;
	}
	
	/**
	 * Sets the list of experimental data
	 * 
	 * @param data  the experimental data
	 */
	
	public void setData(LinkedList<ARFFParameters> data)
	{
		for(int i = 0 ; i < this.dataNo ; i ++)
			this.data.add(data.get(i));		
	}
	
	/**
	 * Sets the list of attributes
	 * 
	 * @param list  the list of the attributes
	 */
	
	public void setAttributeList(LinkedList<ARFFAttribute> list)
	{
		this.attributeList = new LinkedList <ARFFAttribute>();		
		for(int i = 0 ; i < this.getAttNo() ; i ++)
		{
			ARFFAttribute att = new ARFFAttribute();
			att.clone(list.get(i));
			this.attributeList.add(att);
		}
	}
	
	/**
	 * Adds a parameter set in the data list
	 * 
	 * @param param  the new parameter set
	 */
	
	public void addData(ARFFParameters param)
	{
		this.data.add(param);
	}
	
	/**
	 * Prints a string representation of the object.
	 */
	
	public void print()
	{
		System.out.println("relation = "+relation);
		for(int i = 0 ; i < this.getAttNo() ; i ++)
			this.attributeList.get(i).print();
		for(int i = 0 ; i < this.dataNo ; i ++)
			this.data.get(i).print();
	}
	
	/**
	 * Returns the list of the values for the parameter with the specified name
	 * 
	 * @param parameterName  the name of the parameter 
	 * @return Object []  list of the values for the parameter with the specified name
	 */
	
	public Object [] getParamValues(String parameterName)
	{
		Object [] list = new Object[this.dataNo];
		int position = -1;
		
		for(int i = 0 ; i < this.dataNo ; i ++)
			if(this.attributeList.get(i).getName().equals(parameterName))
			{
				position = i;
				break;
			}
		
		if(position == -1)
		{
			System.err.println("No such a parameter.");
			return null;
		}
		for(int i = 0 ; i < this.dataNo ; i ++)
			list[i] = this.data.get(i).getValue(position);
		return list;
	}
	
	/**
	 * Returns the attributes number.
	 * 
	 * @return integer  the attributes number
	 */

	public int getAttNo() 
	{
		return attNo;
	}
	
}
