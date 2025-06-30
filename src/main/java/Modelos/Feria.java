package Modelos;

import java.util.Date;

public class Feria extends Evento {
    private int cantidadStands;
    private boolean esAlAireLibre;


    public Feria(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, int cantidadStands, boolean esAlAireLibre) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);
        
        if (cantidadStands <= 0) {
            throw new IllegalArgumentException("La cantidad de stands debe ser mayor a cero.");
        }
        
        this.cantidadStands = cantidadStands;
        this.esAlAireLibre = esAlAireLibre;
    }

    public int getCantidadStands() {
        return cantidadStands;
    }

    public boolean isEsAlAireLibre() {
        return esAlAireLibre;
    }
}
