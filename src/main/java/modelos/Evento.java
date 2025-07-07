package modelos;
import java.util.UUID;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @Column(columnDefinition = "UUID")
    private UUID idEnvento = UUID.randomUUID();

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private Date fechaInicio;

    @Column(length = 100, nullable = false)
    private int duracion;

    @Column(length = 20, nullable = false)
    private EstadoEvento estado;

    @Column(nullable = false)
    private boolean requiereInscripcion = false;

    @Column(nullable = false)
    private boolean tieneCupo = false;

    @Column(nullable = false)
    private int cupoMaximo;


    protected Evento() {

    }
    public Evento(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo) {
        if(nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede ser nulo o vacío.");
        }
        if(fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula.");
        }
        if(duracion <= 0) {
            throw new IllegalArgumentException("La duración del evento debe ser mayor a cero.");
        }
        
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estado = estado;
        this.requiereInscripcion = requiereInscripcion;
        this.tieneCupo = tieneCupo;
        this.cupoMaximo = cupoMaximo;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public boolean isRequiereInscripcion() {
        return requiereInscripcion;
    }

    public boolean isTieneCupo() {
        return tieneCupo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }


}
