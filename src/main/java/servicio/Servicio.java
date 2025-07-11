package servicio;

import jakarta.persistence.EntityManager;
import modelos.EstadoEvento;
import persistencia.Persistencia;
import modelos.Persona;
import modelos.Evento;
import modelos.Participacion;
import modelos.EstadoEvento;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Servicio {
    private Persistencia persistencia;
    private EntityManager em;

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

    public List<Evento> listarEventos() {
        try{
            return this.persistencia.buscarTodos(Evento.class);
        } catch (Exception e) {
            throw new RuntimeException("No se pudieron listar los eventos", e);
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

    /*public void listarParticipantes(){
        return this.persistencia.bus
    }*/
    public void insertarParticipacion(Participacion participacion) {
    try {
        this.persistencia.iniciarTransaccion();
        this.persistencia.insertar(participacion);
        this.persistencia.confirmarTransaccion();
    } catch (Exception e) {
        this.persistencia.descartarTransaccion();
        throw e;
    }
}

    public List<Participacion> listarParticipaciones() {
        return this.persistencia.buscarTodos(modelos.Participacion.class);
    }

}
