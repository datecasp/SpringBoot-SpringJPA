package com.datecasp.SpringBootSpringJPA.controllers;

import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import com.datecasp.SpringBootSpringJPA.entities.Curso;
import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.repositories.CursoRepository;
import io.swagger.annotations.ApiOperation;
import jdk.jfr.Name;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CursoController
{

    //Atributo para tener acceso al contexto
    private CursoRepository cursoRepository;
    //Para poder buscar alumndao desde un Curso
    private AlumnoRepository alumnoRepository;

    //Constructor con el contexto inyectado
    public CursoController(CursoRepository cursoRepository, AlumnoRepository alumnoRepository)
    {
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
    }

    /**
     *  GET Todos los alumnos
     *
     *  http://localhost:8080/api/Alumnos/TodosLosAlumnos
     *
     *  Devuelve una lista con todos los alumnos
     **/
    @GetMapping("/api/Cursos/TodosLosCursos")
    @ApiOperation("Devuelve todos los cursos")
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
    @ApiOperation("Devuelve un curso por su Id")
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
     *  GET Buscar todos los alumnos de un curso
     *
     * @param cursoId
     *
     *  http://localhost:8080/api/Cursos/AlumnadoCurso/{cursoId}
     *
     *  return ResponseEntity<List<Alumno>>
     */
    @GetMapping("/api/Cursos/AlumnadoCurso/{cursoId}")
    @ApiOperation("Devuelve todo el alumnado de un curso, dado su Id")
    public ResponseEntity<List<Alumno>> getAlumnadoCurso(@PathVariable Long cursoId )
    {
        Optional<List<Alumno>> aluListOpt= Optional.of(alumnoRepository.findAll());
        List<Alumno> aluList= new ArrayList<>();

        if(aluListOpt.isPresent())
        {
            for (Alumno alu : aluListOpt.get())
            {
                if(alu.getCurso().getCursoId() == cursoId){aluList.add(alu);}
            }
            if(aluList.isEmpty()){return ResponseEntity.notFound().build();}
            return ResponseEntity.ok(aluList);
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
    @ApiOperation("Crea un curso")
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
    @PutMapping("/api/Cursos/ActualizarCurso/{cursoId}")
    @ApiOperation("Actualiza un curso, las ids deben coincidir")
    public ResponseEntity<Curso> UpdateCurso(@PathVariable Long cursoId, @RequestBody Curso curso)
    {
        //Comprobamos los Ids
        if (cursoId != curso.getCursoId()){ return ResponseEntity.badRequest().build(); }
        //Si no existe el id, no es PUT si no POST 400
        if(curso.getCursoId() == null)
        {
            return ResponseEntity.badRequest().build();
        }
        //Si el curso no existe, no es PUT si no POST 404
        if(!cursoRepository.existsById(curso.getCursoId()))
        {
            return ResponseEntity.notFound().build();
        }
        //Si el curso existe, lo actualizamos en bbdd
        Curso result = cursoRepository.save(curso);
        //Devuelvo un ok 200 con el alumno
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/Cursos/BorrarCurso/{id}")
    @ApiOperation("Borra un curso dado su Id")
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
