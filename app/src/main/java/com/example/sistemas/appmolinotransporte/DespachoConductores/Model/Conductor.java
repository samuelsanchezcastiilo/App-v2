package com.example.sistemas.appmolinotransporte.DespachoConductores.Model;

/**
 * Created by developer on 15/12/17.
 */

public class Conductor {
    private String name;
    private String apellido;
    private String cedula;
    private String hourStart;
    private String hourWait;
    private String numberBill;
    private String placa;
    private String estado;

    public Conductor() {
    }

    public Conductor(String name, String apellido, String cedula, String hourStart, String hourWait, String numberBill, String placa, String estado) {
        this.name = name;
        this.apellido = apellido;
        this.cedula = cedula;
        this.hourStart = hourStart;
        this.hourWait = hourWait;
        this.numberBill = numberBill;
        this.placa = placa;
        this.estado = estado;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getHourStart() {
        return hourStart;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }

    public String getHourWait() {
        return hourWait;
    }

    public void setHourWait(String hourWait) {
        this.hourWait = hourWait;
    }

    public String getNumberBill() {
        return numberBill;
    }

    public void setNumberBill(String numberBill) {
        this.numberBill = numberBill;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public  String getEstado()
    {
        return estado;
    }
    public void setEstado(String estado){this.estado =estado;}
}
