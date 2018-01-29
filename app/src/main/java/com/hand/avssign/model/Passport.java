package com.hand.avssign.model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Passport {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("mname")
    @Expose
    private String mname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("registration")
    @Expose
    private String registration;
    @SerializedName("series")
    @Expose
    private int series;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("issuedate")
    @Expose
    private String issuedate;
    @SerializedName("issuedby")
    @Expose
    private String issuedby;
    @SerializedName("birthdateHF")
    @Expose
    private String birthdateHF;
    @SerializedName("issuedateHF")
    @Expose
    private String issuedateHF;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getIssuedby() {
        return issuedby;
    }

    public void setIssuedby(String issuedby) {
        this.issuedby = issuedby;
    }

    public String getBirthdateHF() {
        return birthdateHF;
    }

    public void setBirthdateHF(String birthdateHF) {
        this.birthdateHF = birthdateHF;
    }

    public String getIssuedateHF() {
        return issuedateHF;
    }

    public void setIssuedateHF(String issuedateHF) {
        this.issuedateHF = issuedateHF;
    }

}