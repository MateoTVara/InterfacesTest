/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;

/**
 *
 * @author Mateo Torres
 */

import VO.MedicoVo;
import VO.PacienteVo;
import DAO.*;
import Entity.CitasMedPacEntity;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalTime;
import java.util.stream.*;
import javafx.util.StringConverter;
import javafx.collections.transformation.FilteredList;


public class ScheduleAppointmentView {
    
    private ComboBox<PacienteVo> pacienteComboBox;
    private ComboBox<MedicoVo> medicoComboBox;
    private DatePicker fechaPicker;
    private ComboBox<String> horaComboBox;
    private TextField estadoField;
    private TextField pacienteFilterField;
    private TextField medicoFilterField;
    private TableView<CitasMedPacEntity> citasTableView;

    public Pane getView() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label titleLabel = new Label("Agendar Nueva Cita");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        FlowPane formLayout = new FlowPane();
        formLayout.setHgap(10);
        formLayout.setVgap(10);
        formLayout.setPrefWrapLength(400);

        setupPacienteSelector(formLayout);
        setupMedicoSelector(formLayout);
        setupFechaHoraEstado(formLayout);
        setupRegistrarButton(formLayout);

        layout.getChildren().addAll(titleLabel, formLayout, getGridView());
        return layout;
    }

    private void setupPacienteSelector(Pane layout) {
        Label pacienteLabel = new Label("Paciente:");
        pacienteComboBox = new ComboBox<>();
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

        pacienteFilterField = new TextField();
        pacienteFilterField.setPromptText("Buscar paciente...");
        pacienteFilterField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredPacientes.setPredicate(p -> 
                newVal == null || newVal.isEmpty() || p.getNombrepaciente().toLowerCase().contains(newVal.toLowerCase()));
                pacienteComboBox.show();
        });

        layout.getChildren().addAll(pacienteLabel, pacienteFilterField, pacienteComboBox);
    }

    private void setupMedicoSelector(Pane layout) {
        Label medicoLabel = new Label("Médico:");
        medicoComboBox = new ComboBox<>();
        ObservableList<MedicoVo> medicos = FXCollections.observableArrayList(MedicosDao.getMedicos());
        FilteredList<MedicoVo> filteredMedicos = new FilteredList<>(medicos, m -> true);
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

        medicoFilterField = new TextField();
        medicoFilterField.setPromptText("Buscar médico...");
        medicoFilterField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredMedicos.setPredicate(m -> 
                newVal == null || newVal.isEmpty() || m.getNombremedico().toLowerCase().contains(newVal.toLowerCase()));
                medicoComboBox.show();
        });

        layout.getChildren().addAll(medicoLabel, medicoFilterField, medicoComboBox);
    }

    private void setupFechaHoraEstado(Pane layout) {
        // Fecha
        Label fechaLabel = new Label("Fecha:");
        fechaPicker = new DatePicker();

        // Hora
        Label horaLabel = new Label("Hora:");
        horaComboBox = new ComboBox<>();
        horaComboBox.setItems(FXCollections.observableArrayList(
            IntStream.range(8, 19)
                .mapToObj(h -> LocalTime.of(h, 0))
                .flatMap(t -> IntStream.of(0, 30).mapToObj(t::plusMinutes))
                .map(LocalTime::toString)
                .collect(Collectors.toList())
        ));

        // Estado
        Label estadoLabel = new Label("Estado:");
        estadoField = new TextField();
        estadoField.editableProperty().set(false);
        estadoField.setText("Pendiente");

        layout.getChildren().addAll(fechaLabel, fechaPicker, horaLabel, horaComboBox, estadoLabel, estadoField);
    }

    private void setupRegistrarButton(Pane layout) {
        Button registrarBtn = new Button("Registrar Cita");
        registrarBtn.setOnAction(e -> registrarCita());
        layout.getChildren().add(registrarBtn);
    }

    private void registrarCita() {
        PacienteVo paciente = pacienteComboBox.getValue();
        MedicoVo medico = medicoComboBox.getValue();
        String fecha = (fechaPicker.getValue() != null) ? fechaPicker.getValue().toString() : "";
        String hora = horaComboBox.getValue();
        String estado = estadoField.getText();

        if (paciente == null || medico == null || fecha.isEmpty() || hora == null || estado.isEmpty()) {
            mostrarAlerta("❌ Todos los campos son obligatorios.");
            return;
        }

        boolean registrado = CitaDao.registrarCita(
            paciente.getDni(),
            medico.getIdmedico(),
            fecha,
            hora,
            estado
        );

        if (registrado) {
            mostrarAlerta("✅ Cita registrada exitosamente.");
            actualizarTablaCitas(); // 👈 Aquí actualizas el grid
        } else {
            mostrarAlerta("❌ Error al registrar la cita.");
        }

    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public Pane getGridView() {
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));
        
        citasTableView = new TableView<>();

        TableColumn<CitasMedPacEntity, String> pacienteCol = new TableColumn<>("Paciente");
        pacienteCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombrepaciente()));

        TableColumn<CitasMedPacEntity, Integer> edadCol = new TableColumn<>("Edad");
        edadCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getEdad()).asObject());

        TableColumn<CitasMedPacEntity, String> medicoCol = new TableColumn<>("Médico");
        medicoCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombremedico()));

        TableColumn<CitasMedPacEntity, String> especialidadCol = new TableColumn<>("Especialidad");
        especialidadCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEspecialidad()));

        TableColumn<CitasMedPacEntity, String> fechaCol = new TableColumn<>("Fecha");
        fechaCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFecha()));

        TableColumn<CitasMedPacEntity, String> horaCol = new TableColumn<>("Hora");
        horaCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHora()));

        TableColumn<CitasMedPacEntity, String> estadoCol = new TableColumn<>("Estado");
        estadoCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado()));

        citasTableView.getColumns().addAll(pacienteCol, edadCol, medicoCol, especialidadCol, fechaCol, horaCol, estadoCol);

        // Placeholder data - replace with real data from your DAO
        ObservableList<CitasMedPacEntity> citasData = FXCollections.observableArrayList(CitasMedPacDao.getList());

        citasTableView.setItems(citasData);
        citasTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        layout.getChildren().addAll(new Label("📋 Lista de Citas"), citasTableView);
        return layout;
    }
    
    private void actualizarTablaCitas() {
        ObservableList<CitasMedPacEntity> nuevasCitas = FXCollections.observableArrayList(CitasMedPacDao.getList());
        citasTableView.setItems(nuevasCitas);
    }

}
