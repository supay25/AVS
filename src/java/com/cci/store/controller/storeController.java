/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.store.controller;

import com.cci.service.ComprasTO;
import com.cci.service.DetalleCompraTO;
import com.cci.service.ProductoTO;
import com.cci.service.ServicioCompras;
import com.cci.service.ServicioDetalleCompra;
import com.cci.service.ServicioProducto;
import com.cci.service.ServicioTienda;
import com.cci.service.ServicioUsuario;
import com.cci.service.TiendaTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.hbar.HorizontalBarChartDataSet;
import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "storeController")
@SessionScoped
public class storeController {

//Atributos
    private String nombre;
    private String descripcion;
    private String calificacion;
    private String Categoria;
    private List<ProductoTO> listaRetornoProducts;
    private ProductoTO selectedProducto;
    private ArrayList<ProductoTO> listaCarrito = new ArrayList<ProductoTO>();

    ServicioTienda tienda = new ServicioTienda();
    private List<TiendaTO> listaRetorno = tienda.lista();
    private TiendaTO selectedUsuario = new TiendaTO();
    ServicioCompras compra = new ServicioCompras();
    ServicioDetalleCompra sdc = new ServicioDetalleCompra();
    private Set<ComprasTO> ultimasCompras;
    int idProducto;
    private List<ComprasTO> seguimientoCliente;
    private List<DetalleCompraTO> verFacturasEspe;
     private  String image2;
     private String imgTienda;
    private UploadedFile file;
    private String destinationFile2 = "http://localhost:8080/ImagenTest/resources/imagenes/";

    //Constructor
    public storeController() {

    }

    //métodos
    public void openNewProducto() {
        this.selectedProducto = new ProductoTO();
    }

    public void saveTienda() {
        
        this.imgTienda = destinationFile2 + this.file.getFileName(); 
        
        System.out.println("Aqui estas " + this.selectedUsuario.getNombre());
        ServicioTienda user = new ServicioTienda();
        user.insertar(this.selectedUsuario, imgTienda);
        this.listaRetorno = user.lista();
        
        
    }

    public void saveProduct() {
        this.handleFileUpload();
        System.out.println("Aqui esta produto " + this.idProducto + this.selectedProducto.getNombre());
        ServicioProducto prod = new ServicioProducto();
        this.image2 = destinationFile2 + this.file.getFileName();
        
        prod.insertar(this.selectedProducto, this.idProducto, image2);
        ServicioTienda user = new ServicioTienda();
        this.listaRetornoProducts = user.listaProducto(idProducto);
    }
    public void handleFileUpload() {
        try {
            System.out.println("===>>> " + this.file);
            System.out.println("===>>> " + this.file.getFileName() + " size: " + this.file.getSize());
            this.copyFile(this.file.getFileName(), this.file.getInputStream(), false);


            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    protected void copyFile(String fileName, InputStream in, boolean esTemporal) {
        try {
            if (fileName != null) {
                String destinationFile = "C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\ImagenTestweb\\resources\\imagenes\\";
                String image = destinationFile + this.file.getFileName();

                String[] partesArchivo = fileName.split(Pattern.quote("."));
                String nombreArchivo = partesArchivo[0];
                String extensionArchivo = partesArchivo[1];
                if (esTemporal) {
                    nombreArchivo += "_TMP";
                }
                //File tmp = new File(destinationFile + fileName);
                File tmp = new File(destinationFile + nombreArchivo + "." + extensionArchivo);
                tmp.getParentFile().mkdirs();
                OutputStream out = new FileOutputStream(tmp);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                in.close();
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
  

    public void deleteProducto() {
        ServicioProducto servicioProducto = new ServicioProducto();
        servicioProducto.eliminar(this.selectedProducto);
        listaRetornoProducts.remove(selectedProducto);
    }

    public List<ComprasTO> comprasCliente(String correo) {

        seguimientoCliente = compra.facturasSeguimiento(correo);

        return seguimientoCliente;

    }

    public void openNewPage(TiendaTO tienda) {
        ServicioTienda ser = new ServicioTienda();
        this.listaRetornoProducts = ser.listaProducto(tienda.getIdl());
        //this.listaCarrito = new ArrayList<ProductoTO>();
        this.redireccionar("/faces/tienda.xhtml");
        this.idProducto = tienda.getIdl();
        System.out.println("ID tienda " + this.idProducto);
        detalles();

    }

    public List<DetalleCompraTO> verFacturasEspecificas() {

        verFacturasEspe = sdc.verProductosVendidos(idProducto);

        return verFacturasEspe;

    }

    public void openNewPageCliente(TiendaTO tienda) {
        ServicioTienda ser = new ServicioTienda();
        this.listaRetornoProducts = ser.listaProducto(tienda.getIdl());
        //this.listaCarrito = new ArrayList<ProductoTO>();
        this.redireccionar("/faces/Productos.xhtml");
        this.idProducto = tienda.getIdl();

        System.out.println("ID tienda " + this.idProducto);

    }

    public void allDelete(String nombreTienda){
        
        ServicioProducto servPro = new ServicioProducto();
        ServicioTienda servTi = new ServicioTienda();
        
        int idTienda = servTi.obtenerIdTienda(nombreTienda);
        
        servPro.eliminarProductoTienda(idTienda);
        
        servTi.eliminarTienda(nombreTienda);
    }
    
    public void detalles() {
        ServicioUsuario ser = new ServicioUsuario();

        ultimasCompras = ser.detalles(this.idProducto);

    }

    public void openNewTienda() {
        this.selectedUsuario = new TiendaTO();
    }

    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {
        }
    }

    //Getters and setters
    public List<ProductoTO> getListaRetornoProducts() {
        return listaRetornoProducts;
    }

    public List<ComprasTO> getSeguimientoCliente() {
        return seguimientoCliente;
    }

    public void setSeguimientoCliente(List<ComprasTO> seguimientoCliente) {
        this.seguimientoCliente = seguimientoCliente;
    }

    public List<DetalleCompraTO> getVerFacturasEspe() {
        return verFacturasEspe;
    }

    public void setVerFacturasEspe(List<DetalleCompraTO> verFacturasEspe) {
        this.verFacturasEspe = verFacturasEspe;
    }

    public void setListaRetornoProducts(List<ProductoTO> listaRetornoProducts) {
        this.listaRetornoProducts = listaRetornoProducts;
    }

    public ProductoTO getSelectedProducto() {
        return selectedProducto;
    }

    public void setSelectedProducto(ProductoTO selectedProducto) {
        this.selectedProducto = selectedProducto;
    }

    public List<TiendaTO> getListaRetorno() {
        return listaRetorno;
    }

    public void setListaRetorno(List<TiendaTO> listaRetorno) {
        this.listaRetorno = listaRetorno;
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

    public String getPageTitle() {
        System.out.println(" Titulo " + this.getNombre());
        return "My Web App - " + this.selectedUsuario.getNombre();
    }

    public Set<ComprasTO> getUltimasCompras() {
        return ultimasCompras;
    }

    public void setUltimasCompras(Set<ComprasTO> ultimasCompras) {
        this.ultimasCompras = ultimasCompras;
    }

    public double execute() {

        ServicioCompras compras = new ServicioCompras();

        double total = compras.ventasGlobal();
        return total;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getDestinationFile2() {
        return destinationFile2;
    }

    public void setDestinationFile2(String destinationFile2) {
        this.destinationFile2 = destinationFile2;
    }

    public String getImgTienda() {
        return imgTienda;
    }

    public void setImgTienda(String imgTienda) {
        this.imgTienda = imgTienda;
    }
    
    
}
