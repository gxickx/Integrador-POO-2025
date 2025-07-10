package modelos;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("FERIA")
@Table (name = "feria")
public class Feria extends Evento {

    @Column(nullable = false)
    private int cantidadStands;

    @Column(nullable = false)
    private boolean esAlAireLibre;

    public Feria() {
        super();
    }

    public Feria(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, int cantidadStands, boolean esAlAireLibre) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);

        setCantidadStands(cantidadStands);
        setEsAlAireLibre(esAlAireLibre);

    }
    public int getCantidadStands() {
        return cantidadStands;
    }

    public void setCantidadStands(int cantidadStands) {
        if (cantidadStands <= 0) {
            throw new IllegalArgumentException("La cantidad de stands debe ser mayor a cero.");
        }
        this.cantidadStands = cantidadStands;
    }

    public boolean isEsAlAireLibre() {
        return esAlAireLibre;
    }

    public void setEsAlAireLibre(boolean esAlAireLibre) {
        this.esAlAireLibre = esAlAireLibre;
    }
}
