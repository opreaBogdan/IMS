package bigdata.algorithms.Parsers;

/**
 * @author Andreea
 */

import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class ARFFParser 
{
	/**
	 * The name of the input file.
	 * 
	 * @type String
	 * @access public
	 */
	
	public String filename;
	
	/**
	 * The content of the input file.
	 * 
	 * @type ARFFFormat
	 * @access public
	 */
	
	public ARFFFormat arff;
	
	/**
	 * Creates an ARFF Parser
	 * 
	 * @param filename  the name for the input file
	 */
	
	@SuppressWarnings("deprecation")
	public ARFFParser(String filename)
	{
		File file = new File(filename);
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    DataInputStream dis = null;
	    String content = ""; 
	    
	    try 
	    {
	    	fis = new FileInputStream(file);
	    	bis = new BufferedInputStream(fis);//added for fast reading.
	    	dis = new DataInputStream(bis);

	    	while (dis.available() != 0)//if the file does not have more lines
	    		content += dis.readLine()+",";

	    	fis.close();
	    	bis.close();
	    	dis.close();
	    } 
	    catch (FileNotFoundException e) 
	    {
	    	e.printStackTrace();
	    } 
	    catch (IOException e) 
	    {
	      e.printStackTrace();
	    }
	    this.arff = this.parse(content);
	}
	
	/**
	 * Parses a string according to the ARFF format
	 * 
	 * @param content  the content of the file
	 * @return ARFFFormat  the content of the input file
	 */
	
	private ARFFFormat parse(String content)
	{
		int position = content.indexOf("@relation")+10;
		content = content.substring(position);
		StringTokenizer st = new StringTokenizer(content," ,");
		ARFFFormat arff = new ARFFFormat();
		String relation = st.nextToken();
		arff.setRelation(relation.substring(0, relation.length()-1));
		String next="";
		LinkedList <ARFFAttribute> list = new LinkedList<ARFFAttribute>();
		ARFFAttribute att = new ARFFAttribute(); 
		int AttNo = 0;
		for(;;)
		{
			if(next.indexOf("@data") != -1)
			{
				list.add(att);
				break;
			}
				
			if(next.indexOf("@attribute") != -1)
			{
				if(att.isNull() == false)
					list.add(att);
				att = new ARFFAttribute();
			
				String name = st.nextToken();
				String valueType = st.nextToken();
				att.setName(name);
				att.setValueType(valueType);
				AttNo ++;
			}	
			next = st.nextToken();
		}
		
		arff.setAttNo(AttNo);
		arff.setAttributeList(list);
			
		position = content.indexOf("@data")+5;
		content = content.substring(position);
		
		ARFFParameters param = new ARFFParameters();
		int noParam = 0;

		for(;;)
		{
			if(st.hasMoreTokens() == false)
				break;
								
			param.setNo(arff.getAttNo());
			
			for(int i = 0 ; i < arff.getAttNo() ; i ++ )
			{
				next = st.nextToken();
				param.addParam((Object)next);
			}
			
			arff.addData(param);
			noParam ++;
			param = new ARFFParameters();
		}
		arff.setDataNo(noParam);
		arff.print();
		
		Object [] plist = arff.getParamValues("bandwidth");
		for(int i = 0 ; i < arff.getAttNo() ; i ++ )
			System.out.print((String)plist[i]+" ");
		
		return arff;
	}
}
