/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Gasto;

/**
 *
 * @author Aspire 5
 */
public class GastoRepository {
 
    // Método para insertar un nuevo gasto en la base de datos
    // Método para insertar un nuevo gasto en la base de datos
    public void insertar(Gasto gasto) throws SQLException {
        String sql = "INSERT INTO gastos (descripcion, monto, fecha) VALUES (?, ?, NOW())";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, gasto.getDescripcion());
            ps.setDouble(2, gasto.getMonto());
            
            ps.executeUpdate();
        }
    }
    // Método para actualizar un gasto existente
    public boolean actualizar(Gasto gasto) throws SQLException {
        String sql = "UPDATE gastos SET descripcion = ?, monto = ?, fecha = ? WHERE id = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, gasto.getDescripcion());
            ps.setDouble(2, gasto.getMonto());
            ps.setDate(3, Date.valueOf(gasto.getFecha()));
            ps.setInt(4, gasto.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar gasto: " + e.getMessage());
            return false;
        }
    }
    // Método para eliminar un gasto por su ID
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM gastos WHERE id = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar gasto: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todos los gastos registrados
    public List<Gasto> obtenerTodos() throws SQLException {
        List<Gasto> lista = new ArrayList<>();
        String sql = "SELECT * FROM gastos";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Gasto g = new Gasto(
                    rs.getInt("id"),
                    rs.getString("descripcion"),
                    rs.getDouble("monto"),
                    rs.getDate("fecha").toLocalDate() // convierte SQL Date -> LocalDate
                );
                lista.add(g);
            }
        }

        return lista;
    }
    // Método para calcular el total de todos los gastos

    public double obtenerTotal() throws SQLException {
        String sql = "SELECT IFNULL(SUM(monto), 0) AS total FROM gastos";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
}

