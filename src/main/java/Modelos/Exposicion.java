package modelos;

import java.util.Date;

public class Exposicion extends Evento {
    private TipoArte tipoArte;

    public Exposicion(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, TipoArte tipoArte) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);
        
        if (tipoArte == null) {
            throw new IllegalArgumentException("El tipo de arte no puede ser nulo.");
        }
        
        this.tipoArte = tipoArte;
    }

    public TipoArte getTipoArte() {
        return tipoArte;
    }
    
}
