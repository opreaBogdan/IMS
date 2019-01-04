package bigdata.algorithms.Parsers;

import bigdata.utils.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.LinkedList;

/**
 * Class used for parsing a file containing a list of values.
 * 
 * @author Andreea
 */

public class PlainParser {
	/**
	 * Reads a file and returns the file content as a time series.
	 * 
	 * @param filename
	 *            the name of the input file
	 * @return LinkedList<Double> the time series specified in file
	 */

	@SuppressWarnings("deprecation")
	public static LinkedList<Double> parse(MultipartFile fileFromServer) {
		// transfer the content to a temp file
		String tempPath = Constants.tempFolder + "parse.txt";
		File file = new File(tempPath);
		try {
			fileFromServer.transferTo(file);
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		LinkedList result = parse(file.getAbsolutePath());
		
		if (file.delete()) {
//			System.out.println(file.getName() + " is deleted!");
		} else {
			System.out.println("Delete operation has failed.");
		}
		
		return result;

	}

    public static String parseToString(String fileName){
        LinkedList<Double> doubleValues = parse(fileName);
        StringBuilder stringValue = new StringBuilder();

        for(Double doubleValue : doubleValues){
            stringValue.append(doubleValue.toString() + "_");
        }

        return stringValue.substring(0, stringValue.length()-1).toString();
    }

	@SuppressWarnings("deprecation")
	public static LinkedList<Double> parse(String fileName) {

		File file = new File(fileName);
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		LinkedList<Double> list = new LinkedList<Double>();

		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);// added for fast reading.
			dis = new DataInputStream(bis);

			while (dis.available() != 0)// if the file does not have more lines
				list.add(Double.parseDouble(dis.readLine()));

			fis.close();
			bis.close();
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// delete the temp file
		

		return list;
	}
}
