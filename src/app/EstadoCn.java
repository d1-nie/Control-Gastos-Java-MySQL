package app;

import conexion.Conexion;
import java.sql.Connection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Aspire 5
 */

public class EstadoCn {
    public static void main(String[] args) {
        Connection c = Conexion.conectar();
        if (c != null) {
            System.out.println("Conexión establecida");
        } else {
            System.out.println("Fallo conexión");
        }
    }
    
}
