package com.jovin.customcalendar;

public class Users {

    private String NAME;
    private String EMAIL;
    private String DESIGNATION;
    private String SHIFT;
    private String DATE;


    public Users() {
    }

    public Users(String NAME) {
        this.NAME = NAME;
    }

    public Users(String NAME,String SHIFT) {
        this.NAME = NAME;
        this.SHIFT = SHIFT;
    }

    public Users(String NAME, String EMAIL, String DESIGNATION) {
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.DESIGNATION = DESIGNATION;
    }

    public Users(String NAME, String SHIFT, String DESIGNATION,String DATE) {
        this.NAME = NAME;
        this.SHIFT = SHIFT;
        this.DESIGNATION = DESIGNATION;
        this.DATE = DATE;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getDESIGNATION() {
        return DESIGNATION;
    }

    public void setDESIGNATION(String DESIGNATION) {
        this.DESIGNATION = DESIGNATION;
    }

    public String getSHIFT() {
        return SHIFT;
    }
}
