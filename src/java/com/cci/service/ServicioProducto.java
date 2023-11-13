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
public class ServicioProducto extends Servicio {

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

    public void insertar(ProductoTO productoTO, int tienda) {

        try {

            if (productoExists(productoTO.getId())) {
                PreparedStatement stmt = super.getConexion().prepareStatement("UPDATE productos SET nombre=? , descripcion=?,precio = ? where idproductos=?");

                stmt.setString(1, productoTO.getNombre());
                stmt.setString(2, productoTO.getDescripcion());
                stmt.setInt(3, productoTO.getPrecio());
                stmt.setInt(4, productoTO.getId());
                stmt.execute();

                stmt.close();

            } else {

                PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO productos(nombre, descripcion, precio,tienda) VALUES (?,?,?,?)");

                stmt.setString(1, productoTO.getNombre());
                stmt.setString(2, productoTO.getDescripcion());
                stmt.setInt(3, productoTO.getPrecio());
                stmt.setInt(4, tienda);
                stmt.execute();

                stmt.close();

            }

        } catch (SQLException ex) {
            System.out.println("Error al insertar Producto: " + ex.getMessage());
        }

    }

    public boolean productoExists(int Tienda) {
        try {
            PreparedStatement checkStmt = super.getConexion().prepareStatement("SELECT COUNT(*) FROM productos WHERE idproductos = ?");
            checkStmt.setInt(1, Tienda);

            ResultSet result = checkStmt.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                return count > 0; // If count > 0, the user already exists
            }
        } catch (SQLException ex) {
            System.out.println("Error checking if producto exists: " + ex.getMessage());
        }
        return false;
    }

    public void eliminar(ProductoTO productoTO) {
        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("DELETE FROM productos WHERE nombre = ?");
            stmt.setString(1, productoTO.getNombre());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar usuario: " + ex.getMessage());
        }

    }

}
