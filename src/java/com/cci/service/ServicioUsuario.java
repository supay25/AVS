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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author lizan
 */
public class ServicioUsuario extends Servicio {

    //Registra un nuevo usuario y le da el rol de cliente
    public void AgregarUsuario(UsuarioTO userTO) {
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

    //Agrega un administrador y le da el rol de admin
    public void AgregarAdmin(UsuarioTO userTO) {

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

    //Actualiza la información del cliente
    public void actualizar(UsuarioTO userTO) {

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("UPDATE usuario SET nombre=? , apellido=?, telefono = ?, Correo=? where id=?");

            stmt.setString(1, userTO.getNombre());
            stmt.setString(2, userTO.getApellido());
            stmt.setInt(3, userTO.getTelefono());
            stmt.setString(4, userTO.getCorreo());
            stmt.setInt(5, userTO.getId());
            stmt.execute();

            stmt.close();
        } catch (SQLException ex) {
            System.out.println("El error: " + ex);
        }
    }

    //Elimina un usuario
    public void eliminar(int id) {
        try {

            PreparedStatement stmt = super.getConexion().prepareStatement("DELETE FROM avs WHERE ID=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();

            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Error al eliminar usuario: " + ex.getMessage());
        }
    }

    //Busca en la base de datos el correo y la clave para verificación.
    public boolean Ver(String correo, String clave) {
        UsuarioTO c = null;

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT COUNT(*) FROM usuario WHERE correo = ? and contrasena=? ");
            stmt.setString(1, correo);
            stmt.setString(2, clave);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                int count = resultado.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            System.out.println("El error: " + ex);
        }

        return false;
    }

    //Verifica el token para que el administrador se pueda registrar
    public boolean VerificarT(String token) {

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT COUNT(*) FROM token WHERE token = ?");
            stmt.setString(1, token);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                int count = resultado.getInt(1);
                return count > 0;
            }

        } catch (SQLException ex) {
            System.out.println("El error: " + ex);
        }

        return false;
    }

    //Hace una lista con el detalle de compra del usuario.
    public Set<ComprasTO> detalles(int id) {

        Set<ComprasTO> perfil = new HashSet<>();

        try {
            System.out.println("Creating statement for Productos...");
            PreparedStatement stmt1 = super.getConexion().prepareStatement("SELECT idCompra FROM avs.detalles WHERE idTiend = ?;");
            stmt1.setInt(1, id);

            System.out.println("Id Tienda: " + id);
            ResultSet rs1 = stmt1.executeQuery();

            while (rs1.next()) {
                int idCompra = rs1.getInt("idCompra");

                System.out.println("Creating statement for Productos...");
                PreparedStatement stmt2 = super.getConexion().prepareStatement("SELECT correo, provincia,codigoCompra,total FROM avs.compras WHERE idCompras = ?;");
                stmt2.setInt(1, idCompra);
                System.out.println("ID: " + idCompra);
                ResultSet rs2 = stmt2.executeQuery();

                while (rs2.next()) {
                    String correo = rs2.getString("correo");
                    String provincia = rs2.getString("provincia");
                    int total = rs2.getInt("total");
                    String codigoCompra = rs2.getString("codigoCompra");
                    ComprasTO prod = new ComprasTO();
                    prod.setCorreo(correo);
                    prod.setTotal(total);
                    prod.setCodigoCompra(codigoCompra);
                    prod.setProvincia(provincia);

                    perfil.add(prod);

                    System.out.println("Added Test: " + prod.getCorreo());
                }
                rs2.close();
                stmt2.close();
            }
            rs1.close();
            stmt1.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return perfil;
    }

    //Verifica si el usuario es un cliente o un administrador.
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

    // Da una lista de usuarios.
    public List<UsuarioTO> demeUsuario(String correo) {

        List<UsuarioTO> usuarioRetorno = new ArrayList<UsuarioTO>();

        try {

            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT id, nombre, apellido, telefono, correo, contrasena, permiso FROM usuario WHERE correo = ?");
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                // query que retorne la lista de la tarea
                int id = rs.getInt("id");

                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int telefono = rs.getInt("telefono");
                String email = rs.getString("correo");
                String contraseña = rs.getString("contrasena");
                String permiso = rs.getString("permiso");

                UsuarioTO userTO = new UsuarioTO(id, nombre, apellido, telefono, email, contraseña, permiso);

                userTO.setId(id);
                userTO.setNombre(nombre);
                userTO.setApellido(apellido);
                userTO.setTelefono(telefono);
                userTO.setCorreo(correo);
                userTO.setContrasena(contraseña);
                userTO.setPermiso(permiso);
                usuarioRetorno.add(userTO);

            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error al abrir Conexión: " + ex.getMessage());
            ex.printStackTrace();
        }
        return usuarioRetorno;
    }

}
