
/**
 * Created by developer on 15/12/17.
 */
package com.example.sistemas.appmolinotransporte.DespachoConductores.Model;


public class Conductor {

    private String name;
    private String apellido;
    private String cedula;
    private String placa;
    private String consecutivo;
    private String estado;
    private String hourSatrt;

    /**
     * No args constructor for use in serialization
     *
     */
    public Conductor() {
    }


    public Conductor(String name, String apellido, String cedula, String placa, String consecutivo, String estado, String hourSatrt) {
        super();
        this.name = name;
        this.apellido = apellido;
        this.cedula = cedula;
        this.placa = placa;
        this.consecutivo = consecutivo;
        this.estado = estado;
        this.hourSatrt = hourSatrt;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHourSatrt() {
        return hourSatrt;
    }

    public void setHourSatrt(String hourSatrt) {
        this.hourSatrt = hourSatrt;
    }

}


