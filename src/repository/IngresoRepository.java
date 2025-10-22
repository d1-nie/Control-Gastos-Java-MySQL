/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Ingreso;

/**
 *
 * @author Aspire 5
 */
public class IngresoRepository {
    //  Método para insertar un nuevo ingreso
    public void insertar(Ingreso ingreso) throws SQLException {
    String sql = "INSERT INTO ingresos (descripcion, monto, fecha) VALUES (?, ?, NOW())";
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, ingreso.getDescripcion());
        ps.setDouble(2, ingreso.getMonto());
        ps.executeUpdate();
    }
}

    //  Método para listar todos los ingresos
    public List<Ingreso> listar() {
        List<Ingreso> lista = new ArrayList<>();
        String sql = "SELECT * FROM ingresos ORDER BY fecha DESC";

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Ingreso ingreso = new Ingreso();
                ingreso.setId(rs.getInt("id"));
                ingreso.setDescripcion(rs.getString("descripcion"));
                ingreso.setMonto(rs.getDouble("monto"));
                ingreso.setFecha(rs.getDate("fecha"));
                lista.add(ingreso);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar ingresos: " + e.getMessage());
        }

        return lista;
    }

    // Método para actualizar un ingreso existente
    public boolean actualizar(Ingreso ingreso) {
        String sql = "UPDATE ingresos SET descripcion=?, monto=?, fecha=? WHERE id=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ingreso.getDescripcion());
            ps.setDouble(2, ingreso.getMonto());
            ps.setDate(3, ingreso.getFecha());
            ps.setInt(4, ingreso.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar ingreso: " + e.getMessage());
            return false;
        }
    }

    //  Método para eliminar un ingreso por su ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM ingresos WHERE id=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar ingreso: " + e.getMessage());
            return false;
        }
    }

    public double obtenerTotal() throws SQLException {
        String sql = "SELECT IFNULL(SUM(monto), 0) AS total FROM ingresos";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); java.sql.ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
}
