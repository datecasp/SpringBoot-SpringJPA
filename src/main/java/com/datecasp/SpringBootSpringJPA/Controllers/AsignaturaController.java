package com.datecasp.SpringBootSpringJPA.controllers;

import com.datecasp.SpringBootSpringJPA.entities.Asignatura;
import com.datecasp.SpringBootSpringJPA.entities.Curso;
import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.repositories.AsignaturaRepository;
import com.datecasp.SpringBootSpringJPA.repositories.CursoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    /**
     PUT Añade Asignatura a un curso

     */
    @PutMapping("/api/Asignaturas/AñadirAsignaturasACurso/{cursoId}")
    @ApiOperation("Añade una lista de asignaturas a un curso")
    public ResponseEntity<Curso> AddAsignaturaACurso(@PathVariable Long cursoId, @ApiParam(value = "Lista de AsignaturaId´s") @RequestBody LinkedList<Long> asignaturaIds)
    {
        Optional<Curso> curso =cursoRepository.findById(cursoId);

        //Si el curso no existe, no es PUT si no POST 404
        if((!cursoRepository.existsById(cursoId))||(asignaturaIds.size() == 0))
        {
            return ResponseEntity.notFound().build();
        }
        else
        {
            for(Long id : asignaturaIds)
            {
                Optional<Asignatura> asignatura = asignaturaRepository.findById(id);

                if(asignatura.isPresent())
                {
                    curso.get().setAsignaturas(asignaturaRepository.findById(id).get());
                }
            }

            //Actualizamos el valor del Curso en la DB
            cursoRepository.flush();
        }

        //Devuelvo un ok 200 con el curso
        return ResponseEntity.ok(curso.get());
    }

    /**
     *  Borrar asignatura de curso
     */
    @PutMapping("/api/Asignaturas/{asignaturaId}/QuitarAsignaturaDeCurso/{cursoId}")
    @ApiOperation(value = "Quitar asignatura de curso")
    public ResponseEntity<Curso> RemoveAsignaturaDeCurso(@PathVariable(value = "cursoId") Long cursoId, @PathVariable(value = "asignaturaId") Long asignaturaId)
    {
        Optional<Curso> curso =cursoRepository.findById(cursoId);

        //Si el curso no existe, no es PUT si no POST 404
        if((!cursoRepository.existsById(cursoId))||(!asignaturaRepository.existsById(asignaturaId)))
        {
            return ResponseEntity.notFound().build();
        }
        else
        {
            curso.get().removeAsignatura(asignaturaId);
            cursoRepository.flush();
        }
        return ResponseEntity.ok(curso.get());
    }
}
