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
 * @author Jose
 */
public class ServicioTienda extends Servicio {

    //Inserta una tienda
    public void insertar(TiendaTO userTO) {
        try {
            if (tiendaExists(userTO.getNombre())) {
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar usuario", "Hubo un error al insertar el usuario"));
                System.out.println(userTO.getNombre());
                PreparedStatement stmt = super.getConexion().prepareStatement("Update tienda SET descripcion = ?, categoria = ?  WHERE nombre = ?");
                //stmt.setInt(1, userTO.getId());

                stmt.setString(1, userTO.getDescripcion());
                stmt.setString(2, userTO.getCategoria());
                stmt.setString(3, userTO.getNombre());
                stmt.execute();

                stmt.close();
                return;
            } else {
                System.out.println(userTO.getNombre());
                if ("".equals(userTO.getNombre()) || "".equals(userTO.getDescripcion()) || "".equals(userTO.getCategoria())) {
                    // Your code here
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Campos Vacios"));
                } 
                else {
                    PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO tienda (nombre, descripcion,categoria) VALUES (?,?,?)");
                    //stmt.setInt(1, userTO.getId());

                    stmt.setString(1, userTO.getNombre());
                    stmt.setString(2, userTO.getDescripcion());
                    stmt.setString(3, userTO.getCategoria());
                    stmt.execute();

                    stmt.close();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tienda Agregada", "La tienda ha sido agregada con éxito."));
                }

            }

            //super.getConexion().close();
        } catch (SQLException ex) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar Tienda", "Hubo un error al insertar la tienda: " + ex.getMessage()));
        }
    }

    //Revisa si ya hay una tienda existente por el nombre
    public boolean tiendaExists(String nombreTienda) {
        try {
            PreparedStatement checkStmt = super.getConexion().prepareStatement("SELECT COUNT(*) FROM tienda WHERE nombre = ?");
            checkStmt.setString(1, nombreTienda);

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

    //Elimina una tienda
    public void eliminarTienda(String nombreTienda) {

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("DELETE FROM tienda WHERE nombre = ?");
            stmt.setString(1, nombreTienda);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar usuario: " + ex.getMessage());
        }

    }

    //Muestra la lista de las tiendas y de una vez divide los productos en las respectivas tiendas
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
                PreparedStatement stmt1 = super.getConexion().prepareStatement("SELECT nombre, descripcion, precio,tienda,cantidad, url FROM avs.productos WHERE tienda = ?;");
                stmt1.setInt(1, tiendaTO.getIdl());
                System.out.println("ID: " + tiendaTO.getIdl());
                ResultSet rs1 = stmt1.executeQuery();

                List<ProductoTO> listaProducto = new ArrayList<ProductoTO>();

                while (rs1.next()) {
                    String nombreProducto = rs1.getString("nombre");
                    String descripProducto = rs1.getString("descripcion");
                    int precio = rs1.getInt("precio");
                    int id = rs1.getInt("tienda");
                    int cantidad = rs1.getInt("cantidad");
                    String  imagen = rs1.getString("url");

                    ProductoTO prod = new ProductoTO(nombreProducto,descripProducto,precio,cantidad, imagen);
                    listaProducto.add(prod);

                    //System.out.println("Added Producto: " + prod.getNombre());
                }
                tiendaTO.setListaProductos(listaProducto);

                listaRetorno.add(tiendaTO);
                for (TiendaTO x : listaRetorno) {
                    System.out.println(x.getNombre());

                    for (ProductoTO y : x.getListaProductos()) {
                        System.out.println(y.getNombre());
                    }
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

    //MUestra la lista de los productos.
   public List<ProductoTO> listaProducto(int id) {
        List<ProductoTO> listaRetorno = new ArrayList<ProductoTO>();

        try {
            System.out.println("Creating statement for Productos...");
            PreparedStatement stmt1 = super.getConexion().prepareStatement("SELECT nombre, descripcion, precio, cantidad, url FROM avs.productos WHERE tienda = ?;");
            stmt1.setInt(1, id);
            System.out.println("ID: " + id);
            ResultSet rs1 = stmt1.executeQuery();

          
            while (rs1.next()) {
                String nombreProducto = rs1.getString("nombre");
                String descripProducto = rs1.getString("descripcion");
                int precio = rs1.getInt("precio");       
                int cantidad = rs1.getInt("cantidad");
                String imagen= rs1.getString("url");
                System.out.println(imagen);
                
                ProductoTO prod = new ProductoTO(nombreProducto, descripProducto, precio,cantidad, imagen);
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
    
    public int obtenerIdTienda(String nomTienda) {
        int idTienda = -1; // Valor por defecto si no se encuentra ningún ID

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT idTienda FROM tienda WHERE nombre = ? LIMIT 1");
            stmt.setString(1, nomTienda);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                idTienda = resultSet.getInt("idtienda");
            }

            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error al obtener el ID de la tienda: " + ex.getMessage());
        }

        return idTienda;
    }
}
