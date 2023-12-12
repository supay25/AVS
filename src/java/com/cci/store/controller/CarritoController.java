/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.store.controller;

import com.cci.service.ComprasTO;
import com.cci.service.ProductoTO;
import com.cci.service.ServicioCompras;
import com.cci.service.ServicioDetalleCompra;
import com.cci.service.ServicioProducto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
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
    private String CodCompra;

    @PostConstruct
    public void init() {
        if (getCodCompra() == null) {
            generarCodigoAleatorio();
        }
    }
    private List<ProductoTO> listaCarrito = new ArrayList<ProductoTO>();
    private ProductoTO selectedProducto;

    //Métodos
    //-------------Métodos del carrito-----------------
    public CarritoController() {
    }

    public void agregarAlCarrito(ProductoTO prodTO) {
        System.out.println("Test " + prodTO.getNombre());
        
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto Agregado al Carrito"));
        
        this.listaCarrito.add(prodTO);
        System.out.println(listaCarrito);

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

    public void generarCodigoAleatorio() {
        int longitud = 8;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codigo = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(indice));
        }

        setCodCompra(codigo.toString());
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
    public double calcularTotalCarrito() {
        double total = 0.0;
        for (ProductoTO producto : listaCarrito) {
            total += producto.getPrecio() * producto.getCantidad();
        }
        double total1 = total * 0.13;
        double total2;
        total2 = total - total1;
        return total2;
    }

    public double totalFinal() {
        double total = 0.0;
        for (ProductoTO producto : listaCarrito) {
            total += producto.getPrecio() * producto.getCantidad();
        }

        return total;

    }
    
    public double IVA (){
        
        double IVA = 0.0;
       
            IVA = totalFinal()- calcularTotalCarrito();
            
            return IVA;
    }

    public void Compras() {
        try {

            double total = totalFinal();

            ComprasTO compra = new ComprasTO(this.direccion, this.metodoPago, this.correoCompra, this.provincia, this.codigoPostal, this.nomTarjeta, this.numTarjeta, this.cvvTarjeta, total, this.CodCompra);
            ServicioCompras ser = new ServicioCompras();

            ser.Insertar(compra);
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Compra realizada", "Gracias por preferir a avs"));
            int idEN = ser.verID(compra.getCodigoCompra());
            
                
            for (ProductoTO producto : listaCarrito) {
                ServicioDetalleCompra sdc = new ServicioDetalleCompra();
                ServicioProducto sp = new ServicioProducto();
                
                int IdProducto =sp.obtenerIdProducto(producto);
                int idTiendaRela = sdc.verTiendaRela(IdProducto);
                
                System.out.println(idTiendaRela);
                System.out.println(IdProducto);
                
                sdc.Insertar(idEN, producto.getNombre(), producto.getCantidad(),idTiendaRela );
               
            }
            
            this.redireccionar("/faces/FacturaElectronica.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo no vinculado", "Porfavor eliga un correo vinculado a avs"));

        }

    }
    public void redireccionarProductos(String ruta) {
         listaCarrito = new ArrayList<ProductoTO>();
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {
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

    public String getCodCompra() {
        return CodCompra;
    }

    public void setCodCompra(String CodCompra) {
        this.CodCompra = CodCompra;
    }

}
