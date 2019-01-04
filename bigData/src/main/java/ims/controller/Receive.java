package ims.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class Receive {


    @JsonProperty("file")
    public MultipartFile file;
    @JsonProperty("perioada")
    public int perioada;

    public Receive() {
    }

    public void setPerioada(int perioada){
        this.perioada = perioada;
    }

    public int getPerioada() {
        return this.perioada;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return this.file;
    }
}
