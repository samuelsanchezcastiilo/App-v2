

/**
 * Created by sistemas on 15/02/18.
 */
package com.example.sistemas.appmolinotransporte.Productos.Model;


public class ProducModel {

    private String consecutivo;
    private String factura;
    private String tipo;
    private String destino;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProducModel() {
    }

    /**
     *
     * @param destino
     * @param factura
     * @param tipo
     * @param consecutivo
     */
    public ProducModel(String consecutivo, String factura, String tipo, String destino) {
        super();
        this.consecutivo = consecutivo;
        this.factura = factura;
        this.tipo = tipo;
        this.destino = destino;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

}