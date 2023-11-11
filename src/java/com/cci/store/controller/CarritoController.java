/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.store.controller;

import com.cci.service.ProductoTO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author lizan
 */
@ManagedBean(name = "carritoController")
@SessionScoped
public class CarritoController {

    //Atributos
    private List<ProductoTO> listaRetornoCarrito;
    private ProductoTO selectedProducto;

    //Métodos
    public CarritoController() {
    }

    public void openNewProducto() {
        this.selectedProducto = new ProductoTO();
    }

    //Getters and Setters
    public List<ProductoTO> getListaRetornoCarrito() {
        return listaRetornoCarrito;
    }

    public void setListaRetornoCarrito(List<ProductoTO> listaRetornoCarrito) {
        this.listaRetornoCarrito = listaRetornoCarrito;
    }

    public ProductoTO getSelectedProducto() {
        return selectedProducto;
    }

    public void setSelectedProducto(ProductoTO selectedProducto) {
        this.selectedProducto = selectedProducto;
    }

}
