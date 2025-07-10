package modelos;

public class ParticipacionId {
    //aca va la clase embebida

    /*
    * cosa de chatgpt
    * @Embeddable
public class ParticipacionId implements Serializable {
    private String personaDni;
    private UUID eventoId;

    public ParticipacionId() {}

    public ParticipacionId(String personaDni, UUID eventoId) {
        this.personaDni = personaDni;
        this.eventoId = eventoId;
    }

    // Getters y Setters
    public String getPersonaDni() {
        return personaDni;
    }

    public UUID getEventoId() {
        return eventoId;
    }

    // equals y hashCode (requeridos)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipacionId that)) return false;
        return Objects.equals(personaDni, that.personaDni) &&
               Objects.equals(eventoId, that.eventoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personaDni, eventoId);
    }
    * */
}
