package com.datecasp.SpringBootSpringJPA.controllers;

import com.datecasp.SpringBootSpringJPA.entities.Asignatura;
import com.datecasp.SpringBootSpringJPA.entities.Curso;
import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.repositories.AsignaturaRepository;
import com.datecasp.SpringBootSpringJPA.repositories.CursoRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AsignaturaController
{
    //Atributo para tener acceso al contexto
    private CursoRepository cursoRepository;
    //Para poder buscar alumndao desde un Curso
    private AsignaturaRepository asignaturaRepository;

    //Constructor con el contexto inyectado

    public AsignaturaController(CursoRepository cursoRepository, AsignaturaRepository asignaturaRepository)
    {
        this.cursoRepository = cursoRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    /**
     *  GET Todas las asignaturas
     *
     *  http://localhost:8080/api/Asignaturas/TodasLasASignaturas
     *
     *  Devuelve una lista con todas las asignaturas
     **/
    @GetMapping("/api/ASignaturas/TodasLasASignaturas")
    @ApiOperation("Devuelve todas las asignaturas")
    public List<Asignatura> FindAll()

    {
        return asignaturaRepository.findAll();
    }
}
