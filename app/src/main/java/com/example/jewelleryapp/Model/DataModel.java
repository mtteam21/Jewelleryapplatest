package com.example.jewelleryapp.Model;

public class DataModel {
    private String privacy;
    private String terms;

    public DataModel(String privacy, String terms) {
        this.privacy = privacy;
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
}
