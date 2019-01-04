package ims.controller;

import ims.entities.TimeSeriesInputEntity;
import ims.entities.UserEntity;
import ims.repositories.TimeSeriesInputEntityRepository;
import ims.repositories.UserEntityRepository;
import ims.utils.Constants;
import ims.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
class HelloController {

    @Autowired
    TimeSeriesInputEntityRepository timeSeriesInputEntityRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
    UserEntity user;

    private LinkedList<Double> values = null;
    private int predict_start = 5;
    private int prediction_time = 5;

    private List predict(int port)
    {
        final String url = "http://localhost:" + port + "/predict";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> result = restTemplate.postForEntity(url, values, List.class);
        return result.getBody();
    }

    @RequestMapping("/random")
    public List randomPrediction() {
        return predict(8081);
    }

    @RequestMapping("/homeostatic")
    public List<Double> homeostaticPrediction() {
        return predict(8084);
    }

    @RequestMapping("/backpropagation")
    public List<Double> backpropagationPrediction() {
        return predict(8082);
    }

    @RequestMapping("/cascor")
    public List<Double> cascorPrediction() {
        return predict(8083);
    }

    @RequestMapping("/ema")
    public List<Double> emaPrediction() {
        return predict(8087);
    }

    @RequestMapping("/tendency")
    public List<Double> tendencyPrediction() {
        return predict(8086);
    }

    @RequestMapping("/unix")
    public List<Double> unixPrediction() {
        return predict(8085);
    }

    @RequestMapping("/wma")
    public List<Double> wmaPrediction() {
        return predict(8088);
    }


    @RequestMapping(value = "/resultsFromToken", params = "token", method = POST)
    public String resultsFromToken(@RequestParam("token") String token) {
        List<TimeSeriesInputEntity> queryResult = timeSeriesInputEntityRepository.findByToken(token);
        String result = "No data available for this token";
        if (queryResult == null || queryResult.size() == 0) {
            return result;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(Constants.serverBasePath + Constants.delimiter + token));
            LinkedList<Double> res = Utils.parseInputFileFromToken(br);
            prediction_time = res.remove(0).intValue();
            values = res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Token uploaded!";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateAlgorithm(@RequestParam Map<String,String> requestParams) throws Exception{
        if (user == null)
            return "Please login first";

        String token = requestParams.get("token");
        String description = requestParams.get("description");

        //perform DB operations
        timeSeriesInputEntityRepository.updateAlgorithm(description, token);
        return "Updated";
    }


    @ResponseBody
    @RequestMapping(value = "/uploadingFile", method = RequestMethod.POST ,consumes = {"application/x-www-form-urlencoded","multipart/form-data"})
    public String uploadFile(@ModelAttribute Receive response) throws Exception {
        if(response.getFile() == null) {
            throw new IllegalArgumentException("File not found");
        }
        prediction_time = response.getPerioada();
        BufferedReader readFile = new BufferedReader(new InputStreamReader(response.getFile().getInputStream(), "UTF-8"));
        String token = Utils.generateToken(timeSeriesInputEntityRepository);
        values = Utils.parseInputFile(readFile, token, timeSeriesInputEntityRepository);
        return token;
    }

    @ResponseBody
    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    public PredictionJSON getStatistics(@RequestBody String ids) throws Exception {
        final String url = "http://localhost:8089/statistics";

        List<Double> values = new ArrayList<>();
        String[] vals = ids.split(",");
        for (String val : vals) {
            val = val.trim();
            values.add(Double.parseDouble(val));
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PredictionJSON> result = restTemplate.postForEntity(url, values, PredictionJSON.class);
        return result.getBody();
    }
}