package controladores;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


import org.example.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelos.Persona;
import modelos.RolPersona;
import modelos.Taller;
import modelos.EstadoEvento;
import modelos.Evento;
import modelos.Modalidad;
import modelos.Participacion;
import servicio.Servicio;


public class ParticipantesController {

    @FXML
    private Button btnAlta;

    @FXML
    private Button btnBaja;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnModificacion;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Evento, String> columnaID;

    @FXML
    private TableColumn<Persona, String> columnaEvento;

    @FXML
    private TableColumn<Persona, String> columnaPersona;

    @FXML
    private TableColumn<Persona, RolPersona> columnaRol;

    @FXML
    private ComboBox<Evento> comboEvento;

    @FXML
    private ComboBox<Persona> comboPersona;

    @FXML
    private ComboBox<RolPersona> comboRol;

    @FXML
    private ComboBox<String> comboVerTipoEvento;

    @FXML
    private Label labelVacantes;

    @FXML
    private TableView<Participacion> tablaPersonas;

    private Servicio servicio;

    @FXML
    private void initialize() {
        servicio = Main.getServicio();
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaPersona.setCellValueFactory(new PropertyValueFactory<>("persona"));
        columnaEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        columnaRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        comboPersona.getItems().clear();
        comboPersona.getItems().addAll(servicio.listarPersonas());
        comboRol.getItems().addAll(RolPersona.values());
        comboVerTipoEvento.getItems().addAll("Concierto", "Exposición", "Taller", "Ciclo de Cine", "Feria");

        
        comboEvento.setDisable(true);
        comboPersona.setDisable(true);
        comboRol.setDisable(true);
        btnConfirmar.setDisable(true);

        LocalDate localDate = LocalDate.now();
        Date fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Evento evento = new Taller("aa", fecha, 3, EstadoEvento.CONFIRMADO, true, true, 20, Modalidad.PRESENCIAL);
        servicio.insertarEvento(evento);

        comboEvento.getItems().addAll(servicio.listarEventos());
         

        tablaPersonas.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());
        try {
            tablaPersonas.getItems().addAll(servicio.listarParticipaciones());
        } catch (Exception e) {
            throw e;
        }  
    }

    void cargarDatos(){
        var item = tablaPersonas.getSelectionModel().getSelectedItem();
        Participacion participacion = (Participacion)item;
        if (participacion != null) {
                comboEvento.setValue(participacion.getEvento());
                comboPersona.setValue(participacion.getPersona());
                comboRol.setValue(participacion.getRol());
        }
    }

    @FXML
    void onClickActivaAltaPersona(ActionEvent event) {
        limpiar();
        if(btnAlta.getText()=="Cancelar"){
            bloquearBotones();
        }
        else{
            desbloquearBotones();
            btnModificacion.setDisable(true);
            btnAlta.setText("Cancelar");

        }
    }

    public void bloquearBotones(){
        comboEvento.setDisable(true);
        comboPersona.setDisable(true);
        comboRol.setDisable(true);
        btnBaja.setDisable(false);
        btnModificacion.setDisable(false);
        btnConfirmar.setDisable(true);
        btnAlta.setText("Alta");
    }

    public void desbloquearBotones(){
        comboEvento.setDisable(false);
        comboPersona.setDisable(false);
        comboRol.setDisable(false);
        btnConfirmar.setDisable(false);
        btnBaja.setDisable(true);
    }

    @FXML
    void onClickAltaPersona(ActionEvent event) {
        Persona persona = comboPersona.getValue();
        Evento evento = comboEvento.getValue();
        RolPersona rol = comboRol.getValue();
        if (btnAlta.getText()=="Cancelar" && persona != null && evento != null && rol != null) 
            {
                Participacion participacion = new Participacion(persona, evento, rol);
                servicio.insertarParticipacion(participacion);
            }

        bloquearBotones();
        limpiar();
    }

    @FXML
    void onClickBajaPersona(ActionEvent event) {

    }

    @FXML
    void onClickModifcarPersona(ActionEvent event) {
        var persona = tablaPersonas.getSelectionModel().getSelectedItem();
         if(btnModificacion.getText()=="Cancelar"){
            btnModificacion.setText("Modificación");
            bloquearBotones();
            btnAlta.setDisable(false);
            limpiar();
        }
        else if (persona != null) {
            desbloquearBotones();
            comboEvento.setDisable(true);
        }

    }

    @FXML
    void onClickVolverInicio(ActionEvent event) throws IOException {
        Main.setRoot("PantallaInicio");
    }

    @FXML
    void OnSelectionfiltrarPorTipoEvento(ActionEvent event) {

    }

    private void limpiar() {
        comboEvento.getSelectionModel().clearSelection();
        comboPersona.getSelectionModel().clearSelection();
        comboRol.getSelectionModel().clearSelection();

        tablaPersonas.getItems().clear();
        try {
            tablaPersonas.getItems().addAll(servicio.listarParticipaciones());
        } catch (Exception e) {
            throw e;
        }
        tablaPersonas.getSelectionModel().clearSelection();
    }
}
