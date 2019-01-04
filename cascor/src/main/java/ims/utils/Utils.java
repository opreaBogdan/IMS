package ims.utils;

import java.util.Arrays;

public class Utils {

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
}
