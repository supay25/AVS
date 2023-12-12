
package com.cci.service;

import java.io.Serializable;

public class ComprasTO implements Serializable {
    
    //Atributo
    private String direccion;
    private String metodoPago;
    private String correo;
    private String estado; 
    private String nomTarjeta;
    private String codigoCompra;
    private int codPostal;
    private int numTarjeta;
    private int cvv;
    private double total;
    
    
    //Constructor
    public ComprasTO() {
    }

    public ComprasTO(String direccion, String metodoPago, String correo, String estado, int codPostal, String nomTarjeta, int numTarjeta, int cvv, double total, String codigoCompra) {
        this.direccion = direccion;
        this.metodoPago = metodoPago;
        this.correo = correo;
        this.estado = estado;
        this.codPostal = codPostal;
        this.nomTarjeta = nomTarjeta;
        this.numTarjeta = numTarjeta;
        this.cvv = cvv;
        this.total = total;
        this.codigoCompra = codigoCompra;
    }
    
    // Getters and Setters

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getProvincia() {
        return estado;
    }

    public void setProvincia(String estado) {
        this.estado = estado;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    public String getNomTarjeta() {
        return nomTarjeta;
    }

    public void setNomTarjeta(String nomTarjeta) {
        this.nomTarjeta = nomTarjeta;
    }

    public int getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(int numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCodigoCompra() {
        return codigoCompra;
    }

    public void setCodigoCompra(String codigoCompra) {
        this.codigoCompra = codigoCompra;
    }
    
    
    
    
}
