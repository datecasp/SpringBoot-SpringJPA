package com.datecasp.SpringBootSpringJPA.Exceptions;

public class AlumnoNotFoundException extends RuntimeException {

    public AlumnoNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}