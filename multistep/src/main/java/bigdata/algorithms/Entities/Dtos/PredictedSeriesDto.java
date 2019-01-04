package bigdata.algorithms.Entities.Dtos;

import bigdata.utils.Constants;

import java.util.LinkedList;

public class PredictedSeriesDto {
	
	/**
	 * The algorithm name.
	 * 
	 * @type String
	 * @access private 
	 */
	
	private String algorithm;
	
	/**
	 * The algorithm name.
	 * 
	 * @type String
	 * @access private 
	 */
	
	private double nmseValue;
	
	/**
	 * The algorithm name.
	 * 
	 * @type String
	 * @access private 
	 */
	
	private double crossValue;

	/**
	 * The list of the predicted values.
	 * 
	 * @type LinkedList<Double>
	 * @access private 
	 */

    private boolean multiStep;



    public long fileId;
	
	private LinkedList<Double> realValues;
	
	/**
	 * The list of the predicted values.
	 * 
	 * @type LinkedList<Double>
	 * @access private 
	 */
	
	private LinkedList<Double> predictedValues;
	
	private BasicAnalyticsDto basicAnalyticsDto;
	
	public PredictedSeriesDto(String algorithm){
		this.algorithm = algorithm;
		this.realValues = new LinkedList<Double>();
		this.predictedValues = new LinkedList<Double>();
	}
	
	public PredictedSeriesDto(String algorithm, LinkedList<Double> inputValues,
			LinkedList<Double> predictedValues, double nmseValue, double crossValue, boolean multiStep){
		this.algorithm = algorithm;
		this.nmseValue = nmseValue;
		this.crossValue = crossValue;
		this.realValues = new LinkedList<Double>(inputValues);
		this.predictedValues = new LinkedList<Double>(predictedValues);
        this.multiStep = multiStep;
	}
	
	public String getAlgorithm()
	{
		return this.algorithm;
	}
	
	/**
	 * Returns the list of the predicted time series.
	 * 
	 * @return LinkedList<Double>  the list of the predicted time series
	 */
	
	
	/**
	 * Sets the algorithm's name.
	 * 
	 * @param string new algorithm's name
	 */
	
	public void setAlgorithm(String algorithm) 
	{
		this.algorithm = algorithm;
	}
	
	public double getNmseValue() {
		return nmseValue;
	}

	public void setNmseValue(double nmseValue) {
		this.nmseValue = nmseValue;
	}

	public double getCrossValue() {
		return crossValue;
	}

	public void setCrossValue(double crossValue) {
		this.crossValue = crossValue;
	}

	public LinkedList<Double> getRealValues() {
		return realValues;
	}

	public LinkedList<Double> getPredictedValues() {
		return predictedValues;
	}

    public String getPredictedValuesToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Double predictedValue : predictedValues){
            stringBuilder.append(predictedValue + Constants.timeSeriesInputDelimiter);
        }

        return stringBuilder.substring(0, stringBuilder.length() - 1).toString();
    }

	public BasicAnalyticsDto getBasicAnalyticsDto() {
		return basicAnalyticsDto;
	}

	public void setBasicAnalyticsDto(BasicAnalyticsDto basicAnalyticsDto) {
		this.basicAnalyticsDto = basicAnalyticsDto;
	}

    public boolean isMultiStep() {
        return multiStep;
    }

    public void setMultiStep(boolean multiStep) {
        this.multiStep = multiStep;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }
}
