package com.vanitystore.model;

public class Usuario {
    private int usuarioID;
    private String nombreCompleto;
    private String rol;

    // Constructor vacío (caja vacía)
    public Usuario() {
    }

    // Constructor con datos (caja llena)
    public Usuario(int usuarioID, String nombreCompleto, String rol) {
        this.usuarioID = usuarioID;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    // Métodos para leer y escribir en las características
    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}