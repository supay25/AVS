package com.cci.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author lizan
 */
public class ServicioCompras extends Servicio {

    // Es para insertar
    public void Insertar(ComprasTO comprasTO) {

        try {
            if (existente(comprasTO.getCorreo())) {
                PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO compras (direccion, metodoPago, correo, provincia, codigoPostal, nomTarjeta, numTarjeta, cvv, total) VALUES (?,?,?,?,?,?,?,?,?)");

                stmt.setString(1, comprasTO.getDireccion());
                stmt.setString(2, comprasTO.getMetodoPago());
                stmt.setString(3, comprasTO.getCorreo());
                stmt.setString(4, comprasTO.getProvincia());
                stmt.setInt(5, comprasTO.getCodPostal());
                stmt.setString(6, comprasTO.getNomTarjeta());
                stmt.setInt(7, comprasTO.getNumTarjeta());
                stmt.setInt(8, comprasTO.getCvv());
                stmt.setDouble(9, comprasTO.getTotal());
               
                stmt.execute();

                stmt.close();

            } else {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo no encontrado", "Use correo afilidado a AVS"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al insertar compra: " + ex.getMessage());
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

    private boolean existente(String correo) {

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT COUNT(*) FROM usuario WHERE correo = ?");
            stmt.setString(1, correo);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                int count = resultado.getInt(1);
                return count > 0;
            }

        } catch (SQLException ex) {
            System.out.println("la adriana cachera" + ex);
        }

        return false;
    }

}//Fin

