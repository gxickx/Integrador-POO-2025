package servicio;

import java.time.LocalDate;
import persistencia.Persistencia;
import modelos.Persona;
import java.util.UUID;


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
        } catch (Exception var6) {
            this.persistencia.descartarTransaccion();
            throw var6;
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

}
