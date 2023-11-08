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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
@ManagedBean(name = "loginController")
@SessionScoped

public class LoginController {
   
    private String nombre;
    private String apellido;
    private int telefono;
    private String correo;
    private String clave;
    private String permiso;
    
    private UsuarioTO selectedUsuario;

    public LoginController() {
    }

    public void openNew() {
        this.selectedUsuario = new UsuarioTO();
    }

    public void ingresar(){
        
        ServicioUsuario s = new ServicioUsuario();
        
        if(true == s.Ver(this.getCorreo(), this.getClave())){
        
        this.redireccionar("/faces/index.xhtml");
            
            
        }else{
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos Invalidos", "La clave o correo no son correctos"));
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
    
    public void saveUser() {
        
        ServicioUsuario u = new ServicioUsuario();
         u.AgregarUsuario(selectedUsuario);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario agregado"));    
    }
    

    public LoginController(String nombre, String apellido, int telefono, String correo, String clave, String permiso, UsuarioTO selectedUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.clave = clave;
        this.permiso = permiso;
        this.selectedUsuario = selectedUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
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

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public UsuarioTO getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(UsuarioTO selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }
    
    
}



  
