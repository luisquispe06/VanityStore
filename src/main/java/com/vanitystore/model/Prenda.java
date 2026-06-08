package com.vanitystore.model;

public class Prenda {
    private int prendaID;
    private String nombrePrenda;
    private String marca;
    private String categoria;
    
    // Nuevos campos para gestionar las variantes al crear la prenda
    private String talla;
    private String color;
    private double precioUnitario;
    private int cantidadInicial;

    public Prenda() {
    }

    // Getters y Setters
    public int getPrendaID() { return prendaID; }
    public void setPrendaID(int prendaID) { this.prendaID = prendaID; }

    public String getNombrePrenda() { return nombrePrenda; }
    public void setNombrePrenda(String nombrePrenda) { this.nombrePrenda = nombrePrenda; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    // Getters y Setters para las nuevas variantes
    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public int getCantidadInicial() { return cantidadInicial; }
    public void setCantidadInicial(int cantidadInicial) { this.cantidadInicial = cantidadInicial; }
}