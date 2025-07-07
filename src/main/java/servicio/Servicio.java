package servicio;

import java.time.LocalDate;
import persistencia.Persistencia;
import modelos.Persona;

public class Servicio {
    private Persistencia persistencia;

    public Servicio(Persistencia p) {
      this.persistencia = p;
    }
    public void insertarPersona(String nombre, String apellido,  String correo, String dni, String telefono) {
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


    }
