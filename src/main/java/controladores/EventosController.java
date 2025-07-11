package controladores;

import java.io.IOException;

import org.example.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableColumn<?, ?> columnaCupoMax;

    @FXML
    private TableColumn<?, ?> columnaCuposVacantes;

    @FXML
    private TableColumn<?, ?> columnaDuracion;

    @FXML
    private TableColumn<?, ?> columnaEstado;

    @FXML
    private TableColumn<?, ?> columnaFechaInicio;

    @FXML
    private TableColumn<?, ?> columnaID;

    @FXML
    private TableColumn<?, ?> columnaNombre;

    @FXML
    private ComboBox<?> comboEstadoEvento;

    @FXML
    private ComboBox<?> comboOpcional;

    @FXML
    private ComboBox<?> comboTipoEvento;

    @FXML
    private DatePicker dateFin;

    @FXML
    private DatePicker dateInicio;

    @FXML
    private Label labelCombo;

    @FXML
    private Label labelTextOpcional;

    @FXML
    private TableView<?> tablaPersonas;

    @FXML
    private TextField textOpcional;

    @FXML
    private TextField txtNombreEvento;

    @FXML
    void onClickActivaAltaPersona(ActionEvent event) {

    }

    @FXML
    void onClickAltaPersona(ActionEvent event) {

    }

    @FXML
    void onClickBajaPersona(ActionEvent event) {

    }

    @FXML
    void onClickModifcarPersona(ActionEvent event) {

    }

    @FXML
    void onClickVolverInicio(ActionEvent event) throws IOException {
        Main.setRoot("PantallaInicio");
    }

}
