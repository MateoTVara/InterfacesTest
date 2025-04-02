/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Interfaces;

/**
 *
 * @author Mateo Torres
 */
import com.example.Auxiliary.TempLabelTextField;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class ScheduleAppointmentView {
    public Pane getView() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label titleLabel = new Label("Agendar Nueva Cita");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label pacienteLabel = new Label("Paciente:");
        TextField pacienteField = new TextField();
        
        Label medicoLabel = new Label("Médico:");
        TextField medicoField = new TextField();
        
        Label fechaLabel = new Label("Fecha:");
        TextField fechaField = new TextField();
        
        Label horaLabel = new Label("Hora:");
        TextField horaField = new TextField();
        
        Label estadoLabel = new Label("Label:");
        TextField estadoField = new TextField();
        
        layout.getChildren().addAll(titleLabel, pacienteLabel, pacienteField, medicoLabel, medicoField,
                                    fechaLabel, fechaField, horaLabel, horaField, estadoLabel, estadoField);
        
        return layout;
    }
    
    public static TempLabelTextField assignLabel(Label label, TextField textField){
        textField= new TextField();
        return new TempLabelTextField(textField, label);      
    }
}
