/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;

/**
 *
 * @author Mateo Torres
 */

import Entity.*;
import DAO.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.*;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.StringConverter;
import javafx.collections.transformation.FilteredList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


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
        
        // Selector de paciente (with filtered list)
        Label pacienteLabel = new Label("Paciente:");
        ComboBox<PacienteVo> pacienteComboBox = new ComboBox<>();
        ObservableList<PacienteVo> pacientes = FXCollections.observableArrayList(PacientesDao.getPacientes());
        FilteredList<PacienteVo> filteredPacientes = new FilteredList<>(pacientes, p -> true);

        pacienteComboBox.setItems(filteredPacientes);
        pacienteComboBox.setConverter(new StringConverter<PacienteVo>() {
            @Override
            public String toString(PacienteVo paciente) {
                return paciente != null ? paciente.getNombrepaciente() : "";
            }

            @Override
            public PacienteVo fromString(String string) {
                return null;
            }
        });

        TextField pacienteFilterField = new TextField();
        pacienteFilterField.setPromptText("Buscar paciente...");
        pacienteFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPacientes.setPredicate(paciente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return paciente.getNombrepaciente().toLowerCase().contains(newValue.toLowerCase());
            });
        });

        // Selector de medico (with filtered list)
        Label medicoLabel = new Label("Médico:");
        ComboBox<MedicoVo> medicoComboBox = new ComboBox<>();
        ObservableList<MedicoVo> medicos = FXCollections.observableArrayList(MedicosDao.getMedicos());
        FilteredList<MedicoVo> filteredMedicos = new FilteredList<>(medicos, p -> true);

        medicoComboBox.setItems(filteredMedicos);
        medicoComboBox.setConverter(new StringConverter<MedicoVo>() {
            @Override
            public String toString(MedicoVo medico) {
                return medico != null ? medico.getNombremedico() : "";
            }

            @Override
            public MedicoVo fromString(String string) {
                return null;
            }
        });

        TextField medicoFilterField = new TextField();
        medicoFilterField.setPromptText("Buscar médico...");
        medicoFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMedicos.setPredicate(medico -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return medico.getNombremedico().toLowerCase().contains(newValue.toLowerCase());
            });
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
    
        // Botón para registrar la cita
        Button registrarBtn = new Button("Registrar Cita");
        registrarBtn.setOnAction(e -> {
            PacienteVo pacienteSeleccionado = pacienteComboBox.getValue();
            MedicoVo medicoSeleccionado = medicoComboBox.getValue();
            String fecha = (fechaPicker.getValue() != null) ? fechaPicker.getValue().toString() : "";
            String hora = horaComboBox.getValue();
            String estado = estadoField.getText();

            if (pacienteSeleccionado == null || medicoSeleccionado == null || fecha.isEmpty() || hora == null || estado.isEmpty()) {
                mostrarAlerta("❌ Todos los campos son obligatorios.");
                return;
            }

            int dni = pacienteSeleccionado.getDni(); // Suponiendo que PacienteVo tiene getDni()
            int idMedico = medicoSeleccionado.getIdmedico();

            boolean registrado = CitaDao.registrarCita(dni, idMedico, fecha, hora, estado);
            if (registrado) {
                mostrarAlerta("✅ Cita registrada exitosamente.");
            } else {
                mostrarAlerta("❌ Error al registrar la cita.");
            }
        });


        formLayout.getChildren().addAll(pacienteLabel, pacienteComboBox, 
                                        medicoLabel, medicoComboBox, fechaLabel, fechaPicker,
                                        horaLabel, horaComboBox, estadoLabel, estadoField,
                                        registrarBtn);

        layout.getChildren().addAll(titleLabel, formLayout);

        return layout;
    }
    
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
