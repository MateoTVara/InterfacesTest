 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Mateo Torres
 */

import VO.MedicoVo;
import Db.DBConnection;
import static Db.DBConnection.getConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicosDao {
    public static ObservableList<MedicoVo> getMedicos() {
        ObservableList<MedicoVo> medicos = FXCollections.observableArrayList();
        String query = "SELECT * FROM medico";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                int id = rs.getInt("idmedico");
                String nombremedico = rs.getString("nombremedico");
                String especialidad = rs.getString("especialidad");

                MedicoVo medico = new MedicoVo(id, nombremedico, especialidad);
                medicos.add(medico);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener médicos: " + e.getMessage());
        }

        return medicos;
    }
    
    public static int obtenerIdMedicoPorNombre(String nombreMedico) {
        String query = "SELECT idmedico FROM medico WHERE nombremedico = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreMedico);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idmedico");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener ID del médico: " + e.getMessage());
        }
        return -1;
    }
}
