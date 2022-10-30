package com.datecasp.SpringBootSpringJPA.exceptions;

public class AlumnoNotFoundException extends RuntimeException {

    public AlumnoNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}