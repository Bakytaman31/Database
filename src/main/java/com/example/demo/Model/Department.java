package com.example.demo.Model;

import javafx.beans.property.*;
import java.util.Date;

public class Department {
    private StringProperty Dname;
    private IntegerProperty Dnumber;
    private StringProperty Mgr_ssn;
    private ObjectProperty<Date> Mgr_start_date;

    public Department(
            String dname,
            int dnumber,
            String mgr_ssn,
            Date mgr_start_date
    ){
        Dname = new SimpleStringProperty(dname);
        Dnumber = new SimpleIntegerProperty(dnumber);
        Mgr_ssn = new SimpleStringProperty(mgr_ssn);
        Mgr_start_date = new SimpleObjectProperty<>(mgr_start_date);
    }



    public String getDname() {
        return Dname.get();
    }

    public StringProperty dnameProperty() {
        return Dname;
    }

    public void setDname(String dname) {
        this.Dname.set(dname);
    }

    public int getDnumber() {
        return Dnumber.get();
    }

    public IntegerProperty dnumberProperty() {
        return Dnumber;
    }

    public void setDnumber(int dnumber) {
        this.Dnumber.set(dnumber);
    }

    public String getMgr_ssn() {
        return Mgr_ssn.get();
    }

    public StringProperty mgr_ssnProperty() {
        return Mgr_ssn;
    }

    public void setMgr_ssn(String mgr_ssn) {
        this.Mgr_ssn.set(mgr_ssn);
    }

    public Date getMgr_start_date() {
        return Mgr_start_date.get();
    }

    public ObjectProperty<Date> mgr_start_dateProperty() {
        return Mgr_start_date;
    }

    public void setMgr_start_date(Date mgr_start_date) {
        this.Mgr_start_date.set(mgr_start_date);
    }
}
