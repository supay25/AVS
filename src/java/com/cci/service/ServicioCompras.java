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
 * @author lizan
 */
public class ServicioCompras extends Servicio {

    // Es para insertar
    public void Insertar(ComprasTO comprasTO) {

        try {
            if (existente(comprasTO.getCorreo())) {
                PreparedStatement stmt = super.getConexion().prepareStatement("INSERT INTO compras (direccion, metodoPago, correo, provincia, codigoPostal, nomTarjeta, numTarjeta, cvv, total, codigoCompra) VALUES (?,?,?,?,?,?,?,?,?,?)");

                stmt.setString(1, comprasTO.getDireccion());
                stmt.setString(2, comprasTO.getMetodoPago());
                stmt.setString(3, comprasTO.getCorreo());
                stmt.setString(4, comprasTO.getProvincia());
                stmt.setInt(5, comprasTO.getCodPostal());
                stmt.setString(6, comprasTO.getNomTarjeta());
                stmt.setInt(7, comprasTO.getNumTarjeta());
                stmt.setInt(8, comprasTO.getCvv());
                stmt.setDouble(9, comprasTO.getTotal());
                stmt.setString(10, comprasTO.getCodigoCompra());
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

    public List<ComprasTO> ventasTotales() {

        List<ComprasTO> listaRetorno = new ArrayList<ComprasTO>();

        try {
            System.out.println("Creating statement for Productos...");
            PreparedStatement stmt1 = super.getConexion().prepareStatement("SELECT correo, total, nomTarjeta,provincia FROM avs.compras;");
            ResultSet rs1 = stmt1.executeQuery();

            while (rs1.next()) {
                String correo = rs1.getString("correo");
                double total = rs1.getDouble("total");
                String nomTarjeta = rs1.getString("nomTarjeta");
                String provincia = rs1.getString("provincia");

                ComprasTO compra = new ComprasTO();
                compra.setCorreo(correo);
                compra.setTotal(total);
                compra.setProvincia(provincia);
                compra.setNomTarjeta(nomTarjeta);
                listaRetorno.add(compra);

                System.out.println("Added Producto: " + compra.getProvincia());

            }
            // Close the Producto-related resources
            rs1.close();
            stmt1.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaRetorno;
    }

    public double ventasGlobal() {
        double ventasTotales = 0.0;

        try {
            System.out.println("Creating statement for Productos...");
            PreparedStatement stmt1 = super.getConexion().prepareStatement("SELECT  total FROM avs.compras;");
            ResultSet rs1 = stmt1.executeQuery();

            while (rs1.next()) {
                ventasTotales += rs1.getDouble("total");

                System.out.println("Added Producto: " + ventasTotales);

            }
            // Close the Producto-related resources
            rs1.close();
            stmt1.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ventasTotales;
    }

    public int verID(String codigo) {
        int idCompras = -1; // Valor predeterminado si no se encuentra nada

        try {
            PreparedStatement stmt = super.getConexion().prepareStatement("SELECT idCompras FROM compras WHERE codigoCompra = ?");

            stmt.setString(1, codigo);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idCompras = rs.getInt("idCompras");
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return idCompras;
    }

}//Fin

