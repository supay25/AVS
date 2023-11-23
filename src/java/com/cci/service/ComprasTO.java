
package com.cci.service;

import java.io.Serializable;

public class ComprasTO implements Serializable {
    
    //Atributo
    private String direccion;
    private String metodoPago;
    private String correo;
    private String provincia;
    private int codPostal; 
    private String nomTarjeta;
    private int numTarjeta;
    private int cvv;
    private int total;
    
    
    //Constructor
    public ComprasTO() {
    }

    public ComprasTO(String direccion, String metodoPago, String correo, String provincia, int codPostal, String nomTarjeta, int numTarjeta, int cvv, int total) {
        this.direccion = direccion;
        this.metodoPago = metodoPago;
        this.correo = correo;
        this.provincia = provincia;
        this.codPostal = codPostal;
        this.nomTarjeta = nomTarjeta;
        this.numTarjeta = numTarjeta;
        this.cvv = cvv;
        this.total = total;
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
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
