package modelos;
import jakarta.persistence.*;


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

    @Column(nullable = false)
    private boolean baja = false;

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

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    public void setPersona(Persona persona) {
        this.persona = persona;
    }


    public RolPersona getRol() {
        return rol;
    }

    public void setRol(RolPersona rol){
        this.rol = rol;
    }

    public boolean isBaja() {
    return baja;
    }

    public void setBaja() {
        this.baja = true;
    }

    public void setBajaFalse(){
        this.baja = false;
    }

    // No se si setters sean necesarios, podemos dejar así para hacerlo mas sencillo


}
