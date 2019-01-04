package ims.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PredictionJSON {

    @JsonProperty("MedieAritmetica")
    public double MedieAritmetica;
    @JsonProperty("MedieGeometrica")
    public double MedieGeometrica;
    @JsonProperty("medieArmonica")
    public double MedieArmonica;
    @JsonProperty("AbatereMediePatratica")
    public double AbatereMediePatratica;
    @JsonProperty("amplitudineAbsoluta")
    public double AmplitudineAbsoluta;
    @JsonProperty("AmplitudineRelativa")
    public double AmplitudineRelativa;
    @JsonProperty("MediePatratica")
    public double MediePatratica;
    @JsonProperty("Mediana")
    public double Mediana;
    @JsonProperty("CoeficientVariatie")
    public double CoeficientVariatie;
    @JsonProperty("AbatereMedieLiniara")
    public double AbatereMedieLiniara;

    public PredictionJSON(double medie_aritmetica,
                          double medie_geometrica,
                          double medie_armonica,
                          double abatere_medie_patratica,
                          double amplitudine_absoluta,
                          double amplitudine_relativa,
                          double medie_patratica,
                          double mediana,
                          double coeficient_variatie,
                          double abatere_medie_liniara) {
        this.MedieAritmetica = medie_aritmetica;
        this.MedieGeometrica = medie_geometrica;
        this.MedieArmonica = medie_armonica;
        this.AbatereMediePatratica = abatere_medie_patratica;
        this.AmplitudineAbsoluta = amplitudine_absoluta;
        this.AmplitudineRelativa = amplitudine_relativa;
        this.MediePatratica = medie_patratica;
        this.Mediana = mediana;
        this.CoeficientVariatie = coeficient_variatie;
        this.AbatereMedieLiniara = abatere_medie_liniara;
    }

    public PredictionJSON() {}

    public double getMedieAritmetica() {
        return MedieAritmetica;
    }

    public void setMedieAritmetica(double medieAritmetica) {
        MedieAritmetica = medieAritmetica;
    }

    public double getMedieGeometrica() {
        return MedieGeometrica;
    }

    public void setMedieGeometrica(double medieGeometrica) {
        MedieGeometrica = medieGeometrica;
    }

    public double getMedieArmonica() {
        return MedieArmonica;
    }

    public void setMedieArmonica(double medieArmonica) {
        MedieArmonica = medieArmonica;
    }

    public double getAbatereMediePatratica() {
        return AbatereMediePatratica;
    }

    public void setAbatereMediePatratica(double abatereMediePatratica) {
        AbatereMediePatratica = abatereMediePatratica;
    }

    public double getAmplitudineAbsoluta() {
        return AmplitudineAbsoluta;
    }

    public void setAmplitudineAbsoluta(double amplitudineAbsoluta) {
        AmplitudineAbsoluta = amplitudineAbsoluta;
    }

    public double getAmplitudineRelativa() {
        return AmplitudineRelativa;
    }

    public void setAmplitudineRelativa(double amplitudineRelativa) {
        AmplitudineRelativa = amplitudineRelativa;
    }

    public double getMediePatratica() {
        return MediePatratica;
    }

    public void setMediePatratica(double mediePatratica) {
        MediePatratica = mediePatratica;
    }

    public double getMediana() {
        return Mediana;
    }

    public void setMediana(double mediana) {
        Mediana = mediana;
    }

    public double getCoeficientVariatie() {
        return CoeficientVariatie;
    }

    public void setCoeficientVariatie(double coeficientVariatie) {
        CoeficientVariatie = coeficientVariatie;
    }

    public double getAbatereMedieLiniara() {
        return AbatereMedieLiniara;
    }

    public void setAbatereMedieLiniara(double abatereMedieLiniara) {
        AbatereMedieLiniara = abatereMedieLiniara;
    }
}
