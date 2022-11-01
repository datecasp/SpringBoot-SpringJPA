package com.datecasp.SpringBootSpringJPA.controllers;

import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import com.datecasp.SpringBootSpringJPA.entities.Asignatura;
import com.datecasp.SpringBootSpringJPA.entities.Curso;
import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.repositories.AsignaturaRepository;
import com.datecasp.SpringBootSpringJPA.repositories.CursoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @GetMapping("/api/Asignaturas/TodasLasASignaturas")
    @ApiOperation("Devuelve todas las asignaturas")
    public List<Asignatura> FindAll()

    {
        return asignaturaRepository.findAll();
    }

    /**
     *  GET Una asignatura concreta por su Id
     *
     *  http://localhost:8080/api/Asignatura/AsignaturaPorId
     *
     *  Devuelve un ResponseEntity<Asignatura>
     **/
    @GetMapping("/api/Asignaturas/AsignaturaPorId/{asignaturaId}")
    @ApiOperation("Devuelve una asignatura por su Id")
    public ResponseEntity<Asignatura> FindById(@PathVariable Long asignaturaId) {

        Optional<Asignatura> asignaturaOpt= asignaturaRepository.findById(asignaturaId);

        if(asignaturaOpt.isPresent())
        {
            return ResponseEntity.ok(asignaturaOpt.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *  GET Buscar todos los cursos de una asignatura
     *
     * @param asignaturaId, activo
     *
     *  http://localhost:8080/api/Asignaturas/CursosAsignatura/{asignaturaId}
     *
     *  Utiliza el asignaturaId para devolver los cursos que imparten la asignatura
     *
     *  El param activo se usa para filtrar la respuesta por
     *
     *        true -> devuelve sólo los cursos actualmente activos
     *
     *        false -> Añade cursos actuales o no
     *
     *  return ResponseEntity<List<Curso>>
     */
    @GetMapping("/api/Asignaturas/CursosAsignatura/{asignaturaId}")
    @ApiOperation("Devuelve todos cursos de una asignatura, dada su Id")
    public ResponseEntity<List<Curso>> getCursosAsignatura(@PathVariable Long asignaturaId, @ApiParam(value = "Sólo actual") Boolean activo)
    {
        if(!asignaturaRepository.existsById(asignaturaId)){return ResponseEntity.badRequest().build();}
        List<Curso> cursoList= cursoRepository.findCursosByAsignaturasAsignaturaId(asignaturaId);

        return ResponseEntity.ok(cursoList);
    }

    /**
     PUT Modifica Asignatura por su Id

     */
    @PutMapping("/api/Asignaturas/ModificarAsignatura/{asignaturaId}")
    @ApiOperation("Modifica una asignatura por su Id")
    public ResponseEntity<Asignatura> ModificarAsignatura(Long asignaturaId,@ApiParam(value = "Asignatura a modificar") @RequestBody Asignatura asignatura)
    {
        Optional<Asignatura> asignaturaOpt =asignaturaRepository.findById(asignaturaId);

        //Si el curso no existe, no es PUT si no POST 404
        if(!cursoRepository.existsById(asignaturaId))
        {
            return ResponseEntity.notFound().build();
        }
        else
        {
            asignaturaOpt.get().setAsignatura(asignatura.getAsignatura());
            asignaturaOpt.get().setDescripcion(asignatura.getDescripcion());

            //Actualizamos el valor del Curso en la DB
            asignaturaRepository.flush();
        }

        //Devuelvo un ok 200 con el curso
        return ResponseEntity.ok(asignaturaOpt.get());
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

    /**
     *   POST Crear asigantura
     */
    @PostMapping("/api/Asignaturas/CrearAsignatura")
    @ApiOperation(value = "Crea una nueva asignatura")
    public ResponseEntity<Asignatura> CrearAsignatura(@RequestBody Asignatura asignatura)
    {
        //Si meten id, badrequest 400
        if (asignatura.getId() != null)
        {
            return ResponseEntity.badRequest().build();
        }

        Asignatura result = asignaturaRepository.save(asignatura);

        return ResponseEntity.ok(result);
    }

    /**
     *  DELETE Borrar asignatura por su Id
     * @param asignaturaId
     * @return 204 NoContent
     */
    @DeleteMapping("/api/Asignatura/BorrarAsignatura/{asignaturaId}")
    @ApiOperation("Borra una asignatura por su Id")
    public ResponseEntity<Asignatura> DeleteAsignatura(@PathVariable Long asignaturaId)
    {
        //Si no existe el curso, 404
        if(!asignaturaRepository.existsById(asignaturaId))
        {
            return ResponseEntity.notFound().build();
        }
        //Si existe el curso, lo borramos
        asignaturaRepository.deleteById(asignaturaId);
        //Devolvemos No content 204
        return ResponseEntity.noContent().build();
    }
}
