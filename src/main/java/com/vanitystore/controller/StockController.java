package com.vanitystore.controller;

import com.vanitystore.model.Stock;
import com.vanitystore.model.StockDAO;
import com.vanitystore.view.FrmStock;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class StockController {

    private FrmStock vista;
    private StockDAO stockDao;

    public StockController(FrmStock vista, StockDAO stockDao) {
        this.vista = vista;
        this.stockDao = stockDao;
    }

    /**
     * Carga el stock de una prenda específica en la tabla de la interfaz gráfica
     * @param prendaID Código de la prenda consultada
     */
    public void mostrarStockPrenda(int prendaID) {
        // 1. Obtener la lista de variantes desde la base de datos usando el DAO
        List<Stock> lista = stockDao.consultarInventarioPorPrenda(prendaID);

        // 2. Obtener el modelo por defecto de la JTable para manipular las filas
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tblStock.getModel();
        
        // 3. Limpiar cualquier fila antigua que haya quedado en la tabla
        modeloTabla.setRowCount(0);

        // 4. Llenar la tabla fila por fila con los datos reales de MySQL
        for (Stock s : lista) {
            Object[] fila = new Object[4];
            fila[0] = s.getTalla();
            fila[1] = s.getColor();
            fila[2] = String.format("S/. %.2f", s.getPrecioUnitario()); // Formato de moneda peruana
            fila[3] = s.getCantidadDisponible();

            modeloTabla.addRow(fila);
        }
        
        // === ESPACIO DE RESERVA PARA EL FLUJO LÓGICO DE AUDITORÍA ===
        // Aquí conectaremos próximamente el ConsultaDAO para hacer el INSERT automático 
        // en la tabla 'consulta' (usuarioID, prendaID, fecha_consulta).
        // System.out.println("Registro de auditoría guardado en la tabla consulta.");
        // ============================================================
    }
}