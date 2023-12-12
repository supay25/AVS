/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author ADMIN
 */

public class ServicioDetalleCompra extends Servicio {
    
     public void Insertar(int idCompra, String nombreP, int cantidad, int idTiend) {

        try {
            
                PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO detalles (idcompra, nombreProducto, cantidad, idTiend) VALUES (?,?,?,?)");

                stmt.setInt(1, idCompra);
                stmt.setString(2, nombreP);
                stmt.setInt(3, cantidad);
                stmt.setInt(4, idTiend);
                
                stmt.execute();

                stmt.close();

        } catch (SQLException ex) {
            System.out.println("Error al insertar compra: " + ex.getMessage());
        }
    }
 
     
     
      public int verTiendaRela(int idProducto) {
        int idTienda = -1; // Valor predeterminado si no se encuentra nada

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT tienda FROM productos WHERE idproductos = ?");

            stmt.setInt(1, idProducto);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idTienda = rs.getInt("tienda");
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return idTienda;
    }
     
      public List<DetalleCompraTO> verProductosVendidos(int idTienda) {

        List<DetalleCompraTO> listaRetorno = new ArrayList<DetalleCompraTO>();

        try {
            System.out.println("Creating statement for Productos...");
            PreparedStatement stmt1 = super.getConexion().prepareStatement("SELECT nombreProducto, cantidad FROM avs.detalles WHERE idTiend = ?;");
            stmt1.setInt(1, idTienda);
            ResultSet rs1 = stmt1.executeQuery();

            while (rs1.next()) {
                String nombreProducto = rs1.getString("nombreProducto");
                int  cantidad = rs1.getInt("cantidad");
                

                DetalleCompraTO dt = new DetalleCompraTO();
                dt.setNombreProducto(nombreProducto);
                dt.setCantidad(cantidad);
               
                listaRetorno.add(dt);

               

            }
            // Close the Producto-related resources
            rs1.close();
            stmt1.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaRetorno;
    }
      
      public int verIDDetalle(String nombre) {
        int idCompras = -1; // Valor predeterminado si no se encuentra nada

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT idDetalles FROM detalles WHERE nombreProducto = ?");

            stmt.setString(1, nombre);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idCompras = rs.getInt("idDetalles");
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return idCompras;
    }
    public int verIDTienda(int idProducto) {
        int idTienda = -1; // Valor predeterminado si no se encuentra nada

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT idTiend FROM detalles WHERE idDetalles = ?");

            stmt.setInt(1, idProducto);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idTienda = rs.getInt("idTiend");
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return idTienda;
    }
    
     public List<ComprasTO> VerTodasLasCompras() { 
         List <ComprasTO> RetornarLista = new ArrayList<ComprasTO>();

        try {
            
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT * FROM compras");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String codigoCompra = rs.getString("codigoCompra");
                String nomTarjeta = rs.getString("nomTarjeta");
                String correo = rs.getString("correo");
                String MetodoPago = rs.getString("MetodoPago");
                double total = rs.getDouble("total");

                ComprasTO verCompras = new ComprasTO();
                verCompras.setCodigoCompra(codigoCompra);
                verCompras.setNomTarjeta(nomTarjeta);
                verCompras.setCorreo(correo);
                verCompras.setMetodoPago(MetodoPago);
                verCompras.setTotal(total);
                RetornarLista.add(verCompras);

            }
            
            rs.close();
            stmt.close();

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());    
            
        }
        return RetornarLista;
    }
      
}
