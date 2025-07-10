package controladores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelos.Persona;
import servicio.Servicio;
import org.example.Main;

public class PersonasController {
    @FXML
    private Button btnAlta;

    @FXML
    private Button btnBaja;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnModificacion;

    @FXML
    private TableColumn<Persona, String> columnaApellido;

    @FXML
    private TableColumn<Persona, String> columnaCorreo;

    @FXML
    private TableColumn<Persona, String> columnaDni;

    @FXML
    private TableColumn<Persona, String> columnaNombre;

    @FXML
    private TableColumn<Persona, String> columnaTel;

    @FXML
    private TableView<Persona> tablaPersonas;

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
        columnaDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnaTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));


        txtApellido.setDisable(true);
        txtCorreo.setDisable(true);
        txtDni.setDisable(true);
        txtNombre.setDisable(true);
        txtTelefono.setDisable(true);
        btnConfirmar.setDisable(true);
        
        tablaPersonas.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());
        try {
            tablaPersonas.getItems().addAll(servicio.listarPersonas());
        } catch (Exception e) {
            throw e;
        }  
    }

    @FXML
    void onClickActivaAltaPersona(ActionEvent event) {
        if(btnAlta.getText()=="Cancelar"){
            bloquearBotones();
            limpiar();
        }
        else{
            desbloquearBotones();
            btnModificacion.setDisable(true);
            btnAlta.setText("Cancelar");
        }
    }

    public void bloquearBotones(){
        txtApellido.setDisable(true);
        txtCorreo.setDisable(true);
        txtDni.setDisable(true);
        txtNombre.setDisable(true);
        txtTelefono.setDisable(true);
        btnConfirmar.setDisable(true);
        btnBaja.setDisable(false);
        btnModificacion.setDisable(false);
        btnAlta.setText("Alta");
        btnModificacion.setText("Modificación");
    }

    public void desbloquearBotones(){
        txtApellido.setDisable(false);
        txtCorreo.setDisable(false);
        txtDni.setDisable(false);
        txtNombre.setDisable(false);
        txtTelefono.setDisable(false);
        btnConfirmar.setDisable(false);
        btnBaja.setDisable(true);
    }

    @FXML
    void onClickAltaPersona(ActionEvent event) {
        var item = tablaPersonas.getSelectionModel().getSelectedItem();
        Persona persona = (Persona) item;
        //limpiar();
        try{
            if (item != null && item instanceof Persona) {
            //Persona persona = (Persona) item;
            txtApellido.setText(persona.getApellido());
            txtCorreo.setText(persona.getCorreo());
            txtDni.setText(persona.getDni());
            txtNombre.setText(persona.getNombre());
            txtTelefono.setText(persona.getTelefono());
            } 

            if (btnAlta.getText()=="Cancelar") 
            {
                servicio.insertarPersona(txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(),
                       txtDni.getText(), txtTelefono.getText());
            }
            else if (btnModificacion.getText()=="Cancelar"){
                servicio.modificarPersona(persona.getDni(), persona.getNombre(), persona.getApellido(),
                       persona.getCorreo(), persona.getTelefono());
                btnAlta.setDisable(false);
            }
            bloquearBotones();
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

        tablaPersonas.getItems().clear();
        try {
            tablaPersonas.getItems().addAll(servicio.listarPersonas());
        } catch (Exception e) {
            throw e;
        }
        tablaPersonas.getSelectionModel().clearSelection();
    }

    @FXML
    void onClickBajaPersona(ActionEvent event) {
        var item = tablaPersonas.getSelectionModel().getSelectedItem();
        Persona persona = (Persona)item;
        if (persona != null) {
            try {
                servicio.eliminarPersona(persona.getDni());
            } catch (Exception e) {
                throw e;
            }
            limpiar();
        }
    }

    @FXML
    void onClickModifcarPersona(ActionEvent event) {
        var item = tablaPersonas.getSelectionModel().getSelectedItem();
        Persona persona = (Persona)item;
        if(btnModificacion.getText()=="Cancelar"){
            btnModificacion.setText("Modificación");
            bloquearBotones();
            btnAlta.setDisable(false);
            limpiar();
        }
        else if (persona != null) {
            txtDni.setText(persona.getDni());
            txtApellido.setText(persona.getApellido());
            txtCorreo.setText(persona.getCorreo());
            txtNombre.setText(persona.getNombre());
            txtTelefono.setText(persona.getTelefono());
            desbloquearBotones();
            
            btnAlta.setDisable(true);
            btnBaja.setDisable(true);
            btnModificacion.setText("Cancelar");
        }
}

    private void cargarDatos() {
            var item = tablaPersonas.getSelectionModel().getSelectedItem();
            Persona persona = (Persona)item;
            if (persona != null) {
                txtDni.setText(persona.getDni());
                txtApellido.setText(persona.getApellido());
                txtCorreo.setText(persona.getCorreo());
                txtNombre.setText(persona.getNombre());
                txtTelefono.setText(persona.getTelefono());
            }
        }
}


