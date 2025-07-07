package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import persistencia.Persistencia;
import modelos.Persona;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("integradorPOO2025");
        Persistencia persistencia = new Persistencia(emf);

        try {
            persistencia.iniciarTransaccion();
            Persona persona = new Persona("Juan", "Pérez", "juan@mail.com", "12345678", "555-1234");
            persistencia.insertar(persona);
            persistencia.confirmarTransaccion();
            System.out.println("Persona insertada correctamente.");
        } catch (Exception e) {
            persistencia.descartarTransaccion();
            e.printStackTrace();
        }
    }
}