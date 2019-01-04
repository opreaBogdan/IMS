package bigdata.algorithms.Entities.Dtos;

public class BasicAnalyticsDto {
	public double min;
	public double max;
	public double mean;
	public double sigma;
	
	public BasicAnalyticsDto(double min, double max, double mean, double sigma){
		this.min = min;
		this.max = max;
		this.mean = mean;
		this.sigma = sigma;
	}
	
	
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	public double getSigma() {
		return sigma;
	}
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}
	
	
}
