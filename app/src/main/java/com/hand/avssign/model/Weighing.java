package com.hand.avssign.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weighing {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("brutto")
    @Expose
    private int brutto;
    @SerializedName("tare")
    @Expose
    private int tare;
    @SerializedName("sor")
    @Expose
    private int sor;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("netto")
    @Expose
    private int netto;
    @SerializedName("sum")
    @Expose
    private double sum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrutto() {
        return brutto;
    }

    public void setBrutto(int brutto) {
        this.brutto = brutto;
    }

    public int getTare() {
        return tare;
    }

    public void setTare(int tare) {
        this.tare = tare;
    }

    public int getSor() {
        return sor;
    }

    public void setSor(int sor) {
        this.sor = sor;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNetto() {
        return netto;
    }

    public void setNetto(int netto) {
        this.netto = netto;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

}