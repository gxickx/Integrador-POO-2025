package modelos;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "cicloCine")
public class CicloCine extends Evento {    
    @Column()
    private Pelicula[] peliculas;

    @Column(nullable = false)
    private boolean tieneCharlas;

    public CicloCine(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo, boolean tieneCharlas) {
        super(nombre, fechaInicio, duracion, estado, requiereInscripcion, tieneCupo, cupoMaximo);
        
        this.tieneCharlas = tieneCharlas;
    }

    
    public boolean isTieneCharlas() {
        return tieneCharlas;
    }

    public void agregarPelicula(Pelicula p){
        if (p == null) {
            throw new IllegalArgumentException("La película no puede ser nula.");
        }
    }

    
}
