/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import vista.IngresoFrom;

/**
 *
 * ARCHIVO EJECUTABLE PARA LA APP
 */
public class AppExecute {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new IngresoFrom().setVisible(true);
        });
    }
}
