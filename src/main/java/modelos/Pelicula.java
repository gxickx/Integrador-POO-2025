package modelos;

public class Pelicula {
    private CicloCine cicloCine;
    private String titulo;

    public Pelicula(CicloCine cicloCine, String titulo, int orden) {
        if (cicloCine == null) {
            throw new IllegalArgumentException("El ciclo de cine no puede ser nulo.");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("El título de la película no puede ser nulo o vacío.");
        }
        this.titulo = titulo;
    }


    public void setCicloCine(CicloCine cicloCine) {
        if (cicloCine == null) {
            throw new IllegalArgumentException("El ciclo de cine no puede ser nulo.");
        }
        this.cicloCine = cicloCine;
    }
}
