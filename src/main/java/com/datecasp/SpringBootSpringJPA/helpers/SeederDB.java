package com.datecasp.SpringBootSpringJPA.helpers;

import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import com.datecasp.SpringBootSpringJPA.entities.Asignatura;
import com.datecasp.SpringBootSpringJPA.entities.Curso;
import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.repositories.AsignaturaRepository;
import com.datecasp.SpringBootSpringJPA.repositories.CursoRepository;

public class SeederDB
{
    public SeederDB(AlumnoRepository alumnosRepo, CursoRepository cursoRepo, AsignaturaRepository asigRepo)
    {
        cursoRepo.save(new Curso("Robótica", "Curso de iniciación a la robótica", Enumerations.nivelCurso.INICIACION));
        cursoRepo.save(new Curso("Electroduendes", "Curso de entendimiento de los electroduendes", Enumerations.nivelCurso.MEDIO));
        cursoRepo.save(new Curso("Robótica avanzada", "Curso avanzado de robótica", Enumerations.nivelCurso.AVANZADO));

        alumnosRepo.save(new Alumno("Alberto", cursoRepo.getById(1L)));
        alumnosRepo.save(new Alumno("Juan", cursoRepo.getById(1L)));
        alumnosRepo.save(new Alumno("Federico", cursoRepo.getById(2L)));
        alumnosRepo.save(new Alumno("Luis", cursoRepo.getById(3L)));

        asigRepo.save(new Asignatura("Ciencias", "Asignatura de ciencias"));
        asigRepo.save(new Asignatura("Mates", "Asignatura de matemáticas"));
        asigRepo.save(new Asignatura("Álgebra", "Asignatura de álgebra"));
    }
}
