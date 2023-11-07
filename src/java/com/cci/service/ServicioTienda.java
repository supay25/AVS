/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.service;

import com.sun.javafx.runtime.SystemProperties;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jose
 */
public class ServicioTienda extends Servicio {

    public List<TiendaTO> lista() {
        List<TiendaTO> listaRetorno = new ArrayList<TiendaTO>();
        
        try {
            System.out.println("Creating statement for Tiendas...");
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT idTienda, nombre, descripcion, calificacion, categoria FROM avs.tienda;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idTienda = rs.getInt("idTienda");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String calificacion = rs.getString("calificacion");
                String categoria = rs.getString("categoria");

                TiendaTO tiendaTO = new TiendaTO(nombre, descripcion, calificacion, categoria, idTienda);

                System.out.println("Added Tienda: " + tiendaTO.getNombre());

                System.out.println("Creating statement for Productos...");
                PreparedStatement stmt1 = super.getConexion().prepareStatement("SELECT nombre, descripcion, precio,tienda FROM avs.productos WHERE tienda = ?;");
                stmt1.setInt(1, tiendaTO.getIdl());
                System.out.println("ID: " + tiendaTO.getIdl());
                ResultSet rs1 = stmt1.executeQuery();

                List<ProductoTO> listaProducto = new ArrayList<ProductoTO>();

                while (rs1.next()) {
                    String nombreProducto = rs1.getString("nombre");
                    String descripProducto = rs1.getString("descripcion");
                    String precio = rs1.getString("precio");
                    int id = rs1.getInt("tienda");

                    ProductoTO prod = new ProductoTO(nombreProducto, descripProducto, precio, id);
                    listaProducto.add(prod);

                    //System.out.println("Added Producto: " + prod.getNombre());
                }
                tiendaTO.setListaProductos(listaProducto);
                
                listaRetorno.add(tiendaTO);
                for (TiendaTO x : listaRetorno){
                    System.out.println(x.getNombre());
                    
                    for (ProductoTO y : x.getListaProductos())
                    System.out.println(y.getNombre());
                    
                   
                }
                // Close the Producto-related resources
                rs1.close();
                stmt1.close();

            }

            // Close the Tienda-related resources
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaRetorno;
    }

    public List<ProductoTO> listaProducto(int id) {
        List<ProductoTO> listaRetorno = new ArrayList<ProductoTO>();

        try {
            System.out.println("Creating statement for Productos...");
            PreparedStatement stmt1 = super.getConexion().prepareStatement("SELECT nombre, descripcion, precio FROM avs.productos WHERE tienda = ?;");
            stmt1.setInt(1, id);
            System.out.println("ID: " + id);
            ResultSet rs1 = stmt1.executeQuery();

          
            while (rs1.next()) {
                String nombreProducto = rs1.getString("nombre");
                String descripProducto = rs1.getString("descripcion");
                String precio = rs1.getString("precio");               

                ProductoTO prod = new ProductoTO(nombreProducto, descripProducto, precio, id);
                listaRetorno.add(prod);

                System.out.println("Added Producto: " + prod.getNombre());
            }                     
            // Close the Producto-related resources
            rs1.close();
            stmt1.close();
       
          
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaRetorno;
    }
}
