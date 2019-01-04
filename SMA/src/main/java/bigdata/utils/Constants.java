package bigdata.utils;

import bigdata.algorithms.Algorithms;

public class Constants {
	public static final String serverBasePath = "D:\\BigData\\storage\\results";
	public static final String basePath = "/TimeSeriesPrediction";
	public static final String delimiter = "\\";
	public static final int tokenSize = 7;
	public static final String tempFolder = serverBasePath + "\\temp";
	public static final String fileToParse = "parse.txt";
	public static final String uploadedFile = "uploadedFile";
	public static final String descriptionsFolder = serverBasePath + "\\AlgorithmDescriptions";
	public static final String defaultDescriptionsFolder = serverBasePath + 
			"\\AlgorithmDescriptions\\defaults";

    public static final String defaultTestFolderName = serverBasePath + "\\Tests";
    public static final String timeSeriesInputDelimiter = "_";



	public class SuccessMessages {
		public static final String descriptionSuccessfullySaved = "Description saved successfully!";
		public static final String fileSuccessfullyUploaded = "File was successfully updated! File name: ";
        public static final String defaultResultsSuccessfullySaved = "Default results saved!";
		
	}
	
	public class ErrorMessages{
		public static final String descriptionNotSaved = "Description was not successfully saved!";
		public static final String fileNotUploaded = "File not successfully updated! File name: ";
	}

    public static final Algorithms[] defaultAlgorithms = new Algorithms[]{
            Algorithms.SMA,
            Algorithms.EMA,
            Algorithms.WMA,
            Algorithms.CasCor,
            Algorithms.Random,
            Algorithms.UNIX
    };
	
	
}
