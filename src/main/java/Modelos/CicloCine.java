package Modelos;

import java.util.Date;

public class CicloCine extends Evento {
    private Pelicula[] peliculas;
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
