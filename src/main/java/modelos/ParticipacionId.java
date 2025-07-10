package modelos;
import jakarta.persistence.*;
import java.util.UUID;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ParticipacionId implements Serializable {
    private String personaDni;
    private UUID eventoId;

    //JPA necesita un constructor vacio
    public ParticipacionId() {}

    public ParticipacionId(String personaDni, UUID eventoId) {
        this.personaDni = personaDni;
        this.eventoId = eventoId;
    }

    public String getPersonaDni() {
        return personaDni;
    }

    public UUID getEventoId() {
        return eventoId;
    }

    // metodos necesarios para que JPA funcione correctamente
    // equals para evitar duplicados en un evento, por ejemplo
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipacionId that)) return false;
        return Objects.equals(personaDni, that.personaDni) &&
               Objects.equals(eventoId, that.eventoId);
    }

    // hashcode se utiliza para indexar los datos
    @Override
    public int hashCode() {
        return Objects.hash(personaDni, eventoId);
    }

}
