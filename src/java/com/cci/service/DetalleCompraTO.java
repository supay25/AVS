/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.service;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class DetalleCompraTO implements Serializable  {
    
    //Atributos
    private String nombreProducto;
    private int cantidad;

    //Constructores
    public DetalleCompraTO() {
    }

    public DetalleCompraTO(String nombreProducto, int cantidad) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
    }

    //Getters and setters
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
