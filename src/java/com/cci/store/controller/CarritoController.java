/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.store.controller;

import com.cci.service.ProductoTO;
import com.cci.service.ServicioProducto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lizan
 */
@ManagedBean(name = "carritoController")
@SessionScoped
public class CarritoController implements Serializable {

    //Atributos
    private int numTarjeta;
    private String verificarNumTarjeta;
    private int cvvTarjeta;
    private String verificarCvvTarjeta;
    private String nomTarjeta;

    private List<ProductoTO> listaCarrito = new ArrayList<ProductoTO>();
    private ProductoTO selectedProducto;

    //Métodos
    //-------------Métodos del carrito-----------------
    public CarritoController() {
    }

    public void agregarAlCarrito(ProductoTO prodTO) {
        this.listaCarrito.add(prodTO);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto Agregado al Carrito"));
    }

    public void redirigirCompra() {
        this.redireccionar("/faces/RealizarCompra.xhtml");
    }

    public void deleteProductoCarrito() {
        listaCarrito.remove(this.selectedProducto);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto del carrito eliminado"));
        System.out.println("Aquí esta el producto del carrito" + listaCarrito);

    }

    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {
        }
    }

    //-----------------------Métodos de la compra----------------------
    public void realizarCompra() {
        if (verificarNumTarjeta.length() == 16 && verificarNumTarjeta.matches("[0-9]+")) {
            // La entrada es válida
            numTarjeta = Integer.parseInt(verificarNumTarjeta);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Número de tarjeta válido"));
            System.out.println("Número de tarjeta válido: " + numTarjeta);

            if (verificarCvvTarjeta.length() == 3 && verificarCvvTarjeta.matches("[0-9]+")) {
                cvvTarjeta = Integer.parseInt(verificarCvvTarjeta);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Número del cvv de la tarjeta válido"));
                System.out.println("Número de tarjeta válido: " + numTarjeta);
                if (nomTarjeta != null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nombre de persona válido"));
                    this.redireccionar("/faces/tienda.xhtml");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Complete el cuadro nombre"));
                }
                
            } else {
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Número del cvv de la tarjeta inválido"));
                System.out.println("El número de tarjeta debe contener exactamente 16 números.");
            }
        } else {
            // La entrada no es válida
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Número de tarjeta inválido"));
            System.out.println("El número de tarjeta debe contener exactamente 16 números.");
        }
    }

    //Getters and Setters
    public ProductoTO getSelectedProducto() {
        return selectedProducto;
    }

    public void setSelectedProducto(ProductoTO selectedProducto) {
        this.selectedProducto = selectedProducto;
    }

    public int getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(int numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public int getCvvTarjeta() {
        return cvvTarjeta;
    }

    public void setCvvTarjeta(int cvvTarjeta) {
        this.cvvTarjeta = cvvTarjeta;
    }

    public String getNomTarjeta() {
        return nomTarjeta;
    }

    public void setNomTarjeta(String nomTarjeta) {
        this.nomTarjeta = nomTarjeta;
    }

    public List<ProductoTO> getListaCarrito() {
        return listaCarrito;
    }

    public void setListaCarrito(List<ProductoTO> listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

}
