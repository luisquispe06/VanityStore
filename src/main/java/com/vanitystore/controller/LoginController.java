package com.vanitystore.controller;

import com.vanitystore.model.Usuario;
import com.vanitystore.model.UsuarioDAO;
import com.vanitystore.view.FrmLogin;
import com.vanitystore.view.FrmDashboard;
import com.vanitystore.model.PrendaDAO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener {
    
    private FrmLogin vista;
    private UsuarioDAO dao;
    private String rolSeleccionado = ""; 

    public LoginController(FrmLogin vista, UsuarioDAO dao) {
        this.vista = vista;
        this.dao = dao;
        this.vista.btnAdmin.addActionListener(this);
        this.vista.btnVendedor.addActionListener(this);
        this.vista.btnIngresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAdmin) {
            rolSeleccionado = "Administrador";
            vista.btnAdmin.setBackground(Color.LIGHT_GRAY);
            vista.btnVendedor.setBackground(null);
        } else if (e.getSource() == vista.btnVendedor) {
            rolSeleccionado = "Vendedor";
            vista.btnVendedor.setBackground(Color.LIGHT_GRAY);
            vista.btnAdmin.setBackground(null);
        } else if (e.getSource() == vista.btnIngresar) {
            if (vista.txtUsuarioID.getText().trim().isEmpty() || vista.txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
                return;
            }
            if (rolSeleccionado.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, seleccione un rol primero.");
                return;
            }
            
            try {
                int id = Integer.parseInt(vista.txtUsuarioID.getText().trim());
                String nombre = vista.txtNombre.getText().trim();
                
                Usuario usuarioValidado = dao.validarAcceso(id, nombre);
                
                if (usuarioValidado != null) {
                    if (usuarioValidado.getRol().equalsIgnoreCase(rolSeleccionado)) {
                        JOptionPane.showMessageDialog(vista, "¡Bienvenido, " + usuarioValidado.getNombreCompleto() + "!");
                        
                        FrmDashboard dashboardVista = new FrmDashboard(usuarioValidado); 
                        PrendaDAO prendaDao = new PrendaDAO();
                        DashboardController dashboardCtrl = new DashboardController(dashboardVista, prendaDao);

                        dashboardVista.setLocationRelativeTo(null);
                        dashboardVista.setVisible(true);
                        vista.dispose(); 
                    } else {
                        JOptionPane.showMessageDialog(vista, "El rol seleccionado no coincide con el usuario.");
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "Usuario o ID no encontrados.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El ID debe ser un número entero.");
            }
        }
    }
}