package com.vanitystore.config;

import com.vanitystore.controller.LoginController;
import com.vanitystore.model.UsuarioDAO;
import com.vanitystore.view.FrmLogin;

public class Principal {

    public static void main(String[] args) {
        // 1. Instanciamos la Vista (el diseño de Balsamiq hecho en NetBeans)
        FrmLogin vistaLogin = new FrmLogin();
        
        // 2. Instanciamos el DAO (el mensajero que va a la base de datos)
        UsuarioDAO usuarioDao = new UsuarioDAO();
        
        // 3. Creamos el Controlador y le entregamos la vista y el dao para que los maneje
        LoginController controlador = new LoginController(vistaLogin, usuarioDao);
        
        // 4. Centramos la pantalla en el monitor del usuario y la hacemos visible
        vistaLogin.setLocationRelativeTo(null);
        vistaLogin.setVisible(true);
    }
}
    
