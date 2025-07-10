package modelos;
import jakarta.persistence.*;
import java.util.UUID;
import modelos.Persona;

//aca va la clase entidad
@Entity
@Table(name = "participacion")
public class Participacion {

    @EmbeddedId
    private ParticipacionId id = new ParticipacionId();

    @ManyToOne
    @MapsId("personaDni")
    @JoinColumn(name = "dni", nullable = false)
    private Persona persona;

    @ManyToOne
    @MapsId("eventoId")  // mapea la id de la clase embebida ParticipacionId
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolPersona rol;

    public Participacion() {}

    public Participacion(Persona persona, Evento evento, RolPersona rol) {
        this.persona = persona;
        this.evento = evento;
        this.rol = rol;
        this.id = new ParticipacionId(persona.getDni(), evento.getIdEvento());
    }

    // Getters
    public ParticipacionId getId() {
        return id;
    }

    public Persona getPersona() {
        return persona;
    }

    public Evento getEvento() {
        return evento;
    }

    public RolPersona getRol() {
        return rol;
    }

    // No se si setters sean necesarios, podemos dejar así para hacerlo mas sencillo


}
