/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lizan
 */
public class ServicioUsuario extends Servicio {
    
    public void AgregarUsuario(UsuarioTO userTO){
        
        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO usuario (nombre, apellido, telefono, correo, contrasena, permiso) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, userTO.getNombre());
            stmt.setString(2, userTO.getApellido());
            stmt.setInt(3, userTO.getTelefono());
            stmt.setString(4, userTO.getCorreo());
            stmt.setString(5, userTO.getContrasena());
            
            stmt.setString(6, "Cliente");
            stmt.execute();

            stmt.close();
            
        } catch (SQLException ex) {
            System.out.println("Error al insertar usuario: " + ex.getMessage());
        }
        
    }
    
    
    public void AgregarAdmin(UsuarioTO userTO){
        
        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO usuario (nombre, apellido, telefono, correo, contrasena, permiso) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, userTO.getNombre());
            stmt.setString(2, userTO.getApellido());
            stmt.setInt(3, userTO.getTelefono());
            stmt.setString(4, userTO.getCorreo());
            stmt.setString(5, userTO.getContrasena());
            
            stmt.setString(6, "Admin");
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
            System.out.println("Error al eliminar usuario: " + ex.getMessage());
        }
    }
     public boolean Ver(String correo, String clave) {
        UsuarioTO c = null;
        
        try{
           PreparedStatement stmt = super.getConexion().prepareStatement("SELECT COUNT(*) FROM usuario WHERE correo = ? and contrasena=? ");
           stmt.setString(1, correo);
           stmt.setString(2, clave);
           ResultSet resultado = stmt.executeQuery();
           if(resultado.next()){
               int count = resultado.getInt(1);
               return count >0;
           }
          
       }catch (SQLException ex){
           System.out.println("El error: " + ex);
       }
       
       return false;
   } 
    
     public boolean VerificarT(String token) {
        
        try{
           PreparedStatement stmt = super.getConexion().prepareStatement("SELECT COUNT(*) FROM token WHERE token = ?");
           stmt.setString(1, token);
           ResultSet resultado = stmt.executeQuery();
           if(resultado.next()){
               int count = resultado.getInt(1);
               return count >0;
           }
          
       }catch (SQLException ex){
           System.out.println("El error: " + ex);
       }
       
       return false;
   } 
    
    public String obtenerPermisoUsuario(String correo) {
    try {
        PreparedStatement stmt = super.getConexion().prepareStatement("SELECT permiso FROM usuario WHERE correo = ?");
        stmt.setString(1, correo);
        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            return resultado.getString("permiso");
        }

        stmt.close();
    } catch (SQLException ex) {
        System.out.println("Error al obtener permiso del usuario: " + ex.getMessage());
    }

    return null;
}
    
}
     
     
     