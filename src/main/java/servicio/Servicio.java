package servicio;

import java.time.LocalDate;
import persistencia.Persistencia;
import modelos.Persona;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;


public class Servicio {
    private Persistencia persistencia;

    public Servicio(Persistencia p) {
        this.persistencia = p;
    }


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

}
