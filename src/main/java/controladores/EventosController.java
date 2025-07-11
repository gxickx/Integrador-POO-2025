package controladores;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import servicio.Servicio;
import modelos.*;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class EventosController {
    @FXML
    private Button btnIrCalendario;

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

        comboEstadoEvento.getItems().setAll(EstadoEvento.values());
        comboEstadoEvento.setValue(EstadoEvento.PLANIFICACION);
        
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

        if (tipo==null){
            return;
        }

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

            comboEstadoEvento.getItems().setAll(EstadoEvento.values());
            comboEstadoEvento.setValue(EstadoEvento.PLANIFICACION);

            if (btnAlta.getText().equals("Cancelar")) {
                bloquearBotones();
            } else {
                desbloquearBotones();
                btnModificacion.setDisable(true);
                btnAlta.setText("Cancelar");
                comboEstadoEvento.setDisable(true);  // bloqueado al iniciar alta
            }
        }


    @FXML
    void onClickCheckTieneCupo(ActionEvent event) {
        boolean tieneCupo = checkTieneCupo.isSelected();
            txtCupoMax.setDisable(!tieneCupo); // habilita o deshabilita el campo
    }

    private int obtenerCupoMaximo() {
        if (checkTieneCupo.isSelected()) {
            try {
                return Integer.parseInt(txtCupoMax.getText());
            } catch (NumberFormatException e) {
                Alerta.mostrarAlerta("Error", "Debe ingresar un cupo válido.");
            }
        }
        return 0;
    }



    @FXML
    void onClickAltaEvento(ActionEvent event) {
        var item = tablaEventos.getSelectionModel().getSelectedItem();
        Evento evento = (Evento) item;

        try {
            // MODIFICACIÓN
            if (item != null && btnModificacion.getText().equals("Cancelar")) {
                servicio.modificarEvento(evento.getIdEvento(), comboEstadoEvento.getValue());
                btnAlta.setDisable(false);
                comboEstadoEvento.setDisable(false);
            }

            // ALTA
            if (btnAlta.getText().equals("Cancelar")) {
                comboEstadoEvento.setValue(EstadoEvento.PLANIFICACION);
                String nombre = txtNombreEvento.getText();
                var fechaInicioDate = dateInicio.getValue();
                var fechaFinDate = dateFin.getValue();
                var estado = comboEstadoEvento.getValue();
                var tipoEvento = comboTipoEvento.getValue();
                var requiereInscripcion = checkTieneInscripcion.isSelected();
                var tieneCupo = checkTieneCupo.isSelected();

                onClickCheckTieneCupo(event);
                int cupoMaximo = obtenerCupoMaximo();

                int duracion = (int) ChronoUnit.DAYS.between(fechaInicioDate, fechaFinDate) + 1;

                //convertir fecha inicio y fecha fin a Date pq el datePicker nos da un tipo de LocalDate, lo que genera errores en las clases
                Date fechaInicio = Date.from(fechaInicioDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fechaFin = Date.from(fechaFinDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (nombre.isBlank() ||  estado == null || tipoEvento == null) {
                    Alerta.mostrarAlerta("Error", "Debe completar todos los campos obligatorios.");
                    return;
                }

                if (fechaFinDate.isBefore(fechaInicioDate)) {
                    Alerta.mostrarAlerta("Error", "La fecha de fin no puede ser anterior a la de inicio.");
                    return;
                }

                Evento nuevoEvento = null;

                switch (tipoEvento) {
                    case "Concierto" -> {
                        TipoEntrada entrada = (TipoEntrada) comboOpcional.getValue();
                        if (entrada == null) {
                            Alerta.mostrarAlerta("Error", "Debe seleccionar un tipo de entrada.");
                            return;
                        }
                        nuevoEvento = new Concierto(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo, entrada);
                    }
                    case "Exposición" -> {
                        TipoArte arte = (TipoArte) comboOpcional.getValue();
                        if (arte == null) {
                            Alerta.mostrarAlerta("Error", "Debe seleccionar un tipo de arte.");
                            return;
                        }
                        nuevoEvento = new Exposicion(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo, arte);
                    }
                    case "Taller" -> {
                        Modalidad modalidad = (Modalidad) comboOpcional.getValue();
                        if (modalidad == null) {
                            Alerta.mostrarAlerta("Error", "Debe seleccionar una modalidad.");
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
                            Alerta.mostrarAlerta("Error", "Debe ingresar la cantidad de stands.");
                            return;
                        }
                        int cantStands;
                        try {
                            cantStands = Integer.parseInt(cantTexto);
                        } catch (NumberFormatException e) {
                            Alerta.mostrarAlerta("Error", "La cantidad de stands debe ser un número.");
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
            Alerta.mostrarAlerta("Error", "Ocurrió un error al dar de alta el evento.");
            throw e;
        }
    }

    @FXML
    void onClickBajaEvento(ActionEvent event) {
        var item = tablaEventos.getSelectionModel().getSelectedItem();
        Evento evento = (Evento)item;
        if (evento != null) {
            try {
                servicio.eliminarEvento(evento.getIdEvento());
            } catch (Exception e) {
                throw e;
            }
            limpiar();
        }
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
            bloquearBotones();

            comboEstadoEvento.setValue(evento.getEstado());
            comboEstadoEvento.setDisable(false);

            btnAlta.setDisable(true);
            btnBaja.setDisable(true);
            btnModificacion.setDisable(false);
            btnConfirmar.setDisable(false);
            btnModificacion.setText("Cancelar");
        }
    }


    @FXML
    void onClickVolverInicio(ActionEvent event) throws IOException {
        Main.setRoot("PantallaInicio");
    }

    @FXML
    private void onClickIrCalendario(ActionEvent event) throws IOException {
        Main.setRoot("Calendario");
    }

    @FXML
    private void cargarDatos(){
        var item = tablaEventos.getSelectionModel().getSelectedItem();
        if ((Evento) item != null) {
            txtNombreEvento.setText(((Evento) item).getNombre());

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
        checkTieneCupo.setDisable(true);
        checkTieneInscripcion.setDisable(true);
        txtNombreEvento.setDisable(true);

        //campos opcionales
        comboOpcional.setVisible(false);
        textOpcional.setVisible(false);
        checkAlAireLibre.setVisible(false);
        checkTieneCharlas.setVisible(false);
        labelCombo.setVisible(false);
        labelTextOpcional.setVisible(false);

        btnModificacion.setDisable(false);
        btnAlta.setText("Alta");
        btnModificacion.setText("Modificación");
        btnConfirmar.setDisable(true);
        txtCupoMax.setDisable(true);
        btnBaja.setDisable(false);
    }

    @FXML
    public void desbloquearBotones() {
        txtNombreEvento.setDisable(false);
        dateFin.setDisable(false);
        dateInicio.setDisable(false);
        comboTipoEvento.setDisable(false);
        checkTieneCupo.setDisable(false);
        checkTieneInscripcion.setDisable(false);

        labelCombo.setVisible(true);
        labelTextOpcional.setVisible(true);
        btnConfirmar.setDisable(false);
        btnBaja.setDisable(true);
    }

    @FXML
    public void limpiar(){
        txtNombreEvento.setText("");
        dateFin.setValue(null);
        dateInicio.setValue(null);
        //comboEstadoEvento.setValue(null);
        //comboEstadoEvento.setValue(EstadoEvento.PLANIFICACION);

        comboOpcional.setValue(null);
        comboTipoEvento.setValue(null);
        textOpcional.setText("");
        txtCupoMax.setText("");
        checkTieneCupo.setSelected(false);
        txtCupoMax.setDisable(true);
        checkTieneInscripcion.setSelected(false);
        checkAlAireLibre.setSelected(false);
        checkTieneCharlas.setSelected(false);
        labelCombo.setText("");


        comboEstadoEvento.setValue(EstadoEvento.PLANIFICACION);

        tablaEventos.getItems().clear();
        try {
            tablaEventos.getItems().addAll(servicio.listarEventos());
        } catch (Exception e) {
            throw e;
        }
        tablaEventos.getSelectionModel().clearSelection();
    }
}
