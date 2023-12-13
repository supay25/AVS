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
    private String nombre;
    private String descripcion;
    private int precio;
    private int id;
    private int cantidad;
    private String imagen;
    
    public ProductoTO() {
    }

    public ProductoTO(String imagen) {
        this.imagen = imagen;
    }
    

    public ProductoTO(String nombre, String descripcion, int precio, int cantidad, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;       
        this.cantidad = cantidad;
        this.imagen = imagen;
    }  

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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    
    @Override
    public String toString() {
        return "ProductoTO{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio+ '}';
    }  
    
}
