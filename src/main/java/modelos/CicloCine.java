package modelos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "cicloCine")
public class CicloCine extends Evento {

    @Column(nullable = false)
    private boolean tieneCharlas;

    public CicloCine() {
        super();
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cicloCine_id")

    private List<Pelicula> peliculas = new ArrayList<>();


    public CicloCine(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, boolean tieneCharlas) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);
        this.tieneCharlas = tieneCharlas;
    }

    public boolean isTieneCharlas() {
        return tieneCharlas;
    }

    public void setTieneCharlas(boolean tieneCharlas) {
        this.tieneCharlas = tieneCharlas;
    }

    public void agregarPelicula(Pelicula p){
        if (p == null) {
            throw new IllegalArgumentException("La película no puede ser nula.");
        } else {
            peliculas.add(p);
        }
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

}
