/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cci.store.controller;

import com.cci.service.ServicioUsuario;
import com.cci.service.UsuarioTO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


public class LoginController {
    private String correo;
    private String clave;

    private UsuarioTO selectedUsuario;

    public LoginController() {
    }

    public void openNew() {
        this.selectedUsuario = new UsuarioTO();
    }

    public void ingresar() {
        System.out.println("Valor de usuario: " + this.getCorreo());
        System.out.println("Valor de clave: " + this.getClave());

        try {
            
            UsuarioTO userTO = new UsuarioTO();
            if (this.getCorreo().equals(userTO.getCorreo()) && this.getClave().equals(userTO.getContrasena())) {
            this.redireccionar("/faces/bienvenido.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos invalidos", "La clave o correo no son correctos"));

        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {   
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public UsuarioTO getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(UsuarioTO selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }
}
