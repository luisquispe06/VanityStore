package com.vanitystore.model;

import java.sql.Timestamp;

public class Consulta {
    private int idConsulta;
    private int usuarioID;
    private int prendaID;
    private Timestamp momento;

    public Consulta() {}

    public Consulta(int idConsulta, int usuarioID, int prendaID, Timestamp momento) {
        this.idConsulta = idConsulta;
        this.usuarioID = usuarioID;
        this.prendaID = prendaID;
        this.momento = momento;
    }

    // Getters y Setters
    public int getIdConsulta() { return idConsulta; }
    public void setIdConsulta(int idConsulta) { this.idConsulta = idConsulta; }
    public int getUsuarioID() { return usuarioID; }
    public void setUsuarioID(int usuarioID) { this.usuarioID = usuarioID; }
    public int getPrendaID() { return prendaID; }
    public void setPrendaID(int prendaID) { this.prendaID = prendaID; }
    public Timestamp getMomento() { return momento; }
    public void setMomento(Timestamp momento) { this.momento = momento; }
}