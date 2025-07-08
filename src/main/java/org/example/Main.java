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
            Persona persona = new Persona("Lionel", "Messi", "messireal@mail.com", "10101010", "5450500");
            persistencia.insertar(persona);
            persistencia.confirmarTransaccion();
            System.out.println("Persona insertada correctamente.");
        } catch (Exception e) {
            persistencia.descartarTransaccion();
            e.printStackTrace();
        }
    }
}