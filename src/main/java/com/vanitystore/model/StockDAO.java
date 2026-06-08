package com.vanitystore.model;

import com.vanitystore.config.Conexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    // Logger para auditoría
    private static final Logger logger = LoggerFactory.getLogger(StockDAO.class);

    public List<Stock> consultarInventarioPorPrenda(int prendaID) {
        List<Stock> listaStock = new ArrayList<>();
        String sql = "SELECT dp.talla, dp.color, dp.precio_unitario, s.cantidad_disponible " +
                     "FROM detalle_prenda dp " +
                     "INNER JOIN stock s ON dp.detallePrendaID = s.detallePrendaID " +
                     "WHERE dp.prendaID = ?";
        
        // Uso de try-with-resources para cierre automático y seguridad
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, prendaID);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Stock stockItem = new Stock();
                    stockItem.setTalla(rs.getString("talla"));
                    stockItem.setColor(rs.getString("color"));
                    stockItem.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    stockItem.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                    
                    listaStock.add(stockItem);
                }
            }
        } catch (SQLException e) {
            // Registro profesional del error
            logger.error("Error al consultar el inventario para la prenda ID {}: {}", prendaID, e.getMessage());
        }
        return listaStock;
    }

    public int obtenerCantidadTotalPorPrenda(int prendaID) {
        String sql = "SELECT SUM(cantidad_disponible) AS total FROM stock s " +
                     "INNER JOIN detalle_prenda dp ON s.detallePrendaID = dp.detallePrendaID " +
                     "WHERE dp.prendaID = ?";
        
        int total = 0;
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, prendaID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            logger.error("Error al sumar stock para prenda ID {}: {}", prendaID, e.getMessage());
        }
        return total;
    }
}