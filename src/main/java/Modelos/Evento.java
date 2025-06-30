package Modelos;

import java.util.Date;

public abstract class Evento {
    private String nombre;
    private Date fechaInicio;
    private int duracion; 
    private EstadoEvento estado;
    private boolean requiereInscripcion;
    private boolean tieneCupo;
    private int cupoMaximo;

    public Evento(String nombre, Date fechaInicio, int duracion, EstadoEvento estado, boolean requiereInscripcion, boolean tieneCupo, int cupoMaximo) {
        if(nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede ser nulo o vacío.");
        }
        if(fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula.");
        }
        if(duracion <= 0) {
            throw new IllegalArgumentException("La duración del evento debe ser mayor a cero.");
        }
        
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estado = estado;
        this.requiereInscripcion = requiereInscripcion;
        this.tieneCupo = tieneCupo;
        this.cupoMaximo = cupoMaximo;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public boolean isRequiereInscripcion() {
        return requiereInscripcion;
    }

    public boolean isTieneCupo() {
        return tieneCupo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }


}
