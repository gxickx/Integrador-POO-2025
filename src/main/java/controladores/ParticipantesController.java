package controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import modelos.Evento;
import modelos.Participacion;
import servicio.Servicio;
import modelos.EstadoEvento;
import modelos.Alerta;


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
    private Label lblVacantes;

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
        comboRol.getItems().clear();
        actualizarRolesPorEvento(null);
        comboVerTipoEvento.getItems().addAll("Concierto", "Exposición", "Taller", "Ciclo de Cine", "Feria");

        
        comboEvento.setDisable(true);
        comboPersona.setDisable(true);
        comboRol.setDisable(true);
        btnConfirmar.setDisable(true);
        comboVerTipoEvento.setOnAction(this::onSeleccionarTipoEvento);


        comboEvento.getItems().addAll(servicio.listarEventos());
        comboEvento.valueProperty().addListener((obs, oldVal, newVal) -> {
        actualizarRolesPorEvento(newVal);
        actualizarLabelVacantes(newVal);});
         

        tablaPersonas.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());
        try {
            tablaPersonas.getItems().addAll(servicio.listarParticipaciones());
        } catch (Exception e) {
            throw e;
        }  
    }

    private void actualizarLabelVacantes(Evento evento) {
        if (evento == null) {
            lblVacantes.setVisible(false);
            return;
        }

        if (evento.isTieneCupo()) {
            lblVacantes.setVisible(true);
            if (evento.getVacantes() > 0) {
                lblVacantes.setText("* Vacantes disponibles: " + evento.getVacantes());
            } else {
                lblVacantes.setText("* No hay vacantes disponibles");
            }
        } else {
            lblVacantes.setVisible(false);
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
        if (btnAlta.getText()=="Cancelar" && persona != null && evento != null && rol != null) {
            if (evento.getEstado() != EstadoEvento.CONFIRMADO){
                Alerta.mostrarAlerta("Error", "Solo se pueden agregar personas a eventos CONFIRMADOS.");
                limpiar();
                return;
            }
            Participacion participacion = new Participacion(persona, evento, rol);
            servicio.insertarParticipacion(participacion);
        }

        bloquearBotones();
        limpiar();
    }

    @FXML
    void onClickBajaPersona(ActionEvent event) {
        var item = tablaPersonas.getSelectionModel().getSelectedItem();
        Participacion participacion = (Participacion)item;
        if (participacion != null) {
            try {
                servicio.eliminarParticipacion(participacion);
            } catch (Exception e) {
                throw e;
            }
            limpiar();
        }
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

    private void actualizarRolesPorEvento(Evento evento) {
    comboRol.getItems().clear();
    comboRol.getItems().add(RolPersona.ORGANIZADOR);
    comboRol.getItems().add(RolPersona.PARTICIPANTE);

    if (evento != null) {
        String clase = evento.getClass().getSimpleName();
        switch (clase) {
            case "Concierto":
                comboRol.getItems().add(RolPersona.ARTISTA);
                break;
            case "Taller":
                comboRol.getItems().add(RolPersona.INSTRUCTOR);
                break;
            case "Exposicion":
                comboRol.getItems().add(RolPersona.CURADOR);
                break;
        }
    }
    }

    @FXML
    void onSeleccionarTipoEvento(ActionEvent event) {
        String tipoSeleccionado = comboVerTipoEvento.getValue();
        if (tipoSeleccionado != null) {
            List<Participacion> participacionesFiltradas = new ArrayList<>();
            for (Participacion p : servicio.listarParticipaciones()) {
                Evento evento = p.getEvento();
                if (evento != null && evento.getClass().getSimpleName().equalsIgnoreCase(mapearNombreAClase(tipoSeleccionado))) {
                    participacionesFiltradas.add(p);
                }
            }

            tablaPersonas.getItems().setAll(participacionesFiltradas);
        }
    }

    private String mapearNombreAClase(String tipo) {
    return switch (tipo) {
        case "Ciclo de Cine" -> "CicloCine";
        default -> tipo;
    };
}


}
