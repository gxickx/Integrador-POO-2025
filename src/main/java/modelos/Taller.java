package modelos;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("TALLER")
@Table(name = "taller")
public class Taller extends Evento {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modalidad modalidad;

    public Taller() {
        super();
    }

    public Taller(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, Modalidad modalidad) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);
    
       setModalidad(modalidad);
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        if (modalidad == null) {
            throw new IllegalArgumentException("La modalidad no puede ser nula.");
        }
        this.modalidad = modalidad;
    }
}
