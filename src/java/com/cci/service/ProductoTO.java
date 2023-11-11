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

    public ProductoTO() {
    }
   
    

    public ProductoTO(String nombre, String descripcion, int precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
       
      
    }
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
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

    @Override
    public String toString() {
        return "ProductoTO{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + ", id=" + id + '}';
    }  
    
}
