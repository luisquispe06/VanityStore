package com.vanitystore.controller;

import com.vanitystore.view.FrmStock;
import com.vanitystore.view.FrmAuditoria;
import com.vanitystore.view.FrmGestionPrenda;
import com.vanitystore.model.Prenda;
import com.vanitystore.model.PrendaDAO;
import com.vanitystore.model.StockDAO;
import com.vanitystore.model.ConsultaDAO;
import com.vanitystore.view.FrmDashboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DashboardController implements ActionListener {

    private FrmDashboard vista;
    private PrendaDAO prendaDao;
    private StockDAO stockDao;

    public DashboardController(FrmDashboard vista, PrendaDAO prendaDao) {
        this.vista = vista;
        this.prendaDao = prendaDao;
        this.stockDao = new StockDAO();

        // Registro de listeners con validación de seguridad (previene NullPointerException)
        if (this.vista.btnPanelAdmin != null) this.vista.btnPanelAdmin.addActionListener(this);
        if (this.vista.btnCerrarSesion != null) this.vista.btnCerrarSesion.addActionListener(this);
        if (this.vista.btnAgregarPrenda != null) this.vista.btnAgregarPrenda.addActionListener(this);

        this.vista.panelCatalogo.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

        configurarBuscador();
        cargarCatalogo(this.prendaDao.listarPrendas());
    }

    private void cargarCatalogo(List<Prenda> prendas) {
        if (vista.panelCatalogo == null) return;
        
        vista.panelCatalogo.removeAll(); 

        for (Prenda p : prendas) {
            int totalStock = stockDao.obtenerCantidadTotalPorPrenda(p.getPrendaID());
            Color colorEstado = determinarColor(totalStock);

            JPanel tarjeta = new JPanel();
            tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
            tarjeta.setPreferredSize(new Dimension(160, 220));
            tarjeta.setBackground(Color.WHITE);
            tarjeta.setBorder(BorderFactory.createLineBorder(colorEstado, 3));

            JLabel lblNombre = new JLabel(p.getNombrePrenda());
            lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
            lblNombre.setAlignmentX(JPanel.CENTER_ALIGNMENT);

            JLabel lblStock = new JLabel("Stock: " + totalStock);
            lblStock.setFont(new Font("Arial", Font.BOLD, 11));
            lblStock.setForeground(colorEstado);
            lblStock.setAlignmentX(JPanel.CENTER_ALIGNMENT);

            JButton btnStock = new JButton("Consultar Stock");
            btnStock.setActionCommand("STOCK_" + p.getPrendaID());
            btnStock.addActionListener(this);
            btnStock.setAlignmentX(JPanel.CENTER_ALIGNMENT);

            tarjeta.add(javax.swing.Box.createVerticalStrut(15));
            tarjeta.add(lblNombre);
            tarjeta.add(lblStock);
            tarjeta.add(javax.swing.Box.createVerticalStrut(10));
            tarjeta.add(btnStock);

            vista.panelCatalogo.add(tarjeta);
        }

        vista.panelCatalogo.revalidate();
        vista.panelCatalogo.repaint();
    }

    private Color determinarColor(int cantidad) {
        if (cantidad <= 5) return Color.RED;
        if (cantidad <= 15) return Color.ORANGE;
        return new Color(34, 139, 34);
    }

    private void configurarBuscador() {
        if (vista.txtBuscador != null) {
            vista.txtBuscador.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
                @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
                @Override public void changedUpdate(DocumentEvent e) { filtrar(); }

                private void filtrar() {
                    String criterio = vista.txtBuscador.getText().trim();
                    List<Prenda> filtradas = prendaDao.buscarPrenda(criterio);
                    cargarCatalogo(filtradas);
                }
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (e.getSource() == vista.btnCerrarSesion) {
            vista.dispose();
            
        } else if (e.getSource() == vista.btnPanelAdmin) {
            FrmAuditoria vistaAuditoria = new FrmAuditoria();
            ConsultaDAO daoAuditoria = new ConsultaDAO();
            AuditoriaController ctrlAuditoria = new AuditoriaController(vistaAuditoria, daoAuditoria);
            vistaAuditoria.setLocationRelativeTo(this.vista);
            vistaAuditoria.setVisible(true);

        } else if (e.getSource() == vista.btnAgregarPrenda) {
            FrmGestionPrenda vistaGestion = new FrmGestionPrenda();
            PrendaDAO daoGestion = new PrendaDAO();
            GestionPrendaController ctrlGestion = new GestionPrendaController(vistaGestion);
            
            vistaGestion.setLocationRelativeTo(this.vista);
            vistaGestion.setVisible(true);
            
            vistaGestion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    cargarCatalogo(prendaDao.listarPrendas());
                }
            });

        } else if (comando != null && comando.startsWith("STOCK_")) {
            int idPrenda = Integer.parseInt(comando.split("_")[1]);
            int idUsuario = vista.getUsuarioLogueado().getUsuarioID();
            
            ConsultaDAO consultaDAO = new ConsultaDAO();
            consultaDAO.registrarConsulta(idUsuario, idPrenda);
            
            FrmStock frmStockVista = new FrmStock(this.vista, true);
            StockController stockCtrl = new StockController(frmStockVista, this.stockDao);
            
            stockCtrl.mostrarStockPrenda(idPrenda);
            frmStockVista.setLocationRelativeTo(this.vista);
            frmStockVista.setVisible(true);
        }
    }
}