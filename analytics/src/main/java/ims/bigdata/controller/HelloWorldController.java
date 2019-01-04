package ims.bigdata.controller;

import ims.PredictionJSON;
import ims.utils.Utils;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
class HelloController {

    @RequestMapping(value = "/statistics", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Async
    public PredictionJSON analytics(@RequestBody List<Double> ids) throws Exception {
        Double[] values = new Double[ids.size()];
        values = ids.toArray(values);

        return new PredictionJSON(Utils.medie_aritmetica(values),
                Utils.medie_geometrica(values),
                Utils.medie_armonica(values),
                Utils.abatere_medie_patratica(values),
                Utils.amplitutine_absoluta(values),
                Utils.amplitudine_relativa(values),
                Utils.medie_patratica(values),
                Utils.mediana(values),
                Utils.coeficient_variatie(values),
                Utils.abatere_medie_liniara(values));
    }
}