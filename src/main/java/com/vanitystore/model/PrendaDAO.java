package com.vanitystore.model;

import com.vanitystore.config.Conexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrendaDAO {

    private static final Logger logger = LoggerFactory.getLogger(PrendaDAO.class);

    public List<Prenda> listarPrendas() {
        List<Prenda> lista = new ArrayList<>();
        String sql = "SELECT * FROM prenda";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prenda p = new Prenda();
                p.setPrendaID(rs.getInt("prendaID"));
                p.setNombrePrenda(rs.getString("nombre_prenda"));
                p.setMarca(rs.getString("marca"));
                p.setCategoria(rs.getString("categoria"));
                lista.add(p);
            }
        } catch (SQLException e) {
            logger.error("Error al listar prendas: {}", e.getMessage());
        }
        return lista;
    }

    public List<Prenda> buscarPrenda(String criterio) {
        List<Prenda> lista = new ArrayList<>();
        String sql = "SELECT * FROM prenda WHERE nombre_prenda LIKE ? OR marca LIKE ? OR categoria LIKE ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            String busqueda = "%" + criterio + "%";
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prenda p = new Prenda();
                    p.setPrendaID(rs.getInt("prendaID"));
                    p.setNombrePrenda(rs.getString("nombre_prenda"));
                    p.setMarca(rs.getString("marca"));
                    p.setCategoria(rs.getString("categoria"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar prendas con criterio '{}': {}", criterio, e.getMessage());
        }
        return lista;
    }

    public boolean agregarPrendaCompleta(Prenda p) {
        String sqlPrenda = "INSERT INTO prenda (nombre_prenda, marca, categoria) VALUES (?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_prenda (prendaID, talla, color, precio_unitario) VALUES (?, ?, ?, ?)";
        String sqlStock = "INSERT INTO stock (detallePrendaID, cantidad_disponible) VALUES (?, ?)";

        try (Connection cn = Conexion.getConexion()) {
            cn.setAutoCommit(false); // Transacción iniciada

            try (PreparedStatement psPrenda = cn.prepareStatement(sqlPrenda, Statement.RETURN_GENERATED_KEYS)) {
                psPrenda.setString(1, p.getNombrePrenda());
                psPrenda.setString(2, p.getMarca());
                psPrenda.setString(3, p.getCategoria());
                psPrenda.executeUpdate();

                try (ResultSet rs = psPrenda.getGeneratedKeys()) {
                    if (rs.next()) {
                        int prendaID = rs.getInt(1);

                        try (PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle, Statement.RETURN_GENERATED_KEYS)) {
                            psDetalle.setInt(1, prendaID);
                            psDetalle.setString(2, p.getTalla());
                            psDetalle.setString(3, p.getColor());
                            psDetalle.setDouble(4, p.getPrecioUnitario());
                            psDetalle.executeUpdate();

                            try (ResultSet rsDetalle = psDetalle.getGeneratedKeys()) {
                                if (rsDetalle.next()) {
                                    int detalleID = rsDetalle.getInt(1);
                                    try (PreparedStatement psStock = cn.prepareStatement(sqlStock)) {
                                        psStock.setInt(1, detalleID);
                                        psStock.setInt(2, p.getCantidadInicial());
                                        psStock.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }
                cn.commit();
                return true;
            } catch (SQLException e) {
                cn.rollback();
                logger.error("Error crítico en transacción de agregar prenda: {}", e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error al obtener conexión para agregar prenda: {}", e.getMessage());
            return false;
        }
    }
}