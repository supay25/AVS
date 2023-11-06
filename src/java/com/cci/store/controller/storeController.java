/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.store.controller;

import com.cci.service.ProductoTO;
import com.cci.service.ServicioProducto;
import com.cci.service.ServicioTienda;
import com.cci.service.TiendaTO;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "storeController")
@SessionScoped
public class storeController {

    private String nombre;
    private String descripcion;
    private String calificacion;
    private String Categoria;
    private TiendaTO selectedUsuario;
    ServicioTienda tienda = new ServicioTienda();
    private List<TiendaTO> listaRetorno = tienda.lista();
    ServicioProducto products = new ServicioProducto();
    private List<ProductoTO> listaRetornoProducts;
    
    
    

    public ServicioTienda getTienda() {
        return tienda;
    }

    public void setTienda(ServicioTienda tienda) {
        this.tienda = tienda;
    }

    public ServicioProducto getProducts() {
        return products;
    }

    public void setProducts(ServicioProducto products) {
        this.products = products;
    }

    public List<ProductoTO> getListaRetornoProducts() {
        return listaRetornoProducts;
    }

    public void setListaRetornoProducts(List<ProductoTO> listaRetornoProducts) {
        this.listaRetornoProducts = listaRetornoProducts;
    }
    
    

    public List<TiendaTO> getListaRetorno() {
        return listaRetorno;
    }

    public void setListaRetorno(List<TiendaTO> listaRetorno) {
        this.listaRetorno = listaRetorno;
    }

    public storeController() {

    }

    public void openNewPage() {                        
        this.redireccionar("/faces/tienda.xhtml");
        
        

        //FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos Invalidos", "La clave o correo no son correctos"));

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

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public TiendaTO getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(TiendaTO selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {
        }
    }

}
