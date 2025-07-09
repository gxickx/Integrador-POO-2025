package controladores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ListView;
import persistencia.Persistencia;
import modelos.Persona;
import servicio.Servicio;
import org.example.Main;

public class PersonasController {
    @FXML
    private ListView<?> idTablaPersonas;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    private Servicio servicio;

    @FXML
    private void initialize() {
        servicio = Main.getServicio();
        /* ACÁ Tendríamos que poner id a las columnas y filas de la tabla que vamos a mostrar la carga de personas 

        dni.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        
        algo así como lo de arriba
        estaría bueno hacer como el profe y gestionar modificación junto a los inserts
        
        y esto de abajo es mas o menos lo que usa el profe para listar en pantalla. hay que arreglarlo un poco:

        idTablaPersonas.getSelectionModel().selectedItemProperty().addListener(e -> onClickAltaPersona());
        try {
            idTablaPersonas.getItems().addAll(servicio.listarProveedores());
        } catch (Exception e) {
            Alerta.mostrarAlerta(AlertType.ERROR, "Error", "Error al iniciar", e.getMessage());
        }*/
    }

    @FXML
    void onClickAltaPersona(ActionEvent event) {
        var selectedItem = idTablaPersonas.getSelectionModel().getSelectedItem();
        try{
            if (selectedItem != null && selectedItem instanceof Persona) {
            Persona persona = (Persona) selectedItem;
            txtApellido.setText(persona.getApellido());
            txtCorreo.setText(persona.getCorreo());
            txtDni.setText(persona.getDni());
            txtNombre.setText(persona.getNombre());
            txtTelefono.setText(persona.getTelefono());
            } else {
                servicio.insertarPersona(txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(),
                       txtDni.getText(), txtTelefono.getText());
                }
                limpiar();
        } catch (Exception e) {
            throw e;
        }
    }

    private void limpiar() {
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtNombre.setText("");
        txtDni.setText("");

        idTablaPersonas.getItems().clear();

    }

}


