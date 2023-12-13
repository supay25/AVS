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
 * @author Jose
 */
public class ServicioProducto extends Servicio {

    public ServicioProducto() {

    }

    //Inserta los productos en la tienda correspondiente.
    public void insertar(ProductoTO productoTO, int tienda, String imagen) {

        try {

            if (productoExists(productoTO.getNombre())) {
                PreparedStatement stmt = super.getConexion().prepareStatement("UPDATE productos SET descripcion=? , precio=?, cantidad = ?, url=? where nombre=?");

                stmt.setString(1, productoTO.getDescripcion());
                stmt.setInt(2, productoTO.getPrecio());
                stmt.setInt(3, productoTO.getCantidad());
                stmt.setString(4, productoTO.getNombre());
                stmt.setString(5, imagen);
                stmt.execute();

                stmt.close();

            } else {

                PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO productos(nombre, descripcion, precio,tienda,cantidad, url) VALUES (?,?,?,?,?,?)");

                stmt.setString(1, productoTO.getNombre());
                stmt.setString(2, productoTO.getDescripcion());
                stmt.setInt(3, productoTO.getPrecio());
                stmt.setInt(4, tienda);
                stmt.setInt(5, productoTO.getCantidad());
                stmt.setString(6, imagen);
                stmt.execute();

                stmt.close();

            }

        } catch (SQLException ex) {
            System.out.println("Error al insertar Producto: " + ex.getMessage());
        }

    }

    //Muestra si hay un producto existente por el nombre
    public boolean productoExists(String nombre) {
        try {
            PreparedStatement checkStmt = super.getConexion().prepareStatement("SELECT COUNT(*) FROM productos WHERE nombre = ?");
            checkStmt.setString(1, nombre);

            ResultSet result = checkStmt.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                return count > 0; // If count > 0, the user already exists
            }
        } catch (SQLException ex) {
            System.out.println("Error checking if user exists: " + ex.getMessage());
        }
        return false;
    }

    //Elimina un producto por el nombre
    public void eliminar(ProductoTO productoTO) {
        try {
            if (("").equals(productoTO.getNombre()) || (("").equals(productoTO.getDescripcion())) || productoTO.getPrecio() == 0) {
                // Your code here
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Campos Vacios"));

            } else {
                PreparedStatement stmt = super.getConexion().prepareStatement("DELETE FROM productos WHERE nombre = ?");
                stmt.setString(1, productoTO.getNombre());
                stmt.executeUpdate();
                stmt.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Eliminado", "El producto ha sido eliminado con éxito."));

            }
            ;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar producto: ");
        }

    }

    //Elimina todos los productos de una tienda.
    public void eliminarProductoTienda(int idTienda) {
        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("DELETE FROM productos WHERE tienda = ?");
            stmt.setInt(1, idTienda);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar producto: " + ex.getMessage());
        }

    }

    //Obtiene el id de los producos por medio del nombre
    public int obtenerIdProducto(ProductoTO productoTO) {
        int idProducto = -1; // Valor por defecto si no se encuentra ningún ID

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT idproductos FROM productos WHERE nombre = ? LIMIT 1");
            stmt.setString(1, productoTO.getNombre());
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                idProducto = resultSet.getInt("idproductos");
            }

            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error al obtener el ID del producto: " + ex.getMessage());
        }

        return idProducto;
    }
}
