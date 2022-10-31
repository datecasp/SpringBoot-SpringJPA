package com.datecasp.SpringBootSpringJPA.helpers;

import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import com.datecasp.SpringBootSpringJPA.entities.Curso;
import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.repositories.CursoRepository;

public class SeederDB
{
    public SeederDB(AlumnoRepository alumnosRepo, CursoRepository cursoRepo)
    {
        cursoRepo.save(new Curso("Robótica", "Curso de iniciación a la robótica", Enumerations.nivelCurso.INICIACION));
        cursoRepo.save(new Curso("Electroduendes", "Curso de entendimiento de los electroduendes", Enumerations.nivelCurso.MEDIO));
        cursoRepo.save(new Curso("Robótica avanzada", "Curso avanzado de robótica", Enumerations.nivelCurso.AVANZADO));

        alumnosRepo.save(new Alumno("Alberto", cursoRepo.getById(1L)));
        alumnosRepo.save(new Alumno("Juan", cursoRepo.getById(1L)));
        alumnosRepo.save(new Alumno("Federico", cursoRepo.getById(2L)));
        alumnosRepo.save(new Alumno("Luis", cursoRepo.getById(3L)));
    }
}
