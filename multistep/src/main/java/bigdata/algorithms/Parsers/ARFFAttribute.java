package bigdata.algorithms.Parsers;

/**
 * Class representing an ARFF attribute and its properties.
 * 
 * @author Andreea
 */

public class ARFFAttribute 
{
	/**
	 * The attribute name.
	 * 
	 * @type String
	 * @access private
	 */

	private String name;
	
	/**
	 * The attribute value type.
	 * 
	 * @type String
	 * @access private
	 */
	
	String valueType;
	
	/**
	 * The attribute type.
	 * 
	 * @type integer
	 * @access private
	 */
	
	private int type;
	
	/**
	 * A constant for "double" type.
	 * 
	 * @type integer
	 * @access public
	 * @static
	 */
	
	public static final int DOUBLE = 0;
	
	/**
	 * A constant for "string" type.
	 * 
	 * @type integer
	 * @access public
	 * @static
	 */
	
	public static final int STRING = 1;
	
	/**
	 * Creates an object representing an ARFF attribute.
	 * 
	 * @param name  the attribute name
	 * @param valueType  the type of the attribute value
	 */

	public ARFFAttribute(String name, String valueType)
	{
		this.name = name;	
		this.valueType = valueType;
		this.findType();
	}
	
	/**
	 * Creates an empty object representing an ARFF attribute.
	 */
	
	public ARFFAttribute()
	{
		this.name = null;
		this.valueType = null;
	}
	
	/**
	 * Sets the name of the attribute.
	 * 
	 * @param name  the attribute name
	 */
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Sets the value type for the attribute.
	 * 
	 * @param valueType  the type of the value of the attribute as a string
	 */
	
	public void setValueType(String valueType)
	{
		this.valueType = valueType;
		this.findType();
	}
	
	/**
	 * Finds if the attribute is a null one or not.
	 * 
	 * @return boolean
	 */
	
	public boolean isNull()
	{
		if(this.name == null)
			return true;
		return false;
	}
	
	/**
	 * Prints a string representation of the object.
	 */
	
	public void print()
	{
		System.out.println("name = "+this.name+" type = "+this.valueType);
	}
	
	/**
	 * Computes the type of the value:
	 * It can be Double or String
	 */
	
	public void findType()
	{
		if(this.valueType.indexOf("numeric") != -1)
			this.type = DOUBLE;
		else
			this.type = STRING;
	}
	
	/**
	 * Creates a copy of this object.
	 * 
	 * @param att  the object that is copied
	 */
	
	public void clone(ARFFAttribute att)
	{
		this.name = att.name;
		this.type = att.type;
		this.valueType = att.valueType;
	}
	
	/**
	 * Returns the name of the attribute
	 * 
	 * @return String the name of the attribute
	 */
	
	public String getName()
	{
		return this.name;
	}
	
}
