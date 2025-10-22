/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Gasto;
import repository.GastoRepository;

/**
 *
 * @author Aspire 5
 */
public class GastoController {

    private final GastoRepository gastoRepo;

    // Constructor
    public GastoController() {
        this.gastoRepo = new GastoRepository();
    }

    // Método para registrar un nuevo gasto
    public void registrarGasto(String descripcion, double monto) {
        try {
            Gasto gasto = new Gasto();
            gasto.setDescripcion(descripcion);
            gasto.setMonto(monto);
            gasto.setFecha(java.time.LocalDate.now());

            gastoRepo.insertar(gasto);
            JOptionPane.showMessageDialog(null, "✅ Gasto registrado correctamente");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al registrar gasto: " + e.getMessage());
        }
    }

    // Método para mostrar los gastos en una tabla
    public void mostrarGastos(javax.swing.JTable tabla) {
        try {
            List<Gasto> lista = gastoRepo.obtenerTodos();
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0); // Limpia la tabla antes de llenarla

            for (Gasto g : lista) {
                modelo.addRow(new Object[]{
                    g.getId(),
                    g.getDescripcion(),
                    g.getMonto(),
                    g.getFecha()
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al listar gastos: " + e.getMessage());
        }
    }

    // Método para eliminar un gasto
    public void eliminarGasto(int id) {
        try {
            boolean eliminado = gastoRepo.eliminar(id);
            if (eliminado) {
                JOptionPane.showMessageDialog(null, "🗑️ Gasto eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "⚠️ No se encontró el gasto con ese ID");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al eliminar gasto: " + e.getMessage());
        }
    }

    // Método para mostrar el total de gastos
    public void mostrarTotal(javax.swing.JLabel lblTotal) {
        try {
            double total = gastoRepo.obtenerTotal();
            lblTotal.setText("Total de gastos: $" + total);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al calcular el total: " + e.getMessage());
        }
    }
}
