package bigdata.bigdata.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class StatisticsJSON {


    @JsonProperty("real")
    public double[] real;
    @JsonProperty("predicted")
    public double[] predicted;

    public StatisticsJSON() {
    }

    public void setReal(double[] real){
        this.real = real;
    }

    public double[] getReal() {
        return this.real;
    }

    public void setPredicted(double[] predicted) {
        this.predicted = predicted;
    }

    public double[] getPredicted() {
        return this.predicted;
    }
}
