/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Interfaces;

/**
 *
 * @author Mateo Torres
 * 
 */

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainView {

    private boolean gestionCitasExpanded = false; // Estado del submenú

    public Scene createScene() {
        // Crear los elementos del menú principal
        Label inicio = createMenuItem("Inicio");
        Label gestionCitas = createMenuItem("Gestión de Citas");
        Label historialClinico = createMenuItem("Historial Clínico");
        Label medicosEspecialistas = createMenuItem("Médicos y Especialistas");
        Label paciente = createMenuItem("Paciente");

        // Crear submenú de "Gestión de Citas"
        VBox subMenuCitas = new VBox(5);
        Label agendarCita = createSubMenuItem("Agendar nuevas citas");
        Label consultarCitas = createSubMenuItem("Consultar citas programadas");
        Label cancelarCitas = createSubMenuItem("Cancelar o reprogramar citas");
        subMenuCitas.getChildren().addAll(agendarCita, consultarCitas, cancelarCitas);

        // Inicialmente oculto y sin ocupar espacio
        subMenuCitas.setVisible(false);
        subMenuCitas.setManaged(false);

        // Contenedor del menú lateral
        VBox menuLateral = new VBox(10, inicio, gestionCitas, subMenuCitas, historialClinico, medicosEspecialistas, paciente);
        menuLateral.setPadding(new Insets(10));
        menuLateral.setStyle("-fx-background-color: #f4f4f4; -fx-pref-width: 250px;");

        // Contenedor de contenido principal
        StackPane contentPane = new StackPane();
        Label welcomeLabel = new Label("¡Bienvenido!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: darkblue;");
        contentPane.getChildren().add(welcomeLabel);

        // Manejar clics en los elementos del menú
        inicio.setOnMouseClicked(e -> updateContent(contentPane, "Inicio"));
        historialClinico.setOnMouseClicked(e -> updateContent(contentPane, "Historial Clínico"));
        medicosEspecialistas.setOnMouseClicked(e -> updateContent(contentPane, "Médicos y Especialistas"));
        paciente.setOnMouseClicked(e -> updateContent(contentPane, "Paciente"));

        // Manejar el clic en "Gestión de Citas" para expandir/contraer su submenú
        gestionCitas.setOnMouseClicked(e -> {
            gestionCitasExpanded = !gestionCitasExpanded;
            subMenuCitas.setVisible(gestionCitasExpanded);
            subMenuCitas.setManaged(gestionCitasExpanded);
        });

        // Manejar los clics en el submenú
        agendarCita.setOnMouseClicked(e -> updateContent(contentPane, "Agendar nuevas citas"));
        consultarCitas.setOnMouseClicked(e -> updateContent(contentPane, "Consultar citas programadas"));
        cancelarCitas.setOnMouseClicked(e -> updateContent(contentPane, "Cancelar o reprogramar citas"));

        // Usar BorderPane para organizar la interfaz
        BorderPane rootLayout = new BorderPane();
        rootLayout.setLeft(menuLateral);
        rootLayout.setCenter(contentPane);
        rootLayout.setPadding(new Insets(10));

        // Crear la escena
        return new Scene(rootLayout, 600, 400);
    }

    // Método para actualizar el contenido central
    private void updateContent(Pane contentPane, String text) {
        contentPane.getChildren().clear();
        Label newLabel = new Label("Sección: " + text);
        newLabel.setStyle("-fx-font-size: 18px;");
        contentPane.getChildren().add(newLabel);
    }

    // Método para crear los elementos principales del menú
    private Label createMenuItem(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-padding: 8px; -fx-background-color: #dddddd; -fx-cursor: hand;");
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }

    // Método para crear los elementos del submenú
    private Label createSubMenuItem(String text) {
        Label label = new Label("  - " + text); // Sangría visual
        label.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-cursor: hand;");
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }
}