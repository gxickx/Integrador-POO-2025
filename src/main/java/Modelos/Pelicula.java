package Modelos;

public class Pelicula {
    private CicloCine cicloCine;
    private String titulo;
    private int orden;

    public Pelicula(CicloCine cicloCine, String titulo, int orden) {
        if (cicloCine == null) {
            throw new IllegalArgumentException("El ciclo de cine no puede ser nulo.");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("El título de la película no puede ser nulo o vacío.");
        }
        if (orden < 1) {
            throw new IllegalArgumentException("El orden debe ser un número positivo.");
        }

        this.titulo = titulo;
        this.orden = orden;
    }


    public void setCicloCine(CicloCine cicloCine) {
        if (cicloCine == null) {
            throw new IllegalArgumentException("El ciclo de cine no puede ser nulo.");
        }
        this.cicloCine = cicloCine;
    }
}
