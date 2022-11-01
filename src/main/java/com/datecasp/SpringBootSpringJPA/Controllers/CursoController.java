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
public class CursoController
{

    //Atributo para tener acceso al contexto
    private CursoRepository cursoRepository;
    //Para poder buscar alumndao desde un Curso
    private AlumnoRepository alumnoRepository;
    private AsignaturaRepository asignaturaRepository;

    //Constructor con el contexto inyectado
    public CursoController(CursoRepository cursoRepository, AlumnoRepository alumnoRepository, AsignaturaRepository asignaturaRepository)
    {
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    /**
     *  GET Todos los cursos
     *
     *  http://localhost:8080/api/Cursos/TodosLosCursos
     *
     *  Devuelve una lista con todos los cursos
     **/
    @GetMapping("/api/Cursos/TodosLosCursos")
    @ApiOperation("Devuelve todos los cursos")
    public List<Curso> FindAll()

    {
        return cursoRepository.findAll();
    }

    /**
     *  GET Un curso concreto por su Id
     *
     *  http://localhost:8080/api/Cursos/CursoPorId
     *
     *  Devuelve un ResponseEntity<Curso>
     **/
    @GetMapping("/api/Cursos/CursoPorId/{cursoId}")
    @ApiOperation("Devuelve un curso por su Id")
    public ResponseEntity<Curso> FindById(@PathVariable Long cursoId) {

        Optional<Curso> cursoOpt= cursoRepository.findById(cursoId);
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
     * @param cursoId, activo
     *
     *  http://localhost:8080/api/Cursos/AlumnadoCurso/{cursoId}
     *
     *  Utiliza el cursoId para devolver el alumnado que tiene asigando
     *
     *  El param activo se usa para filtrar la respuesta por
     *
     *        true -> devuelve sólo los alumnos actuales del curso
     *
     *        false -> Añade alumnado que hizo el curso pero ya está dado de baja
     *
     *  return ResponseEntity<List<Alumno>>
     */
    @GetMapping("/api/Cursos/AlumnadoCurso/{cursoId}")
    @ApiOperation("Devuelve todo el alumnado de un curso, dado su Id")
    public ResponseEntity<List<Alumno>> getAlumnadoCurso(@PathVariable Long cursoId, @ApiParam(value = "Sólo actual") Boolean activo)
    {
        Optional<List<Alumno>> aluListOpt= Optional.of(alumnoRepository.findAll());
        List<Alumno> aluList= new ArrayList<>();

        if(aluListOpt.isPresent())
        {
            for (Alumno alu : aluListOpt.get())
            {
                if(!activo)
                {
                    if(alu.getCurso().getId() == cursoId){aluList.add(alu);}
                }else if((alu.getCurso().getId() == cursoId) && alu.getActivo())
                {
                    aluList.add(alu);
                }
            }
            if(aluList.isEmpty()){return ResponseEntity.noContent().build();}
            return ResponseEntity.ok(aluList);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *  GET Buscar todos las asignaturas de un curso
     *
     * @param cursoId, activo
     *
     *  http://localhost:8080/api/Cursos/AsignaturasCurso/{cursoId}
     *
     *  Utiliza el cursoId para devolver las asignaturas de ese curso
     *
     *  El param activo se usa para filtrar la respuesta por
     *
     *        true -> devuelve sólo las asignaturas actualmente activos
     *
     *        false -> Añade asignaturas actuales o no
     *
     *  return ResponseEntity<List<Asignaturas>>
     */
    @GetMapping("/api/Cursos/AsignaturasCurso/{cursoId}")
    @ApiOperation("Devuelve todas las asignaturas de un curso, dada su Id")
    public ResponseEntity<List<Asignatura>> getAsignaturasCurso(@PathVariable Long cursoId, @ApiParam(value = "Sólo actual") Boolean activo)
    {
        if(!cursoRepository.existsById(cursoId)){return ResponseEntity.badRequest().build();}
        List<Asignatura> asignaturaList= asignaturaRepository.findAsignaturasByCursosCursoId(cursoId);

        return ResponseEntity.ok(asignaturaList);
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
        if (curso.getId() != null)
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
    @ApiOperation("Actualiza un curso")
    public ResponseEntity<Curso> UpdateCurso(@PathVariable Long cursoId, @RequestBody Curso curso)
    {
        Optional<Curso> cursoViejo =cursoRepository.findById(cursoId);

        //Si el curso no existe, no es PUT si no POST 404
        if(!cursoRepository.existsById(cursoId))
        {
            return ResponseEntity.notFound().build();
        }
        else
        {
            cursoViejo.get().setCurso(curso.getCurso());
            cursoViejo.get().setDescripcion(curso.getDescripcion());
            cursoViejo.get().setNivelCurso(curso.getNivelCurso());
            //cursoViejo.get().setAsignaturas(curso.getAsignaturas());

            //Actualizamos el valor del Curso en la DB
            cursoRepository.flush();
        }

        //Devuelvo un ok 200 con el curso
        return ResponseEntity.ok(cursoViejo.get());
    }


    /**
     *  DELETE Borrar cursopor su Id
     * @param cursoId
     * @return 204 NoContent
     */
    @DeleteMapping("/api/Cursos/BorrarCurso/{cursoId}")
    @ApiOperation("Borra un curso dado su Id")
    public ResponseEntity<Curso> DeleteCurso(@PathVariable Long cursoId)
    {
        //Si no existe el curso, 404
        if(!cursoRepository.existsById(cursoId))
        {
            return ResponseEntity.notFound().build();
        }
        //Si existe el curso, lo borramos
        cursoRepository.deleteById(cursoId);
        //Devolvemos No content 204
        return ResponseEntity.noContent().build();
    }
}
