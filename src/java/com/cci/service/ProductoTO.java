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
    private String precio;
    private int id;
   

    public ProductoTO(String nombre, String descripcion, String precio, int id) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id = id;
      
    }
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public ProductoTO() {
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

   
    
    
    
}
