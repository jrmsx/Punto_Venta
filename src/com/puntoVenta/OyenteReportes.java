/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puntoVenta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JR
 */
public class OyenteReportes implements ActionListener {

    private Conexion usuario;
    static Reportes ventanaReporte;
    private JTable productos;
    PanelVentas p;
    PanelVendedores p1;
    PanelProductos p2;
    PanelCatalogo catalogo;

    OyenteReportes() {

    }

    OyenteReportes(Reportes ventaReportes, Conexion usuario) {
        this.ventanaReporte = ventaReportes;
        this.usuario = usuario;
    }

    public void setVentanaReporte(Reportes ventanaReporte) {
        this.ventanaReporte = ventanaReporte;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Ventas":

                System.out.println("Ventas");
                p = new PanelVentas();
                try {
                    ventanaReporte.remove(p);
                    ventanaReporte.remove(p1);
                    ventanaReporte.remove(p2);

                } catch (Exception ex) {
                    ventanaReporte.add(p);

                }

                ventanaReporte.add(p);
                ventanaReporte.setpVentas(p);
                SwingUtilities.updateComponentTreeUI(ventanaReporte);
                ventanaReporte.validate();

                break;
            case "Ventas por Vendedor":
                System.out.println("aqui se muestra las ventas por vendedor");
                p1 = new PanelVendedores();
                try {
                    ventanaReporte.remove(p);
                    ventanaReporte.remove(p1);
                    ventanaReporte.remove(p2);

                } catch (Exception ex) {
                    ventanaReporte.add(p1);

                }
                ventanaReporte.add(p1);
                ventanaReporte.setpVendedores(p1);
                SwingUtilities.updateComponentTreeUI(ventanaReporte);
                ventanaReporte.validate();
                break;
            case "Productos mas vendidos":
                System.out.println("Aqui se muestran los productos mas vendidos");
                p2 = new PanelProductos();
                try {
                    ventanaReporte.remove(p);
                    ventanaReporte.remove(p1);
                    ventanaReporte.remove(p2);

                } catch (Exception ex) {
                    ventanaReporte.add(p2);

                }
                ventanaReporte.add(p2);
                ventanaReporte.setpProductos(p2);
                SwingUtilities.updateComponentTreeUI(ventanaReporte);
                ventanaReporte.validate();
                break;
            case "Salir":
                System.out.println("Con esto puedes salir");
                ventanaReporte.dispose();
                break;
            case "Acerca de":
                System.out.println("Mostrara acerca del programa");
                JOptionPane.showConfirmDialog(null, "2014,2015 PiñaSports©", "Acerca de", JOptionPane.DEFAULT_OPTION);

                break;
            case "Actualizar":
                System.out.println("Aqui actualizara");
                break;
            case "Buscar":
                System.out.println("Con esto buscara");
                break;
                
                
            case "Catalogo de Productos":
                productos = generarCatalogo();
                catalogo = new PanelCatalogo(productos);
                ventanaReporte.add(catalogo, "Center");
                SwingUtilities.updateComponentTreeUI(ventanaReporte);
                break;
        }
    }

    public Conexion getUsuario() {
        return usuario;
    }

    public void setUsuario(Conexion usuario) {
        this.usuario = usuario;
    }

    private JTable generarCatalogo() {

        String query = "SELECT * FROM Producto";
        DefaultTableModel modelo = new DefaultTableModel();
        JTable tabla = new JTable(modelo);
        try {
            usuario.iniciarConexion();
            usuario.setResult(usuario.getStament().executeQuery(query));
            ResultSetMetaData metaDatos = usuario.getResult().getMetaData();
            // Se obtiene el número de columnas.
            int numeroColumnas = metaDatos.getColumnCount();

            // Se crea un array de etiquetas para rellenar
            Object[] etiquetas = new Object[numeroColumnas];

            // Se obtiene cada una de las etiquetas para cada columna
            for (int i = 0; i < numeroColumnas; i++) {
                // Nuevamente, para ResultSetMetaData la primera columna es la 1.
                etiquetas[i] = metaDatos.getColumnLabel(i + 1);
            }

            modelo.setColumnIdentifiers(etiquetas);
            // Bucle para cada resultado en la consulta
            while (usuario.getResult().next()) {
                // Se crea un array que será una de las filas de la tabla.
                Object[] fila = new Object[numeroColumnas]; // Hay tres columnas en la tabla

                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                for (int i = 0; i < numeroColumnas; i++) {
                    fila[i] = usuario.getResult().getObject(i + 1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
                }
                // Se añade al modelo la fila completa.
                modelo.addRow(fila);
            }
            usuario.getStament().close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }finally{
            
            usuario.cerrarConexion();
        }
        
        return tabla;
    }

}