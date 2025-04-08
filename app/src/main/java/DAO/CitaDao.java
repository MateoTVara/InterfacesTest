/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Mateo Torres
 */

import static Db.DBConnection.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CitaDao {
    public static boolean registrarCita(int dni, int idMedico, String fecha, String hora, String estado) {
        String query = "INSERT INTO cita (dni, idmedico, fecha, hora, estado) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, dni);
            stmt.setInt(2, idMedico);
            stmt.setString(3, fecha);
            stmt.setString(4, hora);
            stmt.setString(5, estado);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0; 
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar la cita: " + e.getMessage());
            return false;
        }
    }
}
