
package com.cci.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author lizan
 */
public class ServicioCompras extends Servicio{
    
    // Es para insertar
    public void Insertar (ComprasTO comprasTO){
        
        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO compras (direccion, metodoPago, correo, provincia, codigoPostal, nomTarjeta, numTarjeta, cvv, total) VALUES (?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, comprasTO.getDireccion());
            stmt.setString(2, comprasTO.getMetodoPago());
            stmt.setString(3, comprasTO.getCorreo());
            stmt.setString(4, comprasTO.getProvincia());
            stmt.setInt(5, comprasTO.getCodPostal());
            stmt.setString(6, comprasTO.getNomTarjeta());
            stmt.setInt(7, comprasTO.getNumTarjeta());
            stmt.setInt(8, comprasTO.getCvv());
            stmt.setInt(9, comprasTO.getTotal());
            
            
            stmt.execute();

            stmt.close();
            
        } catch (SQLException ex) {
            System.out.println("Error al insertar usuario: " + ex.getMessage());
        }        
    }
    
    public void eliminar(int id) {

        try {
            //super.conectarBBDD();
            PreparedStatement stmt = super.getConexion().prepareStatement("DELETE FROM compras WHERE ID=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();

            stmt.close();
            //super.getConexion().close();

        } catch (SQLException ex) {
            System.out.println("Error al eliminar usuario: " + ex.getMessage());
        }
    }
    
    
    
}//Fin
