package com.vanitystore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Definimos el logger para esta clase
    private static final Logger logger = LoggerFactory.getLogger(Conexion.class);

    private static final String URL = "jdbc:mysql://localhost:3306/vanity_store";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection getConexion() {
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Registro de éxito profesional
            logger.info("Conexión a la base de datos establecida con éxito.");
            
        } catch (ClassNotFoundException | SQLException e) {
            // Registro de error profesional (incluye el detalle del error)
            logger.error("Error al conectar a la base de datos: {}", e.getMessage());
        }
        return cn;
    }
}