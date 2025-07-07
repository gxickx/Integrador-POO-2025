package modelos;

import java.util.Date;

public class Concierto extends Evento {
    private TipoEntrada tipoEntrada;

    public Concierto(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, TipoEntrada tipoEntrada) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);
        
        if (tipoEntrada == null) {
            throw new IllegalArgumentException("El tipo de entrada no puede ser nulo.");
        }
        
        this.tipoEntrada = tipoEntrada;
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }
}
