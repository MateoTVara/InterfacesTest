/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static Db.DBConnection.getConnection;
import Entity.CitasMedPacEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Mateo Torres
 */
public class CitasMedPacDao {
    public static ObservableList<CitasMedPacEntity> getList(){
        ObservableList<CitasMedPacEntity> entradas = FXCollections.observableArrayList();
        String query = "SELECT p.nombrepaciente, p.edad, m.nombremedico, m.especialidad, c.fecha, c.hora, c.estado FROM cita c JOIN paciente p ON c.dni = p.dni JOIN medico m ON c.idmedico = m.idmedico";
        
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()){
            
            while(rs.next()){
                String nombrepaciente = rs.getString("nombrepaciente");
                int edad = rs.getInt("edad");
                String nombremedico =rs.getString("nombremedico");
                String especialidad = rs.getString("especialidad");
                String fecha = rs.getString("fecha");
                String hora = rs.getString("hora");
                String estado = rs.getString("estado");
                
                CitasMedPacEntity list = new CitasMedPacEntity(nombrepaciente, edad, nombremedico,
                                             especialidad, fecha, hora, estado);
                entradas.add(list);
            }
        }catch(SQLException e){
            System.err.println("❌ Error al obtener registro: " + e.getMessage());
        }
        return entradas;
    }
}
