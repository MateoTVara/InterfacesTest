/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Mateo Torres
 */

import VO.PacienteVo;
import Db.DBConnection;
import static Db.DBConnection.getConnection;
import javafx.collections.*;
import java.sql.*;

public class PacientesDao {
    public static ObservableList<PacienteVo> getPacientes() {
        ObservableList<PacienteVo> pacientes = FXCollections.observableArrayList();
        String query = "SELECT * FROM paciente";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                int dni = rs.getInt("dni");
                String nombrepaciente = rs.getString("nombrepaciente");
                int edad = rs.getInt("edad");
                
                PacienteVo paciente = new PacienteVo(dni, nombrepaciente, edad);
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener pacientes: " + e.getMessage());
        }

        return pacientes;
    }
    
    public static int obtenerDniPacientePorNombre(String nombrePaciente) {
        String query = "SELECT dni FROM paciente WHERE nombrepaciente = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombrePaciente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("dni");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener DNI del paciente: " + e.getMessage());
        }
        return -1;
    }
}
