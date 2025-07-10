package modelos;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table (name = "exposicion")
public class Exposicion extends Evento {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoArte tipoArte;

    public Exposicion() {
        super();
    }

    public Exposicion(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, TipoArte tipoArte) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);
        setTipoArte(tipoArte);
    }

    public TipoArte getTipoArte() {
        return tipoArte;
    }

    public void setTipoArte(TipoArte tipoArte) {
        if (tipoArte == null) {
            throw new IllegalArgumentException("El tipo de arte no puede ser nulo.");
        }
        this.tipoArte = tipoArte;
    }
    
}
