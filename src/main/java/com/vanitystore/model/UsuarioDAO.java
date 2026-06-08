package com.vanitystore.model;

import com.vanitystore.config.Conexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Logger para auditoría de accesos
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    public Usuario validarAcceso(int id, String nombreUsuario) {
        Usuario usuarioEncontrado = null;
        String sql = "SELECT * FROM usuario_rol WHERE usuarioID = ? AND nombre_usuario = ?";
        
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.setString(2, nombreUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuarioEncontrado = new Usuario();
                    usuarioEncontrado.setUsuarioID(rs.getInt("usuarioID"));
                    usuarioEncontrado.setNombreCompleto(rs.getString("nombre_completo"));
                    usuarioEncontrado.setRol(rs.getString("rol"));
                    
                    logger.info("Acceso exitoso para el usuario ID: {} (Nombre: {})", id, nombreUsuario);
                } else {
                    logger.warn("Intento de acceso fallido para el usuario ID: {}", id);
                }
            }
        } catch (SQLException e) {
            // Error técnico de base de datos
            logger.error("Error crítico al validar acceso en UsuarioDAO para ID {}: {}", id, e.getMessage());
        }
        return usuarioEncontrado;
    }
}