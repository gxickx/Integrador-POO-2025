package controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import servicio.Servicio;
import modelos.EstadoEvento;
import modelos.Evento;
import java.util.Date;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.example.Main;

public class CalendarioController {
    @FXML
    private Button btnMesAnterior;

    @FXML
    private Button btnMesSiguiente;

    @FXML
    private Pane calendarioPanel;

    @FXML
    private Button btnVolver;

    @FXML
    private Servicio servicio;

    private final int CELDA_ANCHO = 120;
    private final int CELDA_ALTO = 75;
    private final int ESPACIADO_X = 5;
    private final int ESPACIADO_Y = 5;
    private final int INICIO_X = 0;
    private final int INICIO_Y = 0;

    private List<Evento> eventos = new ArrayList<>();
    private int anioActual = 2025;
    private int mesActual = 7; 

    
    @FXML
    private Label lbMes;


    @FXML
    public void initialize() {
        try {
            this.servicio = Main.getServicio(); 
            this.eventos = servicio.listarEventosCalendario();
            mostrarCalendarioDelMes(2025, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void mostrarCalendarioDelMes(int year, int month) {
        calendarioPanel.getChildren().clear();
        actualizarEtiquetaMes();

        LocalDate primerDia = LocalDate.of(year, month, 1);
        int diasEnMes = primerDia.lengthOfMonth();
        int primerDiaSemana = primerDia.getDayOfWeek().getValue() % 7; // 0=Domingo

        int diaActual = 1;
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 7; col++) {
                int index = fila * 7 + col;
                VBox celda = new VBox();
                celda.setLayoutX(INICIO_X + col * (CELDA_ANCHO + ESPACIADO_X));
                celda.setLayoutY(INICIO_Y + fila * (CELDA_ALTO + ESPACIADO_Y));
                celda.setPrefSize(CELDA_ANCHO, CELDA_ALTO);
                celda.setStyle("-fx-border-color: #ccc; -fx-background-color: white; -fx-padding: 5;");

                if (index >= primerDiaSemana && diaActual <= diasEnMes) {
                    Label labelDia = new Label(String.valueOf(diaActual));
                    labelDia.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e8b57;");
                    celda.getChildren().add(labelDia);

                    LocalDate fechaActual = LocalDate.of(year, month, diaActual);

                    for (Evento e : eventos) {
                        if (e.getFechaInicio() == null) continue;
                        LocalDate fechaEvento = convertirADateLocal(e.getFechaInicio());
                        if (fechaEvento != null && fechaEvento.equals(fechaActual)) {
                            Label eventoLabel = new Label(e.getNombre());
                            if(e.getEstado()==EstadoEvento.CONFIRMADO){
                                eventoLabel.setStyle("-fx-background-color: #5865d7ff; -fx-text-fill: white; -fx-padding: 2 4 2 4; -fx-background-radius: 4;");
                            }
                            else if(e.getEstado()==EstadoEvento.EN_EJECUCION){
                                eventoLabel.setStyle("-fx-background-color: #2e8b57; -fx-text-fill: white; -fx-padding: 2 4 2 4; -fx-background-radius: 4;");
                            }
                            else if(e.getEstado()==EstadoEvento.FINALIZADO){
                                eventoLabel.setStyle("-fx-background-color: #8b372eff; -fx-text-fill: white; -fx-padding: 2 4 2 4; -fx-background-radius: 4;");
                            }
                            celda.getChildren().add(eventoLabel);
                        }
                    }

                    diaActual++;
                }

                calendarioPanel.getChildren().add(celda);
            }
        }
    }

    @FXML
    void onClickMesAnterior(ActionEvent event) {
        mesActual--;
        if (mesActual < 1) {
            mesActual = 12;
            anioActual--;
        }
    mostrarCalendarioDelMes(anioActual, mesActual);
    }


    @FXML
    void onClickMesSiguiente(ActionEvent event) {
        mesActual++;
        if (mesActual > 12) {
            mesActual = 1;
            anioActual++;
        }
        mostrarCalendarioDelMes(anioActual, mesActual);
    }

    private LocalDate convertirADateLocal(Date date) {
        if (date == null) return null;
        return Instant.ofEpochMilli(date.getTime())
                      .atZone(ZoneId.systemDefault())
                      .toLocalDate();
    }

    private void actualizarEtiquetaMes() {
    String nombreMes = LocalDate.of(anioActual, mesActual, 1)
                                .getMonth()
                                .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es"));
    lbMes.setText("Calendario de Eventos: " + nombreMes + " " + anioActual);
}

    @FXML
    void onClickVolverInicio(ActionEvent event) throws IOException {
        Main.setRoot("PantallaInicio");
    }
}

