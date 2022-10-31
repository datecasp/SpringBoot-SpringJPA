package com.datecasp.SpringBootSpringJPA.controllers;

import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import com.datecasp.SpringBootSpringJPA.entities.Curso;
import com.datecasp.SpringBootSpringJPA.repositories.CursoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CursoController
{

    //Atributo para tener acceso al contexto
    private CursoRepository cursoRepository;

    //Constructor con el contexto inyectado
    public CursoController(CursoRepository cursoRepository)
    {
        this.cursoRepository = cursoRepository;
    }

    /**
     *  GET Todos los alumnos
     *
     *  http://localhost:8080/api/Alumnos/TodosLosAlumnos
     *
     *  Devuelve una lista con todos los alumnos
     **/
    @GetMapping("/api/Cursos/TodosLosCursos")
    public List<Curso> FindAll()
    {
        return cursoRepository.findAll();
    }

    /**
     *  GET Un alumno concreto por su Id
     *
     *  http://localhost:8080/api/Alumnos/AlumnoPorId
     *
     *  Devuelve un ResponseEntity<Alumno>
     **/
    @GetMapping("/api/Cursos/CursoPorId/{id}")
    public ResponseEntity<Curso> FindById(@PathVariable Long id) {

        Optional<Curso> cursoOpt= cursoRepository.findById(id);
        if(cursoOpt.isPresent())
        {
            return ResponseEntity.ok(cursoOpt.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *  Post Un curso concreto
     *
     *  http://localhost:8080/api/Cursos/CrearCurso
     *
     *  Devuelve un ResponseEntity<Curso>
     **/
    @PostMapping("/api/Cursos/CrearCurso")
    public ResponseEntity<Curso> CreateCurso(@RequestBody Curso curso)
    {
        //Si meten id, badrequest 400
        if (curso.getCursoId() != null)
        {
            return ResponseEntity.badRequest().build();
        }

        Curso result = cursoRepository.save(curso);

        return ResponseEntity.ok(result);
    }

    /**
     *  Updte Un curso concreto por su Id
     *
     *  http://localhost:8080/api/Cursos/ActualizarCurso/{id}
     *
     *  Devuelve un ResponseEntity<Curso>
     **/
    @PutMapping("/api/Cursos/ActualizarCurso")
    public ResponseEntity<Curso> UpdateCurso(@RequestBody Curso curso)
    {
        //Si no existe el id, no es PUT si no POST 400
        if(curso.getCursoId() == null)
        {
            return ResponseEntity.badRequest().build();
        }
        //Si el alumno no existe, no es PUT si no POST 404
        if(!cursoRepository.existsById(curso.getCursoId()))
        {
            return ResponseEntity.notFound().build();
        }
        //Si el alumno existe, lo actualizamos en bbdd
        Curso result = cursoRepository.save(curso);
        //Devuelvo un ok 200 con el alumno
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/Cursos/BorrarCurso/{id}")
    public ResponseEntity<Curso> DeleteCurso(@PathVariable Long id)
    {
        //Si no existe el curso, 404
        if(!cursoRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        //Si existe el curso, lo borramos
        cursoRepository.deleteById(id);
        //Devolvemos No content 204
        return ResponseEntity.noContent().build();
    }
}
