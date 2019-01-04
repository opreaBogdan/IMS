package bigdata.bigdata.controller;

import bigdata.algorithms.*;
import bigdata.entities.TimeSeriesInputEntity;
import bigdata.entities.UserEntity;
import bigdata.repositories.TimeSeriesInputEntityRepository;
import bigdata.repositories.UserEntityRepository;
import bigdata.utils.Constants;
import bigdata.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
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

    public boolean sanityCheck() {
        if (values == null || values.isEmpty())
            return false;
        return true;
    }

    @RequestMapping("/random")
    public LinkedList<Double> randomPrediction() {
        if(!sanityCheck()) {
            return null;
        }

        LinkedList<Double>  result = new LinkedList<>();
        LinkedList<Double>  to_predict = new LinkedList<>();
        int real_size = values.size();

        // add the number of real values
        result.add((double)real_size);

        // add real values
        result.addAll(values);

        // add initial values for prediction
        for (int i = 0; i < predict_start; i++) {
            result.add(values.get(i));
            to_predict.add(values.get(i));
        }

        for (int i = predict_start; i < real_size; i++) {
            double predicted_value = RandomPrediction.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(values.get(i));
        }

        for (int i = 0; i < prediction_time; i++) {
            double predicted_value = RandomPrediction.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(predicted_value);
        }

        return result;
    }

    @RequestMapping("/homeostatic")
    public LinkedList<Double> homeostaticPrediction() {
        if(!sanityCheck()) {
            return null;
        }

        LinkedList<Double>  result = new LinkedList<>();
        LinkedList<Double>  to_predict = new LinkedList<>();
        int real_size = values.size();

        // add the number of real values
        result.add((double)real_size);

        // add real values
        result.addAll(values);

        // add initial values for prediction
        for (int i = 0; i < predict_start; i++) {
            result.add(values.get(i));
            to_predict.add(values.get(i));
        }

        for (int i = predict_start; i < real_size; i++) {
            double predicted_value = Homeostatic.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(values.get(i));
        }

        for (int i = 0; i < prediction_time; i++) {
            double predicted_value = Homeostatic.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(predicted_value);
        }

        return result;
    }

    @RequestMapping("/backpropagation")
    public LinkedList backpropagationPrediction() {
        if(!sanityCheck()) {
            return null;
        }

        BackPropagation.init();

        LinkedList<Double>  result = new LinkedList<>();
        LinkedList<Double>  to_predict = new LinkedList<>();
        int real_size = values.size();

        // add the number of real values
        result.add((double)real_size);

        // add real values
        result.addAll(values);

        // add initial values for prediction
        for (int i = 0; i < predict_start; i++) {
            result.add(values.get(i));
            to_predict.add(values.get(i));
        }

        for (int i = predict_start; i < real_size; i++) {
            double predicted_value = BackPropagation.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(values.get(i));
        }

        for (int i = 0; i < prediction_time; i++) {
            double predicted_value = BackPropagation.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(predicted_value);
        }

        return result;
    }

    @RequestMapping("/cascor")
    public LinkedList<Double> cascorPrediction() {
        if(!sanityCheck()) {
            return null;
        }

        LinkedList<Double>  result = new LinkedList<>();
        LinkedList<Double>  to_predict = new LinkedList<>();
        int real_size = values.size();

        // add the number of real values
        result.add((double)real_size);

        // add real values
        result.addAll(values);

        // add initial values for prediction
        for (int i = 0; i < predict_start; i++) {
            result.add(values.get(i));
            to_predict.add(values.get(i));
        }

        for (int i = predict_start; i < real_size; i++) {
            CasCor.init(to_predict);
            double predicted_value = CasCor.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(values.get(i));
        }

        for (int i = 0; i < prediction_time; i++) {
            CasCor.init(to_predict);
            double predicted_value = CasCor.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(predicted_value);
        }

        return result;
    }

    @RequestMapping("/ema")
    public LinkedList<Double> emaPrediction() {
        if(!sanityCheck()) {
            return null;
        }

        LinkedList<Double>  result = new LinkedList<>();
        LinkedList<Double>  to_predict = new LinkedList<>();
        int real_size = values.size();

        // add the number of real values
        result.add((double)real_size);

        // add real values
        result.addAll(values);

        // add initial values for prediction
        for (int i = 0; i < predict_start; i++) {
            result.add(values.get(i));
            to_predict.add(values.get(i));
        }

        for (int i = predict_start; i < real_size; i++) {
            double predicted_value = EMA.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(values.get(i));
        }

        for (int i = 0; i < prediction_time; i++) {
            double predicted_value = EMA.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(predicted_value);
        }

        return result;
    }

    @RequestMapping("/tendency")
    public LinkedList<Double> tendencyPrediction() {
        if(!sanityCheck()) {
            return null;
        }

        LinkedList<Double>  result = new LinkedList<>();
        LinkedList<Double>  to_predict = new LinkedList<>();
        int real_size = values.size();

        // add the number of real values
        result.add((double)real_size);

        // add real values
        result.addAll(values);

        // add initial values for prediction
        for (int i = 0; i < predict_start; i++) {
            result.add(values.get(i));
            to_predict.add(values.get(i));
        }

        for (int i = predict_start; i < real_size; i++) {
            double predicted_value = Tendency_based.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(values.get(i));
        }

        for (int i = 0; i < prediction_time; i++) {
            double predicted_value = Tendency_based.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(predicted_value);
        }

        return result;
    }

    @RequestMapping("/unix")
    public LinkedList<Double> unixPrediction() {
        if(!sanityCheck()) {
            return null;
        }

        LinkedList<Double>  result = new LinkedList<>();
        LinkedList<Double>  to_predict = new LinkedList<>();
        int real_size = values.size();

        // add the number of real values
        result.add((double)real_size);

        // add real values
        result.addAll(values);

        // add initial values for prediction
        for (int i = 0; i < predict_start; i++) {
            result.add(values.get(i));
            to_predict.add(values.get(i));
        }

        for (int i = predict_start; i < real_size; i++) {
            double predicted_value = UnixCPULoadPrediction.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(values.get(i));
        }

        for (int i = 0; i < prediction_time; i++) {
            double predicted_value = UnixCPULoadPrediction.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(predicted_value);
        }

        return result;
    }

    @RequestMapping("/wma")
    public LinkedList<Double> wmaPrediction() {
        if(!sanityCheck()) {
            return null;
        }

        LinkedList<Double>  result = new LinkedList<>();
        LinkedList<Double>  to_predict = new LinkedList<>();
        int real_size = values.size();

        // add the number of real values
        result.add((double)real_size);

        // add real values
        result.addAll(values);

        // add initial values for prediction
        for (int i = 0; i < predict_start; i++) {
            result.add(values.get(i));
            to_predict.add(values.get(i));
        }

        for (int i = predict_start; i < real_size; i++) {
            double predicted_value = WMA.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(values.get(i));
        }

        for (int i = 0; i < prediction_time; i++) {
            double predicted_value = WMA.predict(to_predict);
            result.add(predicted_value);
            to_predict.add(predicted_value);
        }

        return result;
    }

    @RequestMapping("/all")
    public String allPredictions() {
        if (user == null)
            return "Please login to start simulating";

        LinkedList<Double> ll = new LinkedList<>();
        String algorithm = "TODO";
        String result = "";

        result += "Random " + randomPrediction() + "</br>";
        result += "BackPropagation " + backpropagationPrediction() + "</br>";
        result += "CasCor " + cascorPrediction() + "</br>";
        result += "EMA " + emaPrediction() + "</br>";
        result += "Homeostatic " + homeostaticPrediction() + "</br>";
        result += "Tendency " + tendencyPrediction() + "</br>";
        result += "Unix " + unixPrediction() + "</br>";
        result += "WMA " + wmaPrediction() + "</br>";

        String token = Utils.generateToken(timeSeriesInputEntityRepository);
//        Utils.writeResult(token, algorithm, result, timeSeriesInputEntityRepository);

        return result;
    }

    @RequestMapping(value = "/resultsFromToken", params = "token", method = POST)
    public String resultsFromToken(@RequestParam("token") String token) {
        List<TimeSeriesInputEntity> queryResult = timeSeriesInputEntityRepository.findByToken(token);
        String result = "No data available for this token";
        if (queryResult == null || queryResult.size() == 0) {
            return result;
        }

//        TimeSeriesInputEntity res = queryResult.remove(0);
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
    @RequestMapping(value = "/loginJava", method = RequestMethod.POST)
    public String login(@RequestParam Map<String,String> requestParams) throws Exception {
        return "Welcome "/* + user.getUsername()*/;
//        String username = requestParams.get("username");
//        String password = requestParams.get("password");
//        List<UserEntity> userEntities = userEntityRepository.findUser(username, password);
//        if (userEntities == null || userEntities.size() == 0) {
//            return "No user with those credentials";
//        }
//
//        user = userEntities.remove(0);
    }

    @ResponseBody
    @RequestMapping(value = "/statistics", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded","multipart/form-data"})
    public ArrayList<Double> getStatistics(@ModelAttribute StatisticsJSON response) throws Exception {
        ArrayList<Double> result = new ArrayList<>();

        double[] real = response.getReal();
        double[] predicted = response.getPredicted();

        // real statictics
        result.add(Utils.medie_aritmetica(real));
        result.add(Utils.medie_geometrica(real));
        result.add(Utils.medie_armonica(real));
        result.add(Utils.abatere_medie_patratica(real));
        result.add(Utils.amplitutine_absoluta(real));
        result.add(Utils.amplitudine_relativa(real));
        result.add(Utils.medie_patratica(real));
        result.add(Utils.mediana(real));
        result.add(Utils.coeficient_variatie(real));
        result.add(Utils.abatere_medie_liniara(real));

        // predicted statistics
        result.add(Utils.medie_aritmetica(predicted));
        result.add(Utils.medie_geometrica(predicted));
        result.add(Utils.medie_armonica(predicted));
        result.add(Utils.abatere_medie_patratica(predicted));
        result.add(Utils.amplitutine_absoluta(predicted));
        result.add(Utils.amplitudine_relativa(predicted));
        result.add(Utils.medie_patratica(predicted));
        result.add(Utils.mediana(predicted));
        result.add(Utils.coeficient_variatie(predicted));
        result.add(Utils.abatere_medie_liniara(predicted));

        return result;
    }
}