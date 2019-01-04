package bigdata.utils;

import bigdata.entities.TimeSeriesInputEntity;
import bigdata.repositories.TimeSeriesInputEntityRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Utils {

    public static String generateToken(TimeSeriesInputEntityRepository timeSeriesInputEntityRepository) {
        String result = "";
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVXYZWabcdefghijklmnopqrstuvxyzw0123456789";
        int len = alphanumeric.length();
        boolean done = false;

        while (!done) {
            for (int i = 0; i < Constants.tokenSize; i++) {
                char c = alphanumeric.charAt((int)(Math.random() * len));
                result += c;
            }

            List<TimeSeriesInputEntity> sql_result = timeSeriesInputEntityRepository.findAll();
            boolean ok = true;
            for(TimeSeriesInputEntity res : sql_result) {
                if (res.getToken().equals(result)) {
                    ok = false;
                    break;
                }
                break;
            }
            if (ok == true)
                done = true;
        }

        return result;
    }

    public static void writeResult(String token, String content, TimeSeriesInputEntityRepository timeSeriesInputEntityRepository) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.serverBasePath + Constants.delimiter + token));
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TimeSeriesInputEntity output = new TimeSeriesInputEntity(token);
        timeSeriesInputEntityRepository.save(output);
    }

    // utila cand nu exista multe valori extreme
    public static double medie_aritmetica(double[] valori) {
        double suma = 0;
        for (double valoare : valori)
            suma += valoare;
        return suma / (valori.length);
    }

    // utila cand exista valori extreme, dar fara valori nule sau negative ... cand
    // seria e dinamica
    public static double medie_geometrica(double[] valori) throws Exception{
        double produs = 1;
        for (double valoare : valori) {
            if (valoare <= 0)
                return -1;
            produs *= valoare;
        }
        return Math.pow(produs, 1/(valori.length));
    }

    // pentru serii in care valorile sunt din ce in ce mai mari
    // insesibila la valori nule sau negative
    // da importanta valorilor mari din serie
    public static double medie_patratica(double[] valori) {
        double suma = 0;
        for (double valoare : valori)
            suma += (valoare * valoare);
        suma /= valori.length;
        return suma;
    }

    // exprima caracterul sintetic. Pentru cand frecvenetele sunt egale
    public static double medie_armonica(double[] valori) {
        double suma = 0;
        for (double valoare : valori)
            suma += (1/valoare);
        return valori.length / suma;
    }

    // valoarea care imparte intervalul in 2 intervale egale
    public static double mediana(double[] valori) {
        Arrays.sort(valori);
        return valori[valori.length/2];
    }

    public static double amplitutine_absoluta(double[] valori) {
        double min = valori[0], max = valori[0];
        for (double valoare : valori) {
            if (valoare < min)
                min = valoare;
            if (valoare > max)
                max = valoare;
        }

        return max - min;
    }

    public static double amplitudine_relativa(double[] valori) {
        return 100 * amplitutine_absoluta(valori) / medie_aritmetica(valori);
    }

    public static double abatere_individuala_absoluta(double[] valori, double valoare) {
        return valoare - medie_aritmetica(valori);
    }

    public static double abatere_individuala_relativa(double[] valori, double valoare) {
        return abatere_individuala_absoluta(valori, valoare) / medie_aritmetica(valori);
    }

    public static double abatere_medie_liniara(double[] valori) {
        double suma = 0;
        double media_aritmetica = medie_aritmetica(valori);

        for (double valoare : valori)
            suma += Math.abs(valoare - media_aritmetica);

        return suma / valori.length;
    }

    // da ponderi mai mari abaterilor mai mari
    public static double abatere_medie_patratica(double[] valori) {
        double suma = 0;
        double media_aritmetica = medie_aritmetica(valori);

        for (double valoare : valori)
            suma += Math.pow(Math.abs(valoare - media_aritmetica), 2);

        return Math.sqrt(suma / valori.length);
    }

    // valoare mica -> omogenitate
    // peste un coeficient de 30 - 40 % media nu mai e concludenta
    // daca media aritmetica e aproape de zero, coeficientul nu are o interpretare corecta
    public static double coeficient_variatie(double[] valori) {
        return 100 * abatere_medie_patratica(valori) / medie_aritmetica(valori);
    }

    public static double dispersie(double[] valori) {
        double suma = 0;
        double media_aritmetica = medie_aritmetica(valori);

        for (double valoare : valori)
            suma += Math.pow(valoare - media_aritmetica, 2);

        return suma / valori.length;
    }

    public static LinkedList<Double> parseInputFile(BufferedReader content, String token, TimeSeriesInputEntityRepository timeSeriesInputEntityRepository) {
        LinkedList<Double> result = new LinkedList<>();

        String line;
        String toSave = "";

        try {
            while ((line = content.readLine()) != null) {
                toSave = toSave + line + "\n";
                double value = Double.parseDouble(line);
                result.add(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Utils.writeResult(token, toSave, timeSeriesInputEntityRepository);

        return result;
    }

    public static LinkedList<Double> parseInputFileFromToken(BufferedReader content) {
        LinkedList<Double> result = new LinkedList<>();

        String line;
        String toSave = "";

        try {
            line = content.readLine();
            result.add(Double.parseDouble(line));

            while ((line = content.readLine()) != null) {
                toSave = toSave + line + "\n";
                double value = Double.parseDouble(line);
                result.add(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
