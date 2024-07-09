package com.talon.models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

public class Payroll {
    private final FloatProperty epf = new SimpleFloatProperty();

    public final FloatProperty epfProperty() {
        return this.epf;
    }

    public final float getEmployeeEpf() {
        this.epfProperty().set(0.11f);
        return this.epfProperty().get();
    }

    public final float getEmployerEpf() {
        this.epfProperty().set(0.13f);
        return this.epfProperty().get();
    }

    public final void setEpf(final float epf){
        this.epfProperty().set(epf);
    }

    private final FloatProperty socso = new SimpleFloatProperty();

    public final FloatProperty socsoProperty() {
        return this.socso;
    }

    public final float getEmployeeSocso() {
        this.socsoProperty().set(0.005f);
        return this.socsoProperty().get();
    }

    public final float getEmployerSocso() {
        this.socsoProperty().set(0.018f);
        return this.socsoProperty().get();
    }

    public final void setSocso(final float socso){
        this.socsoProperty().set(socso);
    }



    private final FloatProperty eis = new SimpleFloatProperty();

    public final FloatProperty eisProperty() {
        return this.eis;
    }

    public final float getEmployeeEis() {
        this.eisProperty().set(0.002f);
        return this.eisProperty().get();
    }

    public final float getEmployerEis() {
        this.eisProperty().set(0.002f);
        return this.eisProperty().get();
    }

    public final void setEis(final float eis){
        this.eisProperty().set(eis);
    }

    private final FloatProperty pcb = new SimpleFloatProperty();

    public final FloatProperty pcbProperty() {
        return this.pcb;
    }

    public final float getEmployeePcb() {
        this.pcbProperty().set(0.05f);
        return this.pcbProperty().get();
    }

    public final void setPcb(final float pcb){
        this.epfProperty().set(pcb);
    }

    public Payroll() {
        
    }

    @Override
    public String toString() {
        return String.format("%f %f %f %f", getEmployeeEpf(), getEmployeeSocso(), getEmployeeEis(), getEmployeePcb());
    }

    
}

