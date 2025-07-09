package modelos;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID idPelicula = UUID.randomUUID();

    @ManyToOne(optional = false)
    @JoinColumn(name = "idEvento", nullable = false)
    private CicloCine cicloCine;

    @Column(length = 100, nullable = false)
    private String titulo;


    public Pelicula() {}

    public Pelicula(CicloCine cicloCine, String titulo) {
        setCicloCine(cicloCine);
        setTitulo(titulo);
    }

    public void setCicloCine(CicloCine cicloCine) {
        if (cicloCine == null) throw new IllegalArgumentException("El ciclo de cine no puede ser nulo.");
        this.cicloCine = cicloCine;
    }

    public CicloCine getCicloCine() {
        return cicloCine;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) throw new IllegalArgumentException("El título no puede ser nulo o vacío.");
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public UUID getId() {
        return idPelicula;
    }
}
