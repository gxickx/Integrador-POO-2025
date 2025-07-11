package controladores;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import modelos.Evento;
import servicio.Servicio;
import modelos.*;

import java.util.Date;
import java.util.List;

public class EventosController {

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
    private TableColumn<Evento, ?> columnaCupoMax;

    @FXML
    private TableColumn<?, ?> columnaCuposVacantes;

    @FXML
    private TableColumn<Evento, ?> columnaDuracion;

    @FXML
    private TableColumn<?, ?> columnaEstado;

    @FXML
    private TableColumn<Evento, Date> columnaFechaInicio;

    @FXML
    private TableColumn<?, ?> columnaID;

    @FXML
    private TableColumn<?, ?> columnaNombre;

    @FXML
    private ComboBox<EstadoEvento> comboEstadoEvento;

    @FXML
    private ComboBox<Enum<?>> comboOpcional; //tipoArte, modalidad, tipoEntrada

    @FXML
    private CheckBox checkAlAireLibre;       // para Feria
    @FXML
    private CheckBox checkTieneCharlas;      // para CicloCine
    @FXML
    private CheckBox checkTieneInscripcion;

    @FXML
    private ComboBox<String> comboTipoEvento;

    @FXML
    private DatePicker dateFin;

    @FXML
    private DatePicker dateInicio;

    @FXML
    private Label labelCombo;

    @FXML
    private Label labelTextOpcional;

    @FXML
    private TableView<Evento> tablaEventos;

    @FXML
    private TextField textOpcional; //cantidad de stands

    @FXML
    private TextField txtNombreEvento;

    private Servicio servicio;

    @FXML
    private void initialize(){
        servicio = Main.getServicio();

        columnaID.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        columnaDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        columnaCupoMax.setCellValueFactory(new PropertyValueFactory<>("cupoMaximo"));
        columnaCuposVacantes.setCellValueFactory(new PropertyValueFactory<>("vacantes"));

        // Deshabilitar campos específicos y generales hasta que se seleccione evento
        bloquearBotones();
        comboTipoEvento.getItems().addAll("Concierto", "Exposición", "Taller", "Ciclo de Cine", "Feria");
        comboTipoEvento.setOnAction(e -> mostrarCamposSegunTipo());

        tablaEventos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> cargarDatos());

        try {
            tablaEventos.getItems().addAll(servicio.listarEventos());
        } catch (Exception e) {
           throw e;
        }
    }
    private void mostrarCamposSegunTipo() {
        String tipo = comboTipoEvento.getValue();

        // reiniciamos todos los campos
        comboOpcional.setVisible(false);
        comboOpcional.setDisable(true);
        textOpcional.setVisible(false);
        textOpcional.setDisable(true);
        checkAlAireLibre.setVisible(false);
        checkAlAireLibre.setDisable(true);
        checkTieneCharlas.setVisible(false);
        checkTieneCharlas.setDisable(true);
        labelCombo.setVisible(false);
        labelTextOpcional.setVisible(false);

        switch (tipo) {
            case "Concierto" -> {
                labelCombo.setVisible(true);
                labelCombo.setText("Tipo de entrada:");
                comboOpcional.setVisible(true);
                comboOpcional.setDisable(false);
                comboOpcional.getItems().setAll(TipoEntrada.values()); // o enum
            }
            case "Exposición" -> {
                labelCombo.setVisible(true);
                labelCombo.setText("Tipo de arte:");
                comboOpcional.setVisible(true);
                comboOpcional.setDisable(false);
                comboOpcional.getItems().setAll(TipoArte.values()); // o enum
            }
            case "Taller" -> {
                labelCombo.setVisible(true);
                labelCombo.setText("Modalidad:");
                comboOpcional.setVisible(true);
                comboOpcional.setDisable(false);
                comboOpcional.getItems().setAll(Modalidad.values()); // o enum
            }
            case "Ciclo de Cine" -> {
                checkTieneCharlas.setVisible(true);
                checkTieneCharlas.setDisable(false);
            }
            case "Feria" -> {
                labelTextOpcional.setVisible(true);
                labelTextOpcional.setText("Cant. Stands:");
                textOpcional.setVisible(true);
                textOpcional.setDisable(false);
                checkAlAireLibre.setVisible(true);
                checkAlAireLibre.setDisable(false);
            }
        }
    }


    @FXML
    void onClickActivaAltaEvento(ActionEvent event) {
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

    @FXML
    void onClickAltaEvento(ActionEvent event) {

    }

    @FXML
    void onClickBajaEvento(ActionEvent event) {

    }

    @FXML
    void onClickModifcarEvento(ActionEvent event) {

    }

    @FXML
    void onClickVolverInicio(ActionEvent event) throws IOException {
        Main.setRoot("PantallaInicio");
    }

    @FXML
    private void cargarDatos(){
        var item = tablaEventos.getSelectionModel().getSelectedItem();
        Evento evento = (Evento) item;
        if (evento != null) {
            txtNombreEvento.setText(evento.getNombre());

        }

    }

    @FXML
    public void bloquearBotones() {
        txtNombreEvento.setDisable(true);
        dateFin.setDisable(true);
        dateInicio.setDisable(true);
        comboEstadoEvento.setDisable(true);
        comboTipoEvento.setDisable(true);
        comboOpcional.setDisable(true);
        textOpcional.setDisable(true);
        checkAlAireLibre.setDisable(true);
        checkTieneCharlas.setDisable(true);

        //campos opcionales
        comboOpcional.setVisible(false);
        textOpcional.setVisible(false);
        checkAlAireLibre.setVisible(false);
        checkTieneCharlas.setVisible(false);
        labelCombo.setVisible(false);
        labelTextOpcional.setVisible(false);

        btnAlta.setText("Alta");
        btnModificacion.setText("Modificación");
    }

    @FXML
    public void desbloquearBotones() {
        txtNombreEvento.setDisable(false);
        dateFin.setDisable(false);
        dateInicio.setDisable(false);
        comboEstadoEvento.setDisable(false);
        comboTipoEvento.setDisable(false);

        labelCombo.setVisible(true);
        labelTextOpcional.setVisible(true);
    }

    @FXML
    public void limpiar(){
        txtNombreEvento.setText("");
        dateFin.setValue(null);
        dateInicio.setValue(null);
        comboEstadoEvento.setValue(null);
        comboOpcional.setValue(null);
        comboTipoEvento.setValue(null);
        textOpcional.setText("");

        tablaEventos.getItems().clear();
        try {
            tablaEventos.getItems().addAll(servicio.listarEventos());
        } catch (Exception e) {
            throw e;
        }
        tablaEventos.getSelectionModel().clearSelection();
    }
}
