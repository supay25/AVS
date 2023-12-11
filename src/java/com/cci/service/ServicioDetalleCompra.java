/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     
      
}
