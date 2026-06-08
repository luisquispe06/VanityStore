package com.vanitystore.model;

public class Stock {
    // Atributos combinados del flujo lógico (Detalle + Inventario)
    private String talla;
    private String color;
    private double precioUnitario;
    private int cantidadDisponible;

    // Constructor vacío
    public Stock() {
    }

    // Constructor con parámetros
    public Stock(String talla, String color, double precioUnitario, int cantidadDisponible) {
        this.talla = talla;
        this.color = color;
        this.precioUnitario = precioUnitario;
        this.cantidadDisponible = cantidadDisponible;
    }

    // Getters y Setters
    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}