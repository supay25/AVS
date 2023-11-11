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

/**
 *
 * @author Jose
 */
public class ServicioProducto extends Servicio{

    public ServicioProducto() {
        
    }
    
      public List<ProductoTO> lista() {
        List<ProductoTO> listaRetorno = new ArrayList<ProductoTO>();
        
        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT nombre, descripcion, precio FROM productos");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                int precio = rs.getInt("precio");
                
                
               ProductoTO p = new ProductoTO(nombre, descripcion, precio);
               
                p.setNombre(nombre);
                p.setDescripcion(descripcion);
                p.setPrecio(precio);
                listaRetorno.add(p);
            }
            rs.close();
            stmt.close();

        } catch (SQLException ex) {
          
            ex.printStackTrace();
        }

        return listaRetorno;
    }
     
        /*public void insertar(ProductoTO productoTO) {

        try {
            
             if(existente(productoTO.getCodigo())){
           PreparedStatement stmt = super.getConexion().prepareStatement("UPDATE proyecto SET nombre=? , descripcion=? where codigo=?");

            stmt.setString(3, proyectoTO.getCodigo());
            stmt.setString(1, proyectoTO.getNombre());
            stmt.setString(2, proyectoTO.getDescripcion());
            stmt.execute();

            stmt.close();

           }else{

            PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO proyecto(codigo, nombre , descripcion) VALUES (?,?,?)");

            stmt.setString(1, proyectoTO.getCodigo());
            stmt.setString(2, proyectoTO.getNombre());
            stmt.setString(3, proyectoTO.getDescripcion());
            stmt.execute();

            stmt.close();
           
             }

        } catch (SQLException ex) {
            System.out.println("Error al insertar usuario: " + ex.getMessage());
        }

    
        }*/
        
        
        public void eliminar(String nombre) {
        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("DELETE FROM productos WHERE nombre = ?");
            stmt.setString(1, nombre);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar usuario: " + ex.getMessage());
        }

    }

  /* private boolean existente(String ticodigo){

       try{
           
           PreparedStatement stmt = super.getConexion().prepareStatement("SELECT COUNT(*) FROM proyecto WHERE codigo = ?");
           stmt.setString(1, ticodigo);
           ResultSet resultado = stmt.executeQuery();
           if(resultado.next()){
               int count = resultado.getInt(1);
               return count >0;
           }

       }catch (SQLException ex){
           System.out.println("Error al actualizar"+ ex.getMessage());
       }

       return false;
   }*/ 
    
    
}
