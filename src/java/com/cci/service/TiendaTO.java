/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.service;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jose
 */public class TiendaTO implements Serializable {
    //atributos
    private String nombre;
    private String descripcion;
    private String calificacion;
    private String categoria;
    private int idl;
    private List<ProductoTO> listaProductos;
    
    //Constructores
    public TiendaTO(String nombre, String descripcion, String calificacion, String categoria, int idl) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.categoria = categoria;
        this.idl = idl;
    }
    
    public TiendaTO() {
    }
    
    //Getters and Setters
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

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String Categoria) {
        this.categoria = Categoria;
    }
    
    public int getIdl() {
        return idl;
    }

    public void setIdl(int idl) {
        this.idl = idl;
    }
    
    public List<ProductoTO> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoTO> listaProductos) {
        this.listaProductos = listaProductos;
    }  
    
}
