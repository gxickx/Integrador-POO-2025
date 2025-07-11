package org.example;

import java.io.IOException;

import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistencia.Persistencia;
import servicio.Servicio;

public class Main extends Application{
/*   public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("integradorPOO2025");
        Persistencia persistencia = new Persistencia(emf);

        try {
            persistencia.iniciarTransaccion();
            Persona persona = new Persona("Lionel", "Messi", "messireal@mail.com", "10101010", "5450500");
            persistencia.insertar(persona);
            persistencia.confirmarTransaccion();
            System.out.println("Persona insertada correctamente.");
        } catch (Exception e) {
            persistencia.descartarTransaccion();
            e.printStackTrace();
        }
    }*/


    private static Scene scene;
    private static Servicio servicio;

    @Override
    public void start(Stage stage) throws IOException {
        // creación del manejador de la conexión
        var emf = Persistence.createEntityManagerFactory("integradorPOO2025");
        // crea el servicio y repositorio
        servicio = new Servicio(new Persistencia(emf));

        // carga la escena principal
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/PantallaInicio.fxml"));
        scene = new Scene(fxmlLoader.load(), 925, 610);
        stage.setScene(scene);
        stage.show();
    }

    public static Servicio getServicio() {
        return servicio;
    }


    public static FXMLLoader setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/" + fxml + ".fxml"));
        scene.setRoot(fxmlLoader.load());
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }
}