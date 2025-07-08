package controladores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import persistencia.Persistencia;
import modelos.Persona;

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

    @FXML
    void onClickAltaPersona(ActionEvent event) {
        /* MUY DUDOSO
        var selectedItem = idTablaPersonas.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem instanceof Persona) {
            Persona persona = (Persona) selectedItem;
            txtApellido.setText(persona.getApellido());
            txtCorreo.setText(persona.getCorreo());
            txtDni.setText(persona.getDni());
            txtNombre.setText(persona.getNombre());
            txtTelefono.setText(persona.getTelefono());
        }*/
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


