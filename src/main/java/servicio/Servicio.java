package servicio;

import modelos.EstadoEvento;
import persistencia.Persistencia;
import modelos.Persona;
import modelos.Evento;
import modelos.Participacion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Servicio {
    private Persistencia persistencia;

    public Servicio(Persistencia p) {
        this.persistencia = p;
    }

// Servicio para la clase Persona
    public void insertarPersona(String nombre, String apellido, String correo, String dni, String telefono) {
        try {
            this.persistencia.iniciarTransaccion();
            Persona persona = new Persona(nombre, apellido, correo, dni, telefono);
            this.persistencia.insertar(persona);
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            throw e;
        }
    }

    public void eliminarPersona(String dni) {
        try {
            this.persistencia.iniciarTransaccion();
            var persona = this.persistencia.buscar(Persona.class, dni);
            if (persona != null && persona.isBaja() == false) {

                persona.setBaja();
                this.persistencia.modificar(persona);
                this.persistencia.confirmarTransaccion();

            } else {
                this.persistencia.descartarTransaccion();
            }
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            throw e;
        }
    }

    public void modificarPersona(String dni, String nombre, String apellido, String correo, String telefono) {
        try{
            this.persistencia.iniciarTransaccion();
            var persona = this.persistencia.buscar(Persona.class, dni);
            if(persona!=null){
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setCorreo(correo);
                persona.setTelefono(telefono);
                this.persistencia.modificar(persona);
                this.persistencia.confirmarTransaccion();
            } else {
                this.persistencia.descartarTransaccion();
            }
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            throw e;
        }

    }
    
    public List<Persona> listarPersonas() {
        var personas = this.persistencia.buscarTodos(Persona.class);
        var listado = new ArrayList<Persona>();
        for (var persona : personas) {
            if (persona.isBaja() == false) {
                listado.add(persona);
            }
        }
        return listado;
    }

    // Servicio para la clase Evento

    public List<Evento> listarEventosConfirmados() {
        var eventos = this.persistencia.buscarTodos(Evento.class);
        var confirmados = new ArrayList<Evento>();

        for (var evento : eventos) {
            if (evento.getEstado() == EstadoEvento.CONFIRMADO) {
                confirmados.add(evento);
            }
        }

        return confirmados;
    }

    public List<Evento> listarEventosCalendario() {
        var eventos = this.persistencia.buscarTodos(Evento.class);
        var confirmadoOMas = new ArrayList<Evento>();

        for (var evento : eventos) {
            if (evento.getEstado() != null && evento.getEstado() != EstadoEvento.PLANIFICACION) {
                confirmadoOMas.add(evento);
            }
        }

        return confirmadoOMas;
    }

    public List<Evento> listarEventos() {
        try{
            var eventos = this.persistencia.buscarTodos(Evento.class);
            var listado = new ArrayList<Evento>();
            for (var evento : eventos) {
                if (evento.isBaja() == false) {
                    listado.add(evento);
                }
            }
        return listado;
        } catch (Exception e) {
            throw new RuntimeException("No se pudieron listar los eventos", e);
        }

    }

    public void modificarEvento(UUID idEvento, EstadoEvento unEstado){
        try{
            this.persistencia.iniciarTransaccion();
            Evento evento = this.persistencia.buscar(Evento.class, idEvento);
            if(evento!=null){
                evento.setEstado(unEstado);
                this.persistencia.modificar(evento);
                this.persistencia.confirmarTransaccion();
            } else {
                this.persistencia.descartarTransaccion();
            }
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            throw e;
        }
    }

    public Evento buscarEvento(UUID uuid){
        return this.persistencia.buscar(Evento.class, uuid);
    }

    public void insertarEvento(Evento evento) {
        try {
            this.persistencia.iniciarTransaccion();
            this.persistencia.insertar(evento);
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            throw e;
        }
    }


    public void eliminarEvento(UUID idEvento) {
    try {
        this.persistencia.iniciarTransaccion();
        var evento = this.persistencia.buscar(Evento.class, idEvento);
        if (evento != null && !evento.isBaja()) {
            evento.setBaja();
            this.persistencia.modificar(evento);
            this.persistencia.confirmarTransaccion();
        } else {
            this.persistencia.descartarTransaccion();
        }
    } catch (Exception e) {
        this.persistencia.descartarTransaccion();
        throw e;
    }
}


public void insertarParticipacion(Participacion participacion) {
    try {
        this.persistencia.iniciarTransaccion();
        var existente = this.persistencia.buscar(Participacion.class, participacion.getId());
        if (existente != null) {
            if (existente.isBaja()) {
                existente.setBajaFalse();
                existente.setRol(participacion.getRol());
                this.persistencia.modificar(existente);
            } else {
                throw new IllegalArgumentException("La persona ya está inscripta en este evento.");
            }
        } else {

            Evento evento = participacion.getEvento();
            evento.agregarParticipacion(participacion);
            this.persistencia.insertar(participacion);
        }
        this.persistencia.confirmarTransaccion();
    } catch (Exception e) {
        this.persistencia.descartarTransaccion();
        throw e;
    }
}

    public List<Participacion> listarParticipaciones() {
        var participaciones = this.persistencia.buscarTodos(Participacion.class);
        var listado = new ArrayList<Participacion>();
        for (var participacion : participaciones) {
            if (!participacion.isBaja()
                && participacion.getEvento() != null
                && !participacion.getEvento().isBaja()
                && participacion.getPersona() != null
                && !participacion.getPersona().isBaja()) {
                listado.add(participacion);
            }
        }
        return listado;
    }


    public void eliminarParticipacion(Participacion participacion) {
    try {
        this.persistencia.iniciarTransaccion();
        participacion.setBaja();
        this.persistencia.modificar(participacion);
        this.persistencia.confirmarTransaccion();
        Evento evento = participacion.getEvento();
        evento.removerParticipacion(participacion);
    } catch (Exception e) {
        this.persistencia.descartarTransaccion();
        throw e;
    }
    }





}
