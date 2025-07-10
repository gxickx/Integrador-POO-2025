package modelos;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("CONCIERTO")
@Table(name = "concierto")

public class Concierto extends Evento {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEntrada tipoEntrada;

    public Concierto() {
        super();
    }

    public Concierto(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, TipoEntrada tipoEntrada) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);
        setTipoEntrada(tipoEntrada);
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(TipoEntrada tipoEntrada) {
        if (tipoEntrada == null) {
            throw new IllegalArgumentException("El tipo de entrada no puede ser nulo.");
        }
        this.tipoEntrada = tipoEntrada;
    }
}
