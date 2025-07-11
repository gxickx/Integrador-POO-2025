package controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import org.example.Main;


public class InicioController {

    @FXML
    private Button btnIrCalendario;

    @FXML
    private Button btnIrEventos;

    @FXML
    private Button btnIrParticipacion;

    @FXML
    private Button btnIrPersonas;

    @FXML
    private void onClickIrCalendario(ActionEvent event) throws IOException {
        Main.setRoot("Calendario");
    }

    @FXML
    private void onClickIrEventos(ActionEvent event) throws IOException {
        Main.setRoot("GestionEventos");
    }

    @FXML
    private void onClickIrParticipacion(ActionEvent event) throws IOException {
        Main.setRoot("GestionParticipantes");
    }

    @FXML
    private void onClickIrPersonas(ActionEvent event) throws IOException {
        Main.setRoot("GestionPersonas");
    }

}
