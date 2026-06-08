package com.vanitystore.model;

import com.vanitystore.config.Conexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Importante añadir esto
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    // 1. Declaramos el Logger para esta clase
    private static final Logger logger = LoggerFactory.getLogger(ConsultaDAO.class);

    public boolean registrarConsulta(int usuarioID, int prendaID) {
        String sql = "INSERT INTO consulta (usuarioID, prendaID) VALUES (?, ?)";
        
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioID);
            ps.setInt(2, prendaID);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            // 2. Registro profesional de errores
            logger.error("Error al registrar la consulta para usuario {}: {}", usuarioID, e.getMessage());
            return false;
        }
    }

    public List<Consulta> obtenerHistorial() {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT * FROM consulta ORDER BY momento_consulta DESC";
        
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(rs.getInt("consultaID"));
                c.setUsuarioID(rs.getInt("usuarioID"));
                c.setPrendaID(rs.getInt("prendaID"));
                c.setMomento(rs.getTimestamp("momento_consulta"));
                
                lista.add(c);
            }
        } catch (SQLException e) {
            logger.error("Error al obtener historial de consultas: {}", e.getMessage());
        }
        return lista;
    }

    public List<Object[]> obtenerHistorialDetallado() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT c.consultaID, u.nombre_usuario, p.nombre_prenda, c.momento_consulta " +
                     "FROM consulta c " +
                     "JOIN usuario_rol u ON c.usuarioID = u.usuarioID " +
                     "JOIN prenda p ON c.prendaID = p.prendaID " +
                     "ORDER BY c.momento_consulta DESC";
        
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("consultaID"),
                    rs.getString("nombre_usuario"),
                    rs.getString("nombre_prenda"),
                    rs.getTimestamp("momento_consulta")
                });
            }
        } catch (SQLException e) {
            logger.error("Error al obtener historial detallado: {}", e.getMessage());
        }
        return lista;
    }
}