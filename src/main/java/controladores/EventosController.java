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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
    private CheckBox checkTieneCupo;


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
    @FXML
    private TextField txtCupoMax;

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
        if(btnAlta.getText().equals("Cancelar")){
            bloquearBotones();
        }else{
            desbloquearBotones();
            btnModificacion.setDisable(true);
            btnAlta.setText("Cancelar");
        }
    }

    @FXML
    void onClickAltaEvento(ActionEvent event) {
        var item = tablaEventos.getSelectionModel().getSelectedItem();
        Evento evento = (Evento) item;

        try {
            // MODIFICACIÓN
            if (item != null && btnModificacion.getText().equals("Cancelar")) {
                servicio.modificarEvento(evento.getIdEvento(), evento.getNombre(), evento.getFechaInicio(), evento.getDuracion(), evento.getEstado(), evento.getCupoMaximo(), evento.isRequiereInscripcion());
                btnAlta.setDisable(false);
            }

            // ALTA
            if (btnAlta.getText().equals("Cancelar")) {
                String nombre = txtNombreEvento.getText();
                var fechaInicioDate = dateInicio.getValue();
                var fechaFinDate = dateFin.getValue();
                var estado = comboEstadoEvento.getValue();
                var tipoEvento = comboTipoEvento.getValue();
                var requiereInscripcion = checkTieneInscripcion.isSelected();
                var tieneCupo = checkTieneCupo.isSelected();
                int cupoMaximo;
                try {
                    cupoMaximo = Integer.parseInt(txtCupoMax.getText());
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "El cupo máximo debe ser un número válido.");
                    return;
                }
                int duracion = (int) ChronoUnit.DAYS.between(fechaInicioDate, fechaFinDate) + 1;
                //convertir fecha inicio y fecha fin a Date pq el datePicker nos da un tipo de LocalDate, lo que genera errores en las clases
                Date fechaInicio = Date.from(fechaInicioDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fechaFin = Date.from(fechaFinDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (nombre.isBlank() ||  estado == null || tipoEvento == null) {
                    mostrarAlerta("Error", "Debe completar todos los campos obligatorios.");
                    return;
                }

                if (fechaFinDate.isBefore(fechaInicioDate)) {
                    mostrarAlerta("Error", "La fecha de fin no puede ser anterior a la de inicio.");
                    return;
                }

                Evento nuevoEvento = null;

                switch (tipoEvento) {
                    case "Concierto" -> {
                        TipoEntrada entrada = (TipoEntrada) comboOpcional.getValue();
                        if (entrada == null) {
                            mostrarAlerta("Error", "Debe seleccionar un tipo de entrada.");
                            return;
                        }
                        nuevoEvento = new Concierto(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo, entrada);
                    }
                    case "Exposición" -> {
                        TipoArte arte = (TipoArte) comboOpcional.getValue();
                        if (arte == null) {
                            mostrarAlerta("Error", "Debe seleccionar un tipo de arte.");
                            return;
                        }
                        nuevoEvento = new Exposicion(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo, arte);
                    }
                    case "Taller" -> {
                        Modalidad modalidad = (Modalidad) comboOpcional.getValue();
                        if (modalidad == null) {
                            mostrarAlerta("Error", "Debe seleccionar una modalidad.");
                            return;
                        }
                        nuevoEvento = new Taller(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo, modalidad);
                    }
                    case "Ciclo de Cine" -> {
                        boolean tieneCharlas = checkTieneCharlas.isSelected();
                        nuevoEvento = new CicloCine(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo, tieneCharlas);
                    }
                    case "Feria" -> {
                        String cantTexto = textOpcional.getText();
                        if (cantTexto == null || cantTexto.isBlank()) {
                            mostrarAlerta("Error", "Debe ingresar la cantidad de stands.");
                            return;
                        }
                        int cantStands;
                        try {
                            cantStands = Integer.parseInt(cantTexto);
                        } catch (NumberFormatException e) {
                            mostrarAlerta("Error", "La cantidad de stands debe ser un número.");
                            return;
                        }
                        boolean alAireLibre = checkAlAireLibre.isSelected();
                        nuevoEvento = new Feria(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo, cantStands, alAireLibre);
                    }
                }

                if (nuevoEvento != null) {
                    servicio.insertarEvento(nuevoEvento);
                }
            }
            bloquearBotones();
            limpiar();
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al registrar el evento.");
            throw e;
        }
    }

    @FXML
    void onClickBajaEvento(ActionEvent event) {

    }

    @FXML
    void onClickModificarEvento(ActionEvent event) {
        var item = tablaEventos.getSelectionModel().getSelectedItem();
        Evento evento = (Evento) item;

        if (btnModificacion.getText().equals("Cancelar")) {
            btnModificacion.setText("Modificación");
            bloquearBotones();
            btnAlta.setDisable(false);
            limpiar();
        } else if (evento != null) {
            txtNombreEvento.setText(evento.getNombre());
            dateInicio.setValue(evento.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            checkTieneCupo.setSelected(evento.isTieneCupo());
            txtCupoMax.setText(String.valueOf(evento.getCupoMaximo()));
            comboEstadoEvento.setValue(evento.getEstado());
            checkTieneInscripcion.setSelected(evento.isRequiereInscripcion());

            desbloquearBotones();

            btnAlta.setDisable(true);
            btnBaja.setDisable(true);
            btnModificacion.setText("Cancelar");
        }
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
