package com.hand.avssign.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentForSignature {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("passport")
    @Expose
    private Passport passport;
    @SerializedName("plateNumber")
    @Expose
    private String plateNumber;
    @SerializedName("client")
    @Expose
    private String client;
    @SerializedName("department")
    @Expose
    private Department department;
    @SerializedName("weighings")
    @Expose
    private List<Weighing> weighings = null;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Weighing> getWeighings() {
        return weighings;
    }

    public void setWeighings(List<Weighing> weighings) {
        this.weighings = weighings;
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