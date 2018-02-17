

 package com.example.sistemas.appmolinotransporte.Home.Model;


 import com.google.gson.JsonElement;
 import com.google.gson.JsonSerializationContext;
 import com.google.gson.JsonSerializer;

 import java.lang.reflect.Type;

 public class ConductorModel implements JsonSerializer<ConductorModel> {

    private String name;
    private String apellido;
    private String cedula;
    private String placa;
    private String consecutivo;
    private String estado;
    private String fecha;
    private String hourStart;

    /**
     * No args constructor for use in serialization
     *
     */
    public ConductorModel() {
    }

    /**
     *
     * @param apellido
     * @param hourStart
     * @param estado
     * @param fecha
     * @param name
     * @param consecutivo
     * @param placa
     * @param cedula
     */
    public ConductorModel(String name, String apellido, String cedula, String placa, String consecutivo, String estado, String fecha, String hourStart) {
        super();
        this.name = name;
        this.apellido = apellido;
        this.cedula = cedula;
        this.placa = placa;
        this.consecutivo = consecutivo;
        this.estado = estado;
        this.fecha = fecha;
        this.hourStart = hourStart;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHourStart() {
        return hourStart;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }

     @Override
     public JsonElement serialize(ConductorModel src, Type typeOfSrc, JsonSerializationContext context) {
         return null;
     }
 }