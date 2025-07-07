package Modelos;

public class Persona {
private String nombre;
private String apellido;
private String correo;
private String dni;
private String telefono;



    public Persona(String nombre, String apellido, String correo, String dni, String telefono) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacío.");
        }
        if (correo == null || correo.isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío.");
            // revisar que tenga @ / sea valido
        }
        if (dni == null || dni.isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacío.");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede ser nulo o vacío.");
        }

        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.dni = dni;
    }
}
