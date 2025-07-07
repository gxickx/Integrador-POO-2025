package modelos;

import java.util.Date;

public class Taller extends Evento {
    private Modalidad modalidad;

    public Taller(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, Modalidad modalidad) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);
    
        if (modalidad == null) {
            throw new IllegalArgumentException("La modalidad no puede ser nula.");
        }

        this.modalidad = modalidad;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }
}
