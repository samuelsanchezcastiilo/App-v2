package com.example.sistemas.appmolinotransporte;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by developer on 17/01/18.
 */
public class ConductorModel implements JsonSerializer<ConductorModel>{

    private String cedula;
    private String name;
    private String apellido;
    private String placa;
    private String estado;
    private String hourStart;
    private String hourWait;
    private String numberBill;

    /**
     * No args constructor for use in serialization
     *
     */
    public ConductorModel() {
    }

    /**
     *
     * @param apellido
     * @param numberBill
     * @param hourStart
     * @param estado
     * @param name
     * @param placa
     * @param hourWait
     * @param cedula
     */
    public ConductorModel(String cedula, String name, String apellido, String placa, String estado, String hourStart, String hourWait, String numberBill) {
        super();
        this.cedula = cedula;
        this.name = name;
        this.apellido = apellido;
        this.placa = placa;
        this.estado = estado;
        this.hourStart = hourStart;
        this.hourWait = hourWait;
        this.numberBill = numberBill;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    @Override
    public JsonElement serialize(ConductorModel src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}



