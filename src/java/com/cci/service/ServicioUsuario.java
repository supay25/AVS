/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author lizan
 */
public class ServicioUsuario extends Servicio {
    
    public void AgregarUsuario(UsuarioTO userTO){
        
        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO avs(nombre, apellido, telefono, correo, contrasena, permiso) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, userTO.getNombre());
            stmt.setString(2, userTO.getApellido());
            stmt.setInt(3, userTO.getTelefono());
            stmt.setString(4, userTO.getCorreo());
            stmt.setString(5, userTO.getContrasena());
            stmt.setString(6, userTO.getPermiso());
            stmt.execute();

            stmt.close();
            
        } catch (SQLException ex) {
            System.out.println("Error al insertar usuario: " + ex.getMessage());
        }
        
    }
    
    public void eliminar(int id) {

        try {
            //super.conectarBBDD();
            PreparedStatement stmt = super.getConexion().prepareStatement("DELETE FROM avs WHERE ID=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();

            stmt.close();
            //super.getConexion().close();

        } catch (SQLException ex) {
            System.out.println("Error al eliminar ususario: " + ex.getMessage());
        }
    }
    
    
}
