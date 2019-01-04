package bigdata.algorithms.Backprop;

import bigdata.algorithms.BackPropagation;

import java.io.*;

/**
 * Class implementing input / output operations with backpropagation neural network's configuration.
 * 
 * @author Andreea
 */

public class IObackprop 
{
	/**
	 * Reads the specified input file that codify a backpropagation structure and constructs the corresponding network.  
	 * 
	 * @param filename the input file
	 */
	
	@SuppressWarnings("deprecation")
	public static final void read(String filename)
	{
		System.out.println("read");
		File file = new File(filename);
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    DataInputStream dis = null;
		
		try 
	    {
	    	fis = new FileInputStream(file);
	    	bis = new BufferedInputStream(fis);//added for fast reading.
	    	dis = new DataInputStream(bis);
	    	
	    	BackPropagation.init();
	    	
	    	for(int i = 0 ; i < BackPropagation.nr_neuroni[0] ; i ++)
				 for(int j = 0 ; j < BackPropagation.nr_neuroni[1] ; j ++)
					 BackPropagation.weights1[i][j] = Double.parseDouble(dis.readLine());
			 
			 for(int i = 0 ; i < BackPropagation.nr_neuroni[1] ; i ++)
				 for(int j = 0 ; j < BackPropagation.nr_neuroni[2] ; j ++)
					 BackPropagation.weights2[i][j] = Double.parseDouble(dis.readLine());
			 
			 for(int i = 0 ; i < BackPropagation.nr_neuroni[2] ; i ++)
				 for(int j = 0 ; j < BackPropagation.nr_neuroni[3] ; j ++)
					 BackPropagation.weights3[i][j] = Double.parseDouble(dis.readLine());
	    	
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
	 * The current configuration of backpropagation neural network is saved in the specified file name. 
	 * 
	 * @param filename the output file
	 */
	
	public static final void write(String filename)
	{
		System.out.println("write");
		try
		{
			 Writer output = null;
			 String text = "";
		
			 for(int i = 0 ; i < BackPropagation.nr_neuroni[0] ; i ++)
				 for(int j = 0 ; j < BackPropagation.nr_neuroni[1] ; j ++)
					 text += BackPropagation.weights1[i][j] +"\n";
			 
			 for(int i = 0 ; i < BackPropagation.nr_neuroni[1] ; i ++)
				 for(int j = 0 ; j < BackPropagation.nr_neuroni[2] ; j ++)
					 text += BackPropagation.weights2[i][j] +"\n";
			 
			 for(int i = 0 ; i < BackPropagation.nr_neuroni[2] ; i ++)
				 for(int j = 0 ; j < BackPropagation.nr_neuroni[3] ; j ++)
					 text += BackPropagation.weights3[i][j] +"\n";
				 		 
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

