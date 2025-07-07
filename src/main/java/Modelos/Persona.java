package modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @Column(length = 20, nullable = false)
    private String dni;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String apellido;

    @Column(length = 100, nullable = false)
    private String correo;

    @Column(length = 20, nullable = false)
    private String telefono;

    protected Persona() {
    }

    public Persona(String nombre, String apellido, String correo, String dni, String telefono) {
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setDni(dni);
        setTelefono(telefono);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacío.");
        }
        this.dni = dni.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
        if (nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre no puede tener más de 50 caracteres.");
        }
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacío.");
        }
        if (apellido.length() > 50) {
            throw new IllegalArgumentException("El apellido no puede tener más de 50 caracteres.");
        }
        this.apellido = apellido.trim();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío.");
        }
        if (correo.length() > 100) {
            throw new IllegalArgumentException("El correo no puede tener más de 100 caracteres.");
        }
        if (!correo.contains("@")) {
            throw new IllegalArgumentException("El correo debe ser válido.");
        }
        this.correo = correo.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede ser nulo o vacío.");
        }
        if (telefono.length() > 20) {
            throw new IllegalArgumentException("El teléfono no puede tener más de 20 caracteres.");
        }
        this.telefono = telefono.trim();
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s)", nombre, apellido, dni);
    }
}
