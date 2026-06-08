package com.vanitystore.controller;

import com.vanitystore.model.Prenda;
import com.vanitystore.service.PrendaService;
import com.vanitystore.view.FrmGestionPrenda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class GestionPrendaController implements ActionListener {
    private FrmGestionPrenda vista;
    private PrendaService service;

    public GestionPrendaController(FrmGestionPrenda vista) {
        this.vista = vista;
        this.service = new PrendaService();
        this.vista.btnGuardar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            guardarNuevaPrenda();
        }
    }

    private void guardarNuevaPrenda() {
        try {
            // 1. Mapeo a objeto
            Prenda p = new Prenda();
            p.setNombrePrenda(vista.txtNombre.getText().trim());
            p.setMarca(vista.txtMarca.getText().trim());
            p.setCategoria(vista.txtCategoria.getText().trim());
            p.setTalla(vista.txtTalla.getText().trim());
            p.setColor(vista.txtColor.getText().trim());
            
            // 2. Parseo de números
            p.setPrecioUnitario(Double.parseDouble(vista.txtPrecio.getText().trim()));
            p.setCantidadInicial(Integer.parseInt(vista.txtStock.getText().trim()));

            // 3. Llamada al servicio (donde Guava realiza las validaciones)
            if (service.guardarPrenda(p)) {
                JOptionPane.showMessageDialog(vista, "¡Prenda registrada correctamente!");
                vista.dispose(); 
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException | NullPointerException e) {
            // Atrapa errores de validación (Guava)
            JOptionPane.showMessageDialog(vista, "Validación: " + e.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            // Atrapa cualquier otro error, incluyendo errores de formato numérico (NumberFormatException)
            JOptionPane.showMessageDialog(vista, "Error en los datos o en el sistema: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}