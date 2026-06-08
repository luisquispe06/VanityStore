package com.vanitystore.service;

import com.vanitystore.model.Prenda;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelExportService {
    
    private static final Logger logger = LoggerFactory.getLogger(ExcelExportService.class);

    // Método para exportar la lista de prendas
    public void exportarPrendasAExcel(List<Prenda> listaPrendas, String nombreArchivo) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Inventario");
            
            // Encabezados
            Row headerRow = sheet.createRow(0);
            String[] columnas = {"ID", "Nombre", "Marca", "Categoría"};
            for (int i = 0; i < columnas.length; i++) {
                headerRow.createCell(i).setCellValue(columnas[i]);
            }

            // Datos
            int rowNum = 1;
            for (Prenda p : listaPrendas) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getPrendaID());
                row.createCell(1).setCellValue(p.getNombrePrenda());
                row.createCell(2).setCellValue(p.getMarca());
                row.createCell(3).setCellValue(p.getCategoria());
            }

            try (FileOutputStream fileOut = new FileOutputStream(nombreArchivo)) {
                workbook.write(fileOut);
                logger.info("Excel de inventario generado: {}", nombreArchivo);
            }
        } catch (IOException e) {
            logger.error("Error al exportar inventario: {}", e.getMessage());
        }
    }

    // Método para exportar el historial de consultas (el que usarás en el Panel Admin)
    public void exportarHistorialAExcel(List<Object[]> datos, String nombreArchivo) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Historial Consultas");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            String[] columnas = {"ID Consulta", "Vendedor", "Prenda", "Fecha"};
            for (int i = 0; i < columnas.length; i++) {
                headerRow.createCell(i).setCellValue(columnas[i]);
            }

            // Datos
            int rowNum = 1;
            for (Object[] fila : datos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(fila[0].toString());
                row.createCell(1).setCellValue(fila[1].toString());
                row.createCell(2).setCellValue(fila[2].toString());
                row.createCell(3).setCellValue(fila[3].toString());
            }

            try (FileOutputStream fileOut = new FileOutputStream(nombreArchivo)) {
                workbook.write(fileOut);
                logger.info("Excel de historial generado: {}", nombreArchivo);
            }
        } catch (IOException e) {
            logger.error("Error al exportar historial: {}", e.getMessage());
        }
    }
}