package com.vanitystore.service;

import com.vanitystore.model.Prenda;
import com.vanitystore.model.PrendaDAO;

public class PrendaService {
    private PrendaDAO dao = new PrendaDAO();

    public boolean guardarPrenda(Prenda p) {
        // Validaciones personalizadas
        if (p == null) {
            throw new IllegalArgumentException("La prenda no puede ser nula.");
        }
        if (p.getNombrePrenda() == null || p.getNombrePrenda().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la prenda es obligatorio.");
        }
        if (p.getPrecioUnitario() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (p.getCantidadInicial() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        
        // Si todo es correcto, guardamos
        return dao.agregarPrendaCompleta(p);
    }
}