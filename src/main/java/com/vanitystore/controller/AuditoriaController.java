package com.vanitystore.controller;

import com.vanitystore.model.ConsultaDAO;
import com.vanitystore.view.FrmAuditoria;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class AuditoriaController {
    private FrmAuditoria vista;
    private ConsultaDAO dao;

    public AuditoriaController(FrmAuditoria vista, ConsultaDAO dao) {
        this.vista = vista;
        this.dao = dao;
        cargarTabla();
    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTblAuditoria().getModel();
        model.setRowCount(0); // Limpiar tabla

        // Usamos el método con JOIN que definimos antes
        List<Object[]> historial = dao.obtenerHistorialDetallado();
        
        for (Object[] fila : historial) {
            model.addRow(fila);
        }
    }
}