/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.service;

import java.io.Serializable;

/**
 *
 * @author Jose
 */
public class ProductoTO implements Serializable{
    
    //Atributo
    private String nombre;
    private String descripcion;
    private int tienda;
    private int precio;
    private int id;
    private int cantidad;
    
    //Constructores
    public ProductoTO() {
    }

    public ProductoTO(String nombre, String descripcion, int precio, int cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;       
        this.cantidad = cantidad;
    }  

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getTienda() {
        return tienda;
    }

    public void setTienda(int tienda) {
        this.tienda = tienda;
    }
    
    @Override
    public String toString() {
        return "ProductoTO{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio+ '}';
    }  
    
}
