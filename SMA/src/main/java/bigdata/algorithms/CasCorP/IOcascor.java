package bigdata.algorithms.CasCorP;

import bigdata.algorithms.CasCor;
import bigdata.algorithms.Entities.Algorithm;

import java.io.*;

/**
 * Class implementing input / output operations with CasCor neural network's configuration.
 * 
 * @author Andreea
 */

public class IOcascor 
{
	/**
	 * Reads the specified input file that codify a CasCor structure and constructs the corresponding network.  
	 * 
	 * @param filename the input file
	 */
	
	@SuppressWarnings("deprecation")
	public static final void read(String filename)
	{
		File file = new File(filename);
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    DataInputStream dis = null;
		
		try 
	    {
	    	fis = new FileInputStream(file);
	    	bis = new BufferedInputStream(fis);//added for fast reading.
	    	dis = new DataInputStream(bis);
	    	
	    	CasCor.initNull();
	    	CasCor.no_layers = Integer.parseInt(dis.readLine());
	    	int no = 1 + Algorithm.window + CasCor.no_layers;
	    	
	    	for(int i = 0 ; i < no ; i ++)
	    		for(int j = 0 ; j < no ; j ++)
	    			CasCor.weights[i][j] = Double.parseDouble(dis.readLine());
	    	
	   /* 	System.out.println("Read:");
	    	for(int i = 0 ; i < no ; i ++)
	    	{
	    		for(int j = 0 ; j < no ; j ++)
	    			System.out.print(CasCor.weights[i][j]+" ");
	    		System.out.println();
	    	}
	    */
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
	}
	
	/**
	 * The current configuration of CasCor neural network is saved in the specified file name. 
	 * 
	 * @param filename the output file
	 */
	
	public static final void write(String filename)
	{
		try
		{
			 Writer output = null;
			 String text = "";
		
			 text += CasCor.no_layers+"\n";
			 
			 int no = 1 + Algorithm.window + CasCor.no_layers;
		    	
			 for(int i = 0 ; i < no ; i ++)
				 for(int j = 0 ; j < no ; j ++)
					 text += CasCor.weights[i][j]+"\n";
			 
			 File file = new File(filename);
			 output = new BufferedWriter(new FileWriter(file));
			 output.write(text);
			 output.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
