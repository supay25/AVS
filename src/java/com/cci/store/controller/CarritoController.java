/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.store.controller;

import com.cci.service.ComprasTO;
import com.cci.service.ProductoTO;
import com.cci.service.ServicioCompras;
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
    private String nombreTarjeta;
    private String nomTarjeta;
    private String direccion;
    private String metodoPago;
    private String correoCompra;
    private String provincia;
    private String distrito;
    private int codigoPostal;

    private List<ProductoTO> listaCarrito = new ArrayList<ProductoTO>();
    private ProductoTO selectedProducto;

    //Métodos
    //-------------Métodos del carrito-----------------
    public CarritoController() {
    }

    public void agregarAlCarrito(ProductoTO prodTO) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto Agregado al Carrito"));
        this.listaCarrito.add(prodTO);

    }

    public void redirigirCompra() {
        System.out.println(this.listaCarrito);
        this.redireccionar("/faces/RealizarCompra.xhtml");
    }

    public void deleteProductoCarrito() {
        listaCarrito.remove(this.selectedProducto);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto del carrito eliminado"));
        System.out.println("Aquí esta el producto del carrito" + listaCarrito);

    }

    public void deleteAllCarrito() {
        listaCarrito.clear();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto del carrito eliminado"));
        System.out.println("No hay nada se supone" + listaCarrito);

    }
    
    public void redireccionar(String ruta) {

        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {
        }
    }

    public void redireccionarLimpiaCarrito(String ruta) {

        listaCarrito.clear();

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
                if (nomTarjeta != null) {
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

    public double calcularTotalCarrito() {
        double total = 0.0;
        for (ProductoTO producto : listaCarrito) {
            total += producto.getPrecio () * producto.getCantidad();
        }
        double total1= total * 0.13;
        double total2;
        total2 = total - total1;        
        return total2;
    }
    
    public double totalFinal(){
       double total = 0.0;
        for (ProductoTO producto : listaCarrito) {
            total += producto.getPrecio() * producto.getCantidad();
        }
          
        return total;
        
        
    }
      public void Compras(){
            
            double total = totalFinal();
            
            ComprasTO compra = new ComprasTO(this.direccion, this.metodoPago,this.correoCompra, this.provincia,this.codigoPostal, this.nomTarjeta, this.numTarjeta, this.cvvTarjeta, total);
            ServicioCompras ser = new ServicioCompras();
            
            ser.Insertar(compra);
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

    public String getVerificarNumTarjeta() {
        return verificarNumTarjeta;
    }

    public void setVerificarNumTarjeta(String verificarNumTarjeta) {
        this.verificarNumTarjeta = verificarNumTarjeta;
    }

    public String getVerificarCvvTarjeta() {
        return verificarCvvTarjeta;
    }

    public void setVerificarCvvTarjeta(String verificarCvvTarjeta) {
        this.verificarCvvTarjeta = verificarCvvTarjeta;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

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

    public String getCorreoCompra() {
        return correoCompra;
    }

    public void setCorreoCompra(String correoCompra) {
        this.correoCompra = correoCompra;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    
}
