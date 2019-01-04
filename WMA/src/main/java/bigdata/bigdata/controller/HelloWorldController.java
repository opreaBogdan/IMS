package bigdata.bigdata.controller;

import bigdata.algorithms.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
class HelloController {

    private int predict_start = 5;
    private int prediction_time = 5;


    @RequestMapping(value = "/predict", method = RequestMethod.POST)
    public List<Double> predict(@RequestBody List<Double> ids) {
        LinkedList<Double> values = new LinkedList<>();
        for (double val : ids)
            values.add(val);

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
}