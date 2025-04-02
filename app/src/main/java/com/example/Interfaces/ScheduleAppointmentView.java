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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ScheduleAppointmentView {
    public Pane getView() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label titleLabel = new Label("Agendar Nueva Cita");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        FlowPane formLayout = new FlowPane();
        formLayout.setHgap(10);
        formLayout.setVgap(10);
        formLayout.setPrefWrapLength(400);

        // Lista de pacientes con filtrado dinámico (3 elementos para simular la funcionalidad)
        Label pacienteLabel = new Label("Paciente:");
        ObservableList<String> pacientes = FXCollections.observableArrayList("Juan Pérez", "María Gómez", "Carlos Díaz");
        ComboBox<String> pacienteComboBox = new ComboBox<>(FXCollections.observableArrayList(pacientes));
        pacienteComboBox.setEditable(true);
        pacienteComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            pacienteComboBox.hide();
            pacienteComboBox.setItems(FXCollections.observableArrayList(
                pacientes.stream().filter(p -> p.toLowerCase().contains(newValue.toLowerCase())).collect(Collectors.toList())
            ));
            pacienteComboBox.show();
        });

        // Lista de médicos con filtrado dinámico (3 elementos para simular la funcionalidad)
        Label medicoLabel = new Label("Médico:");
        ObservableList<String> medicos = FXCollections.observableArrayList("Dr. Martínez", "Dra. Suárez", "Dr. Ramírez");
        ComboBox<String> medicoComboBox = new ComboBox<>(FXCollections.observableArrayList(medicos));
        medicoComboBox.setEditable(true);
        medicoComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            medicoComboBox.hide();
            medicoComboBox.setItems(FXCollections.observableArrayList(
                medicos.stream().filter(m -> m.toLowerCase().contains(newValue.toLowerCase())).collect(Collectors.toList())
            ));
            medicoComboBox.show();
        });

        // Selector de fecha
        Label fechaLabel = new Label("Fecha:");
        DatePicker fechaPicker = new DatePicker();

        // Selector de hora con rangos predefinidos
        Label horaLabel = new Label("Hora:");
        ComboBox<String> horaComboBox = new ComboBox<>();
        horaComboBox.setItems(FXCollections.observableArrayList(
            IntStream.range(8, 19) // Horas de 08:00 a 18:00
                .mapToObj(h -> LocalTime.of(h, 0))
                .flatMap(t -> IntStream.of(0, 30).mapToObj(m -> t.plusMinutes(m)))
                .map(LocalTime::toString)
                .collect(Collectors.toList())
        ));

        // Estado de la cita
        Label estadoLabel = new Label("Estado:");
        TextField estadoField = new TextField();

        formLayout.getChildren().addAll(pacienteLabel, pacienteComboBox, 
                                        medicoLabel, medicoComboBox, fechaLabel, fechaPicker,
                                        horaLabel, horaComboBox, estadoLabel, estadoField);

        layout.getChildren().addAll(titleLabel, formLayout);

        return layout;
    }
    
    public static TempLabelTextField assignLabel(Label label, TextField textField){
        textField= new TextField();
        return new TempLabelTextField(textField, label);      
    }
}
