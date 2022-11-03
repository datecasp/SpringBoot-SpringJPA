package com.datecasp.SpringBootSpringJPA.controllers;

import com.datecasp.SpringBootSpringJPA.entities.Curso;
import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import com.datecasp.SpringBootSpringJPA.repositories.CursoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class AlumnoController
{
    //Atributo para tener acceso al contexto
    private AlumnoRepository alumnoRepository;
    private CursoRepository cursoRepository;

    //Constructor con el contexto inyectado
    public AlumnoController(AlumnoRepository alumnoRepository, CursoRepository cursoRepository)
    {
        this.alumnoRepository = alumnoRepository;
        this.cursoRepository = cursoRepository;
    }

    /**
     *  GET Todos los alumnos
     *
     *  http://localhost:8080/api/Alumnos/TodosLosAlumnos
     *
     *  El param activo se usa para filtrar la respuesta por
     *
     *            true -> devuelve sólo los alumnos actuales con curso
     *
     *            false -> devuelve el alumnado incluso antiguo
     *
     *  Devuelve ResponseEntity<List<Alumnos>>
     **/
    @GetMapping("/User/Alumnos/TodosLosAlumnos")
    @ApiOperation("Devuelve todo el alumnado")
    public ResponseEntity<List<Alumno>> FindAll(@ApiParam(value = "Sólo actual") Boolean activo)
    {
        Optional<List<Alumno>> alumnos = Optional.of(alumnoRepository.findAll());
        List<Alumno> listaAlumnos = new ArrayList<>();

        if(alumnos.isPresent())
        {
            for (Alumno alu : alumnos.get())
            {
                if (!activo)
                {
                    return ResponseEntity.ok(alumnos.get());
                }
                else if (alu.getActivo())
                {
                    listaAlumnos.add(alu);
                }
            }
            if(listaAlumnos.isEmpty()){return ResponseEntity.noContent().build();}
            return ResponseEntity.ok(listaAlumnos);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *  GET Un alumno concreto por su Id
     *
     *  http://localhost:8080/api/Alumnos/AlumnoPorId
     *
     *  Devuelve un ResponseEntity<Alumno>
     **/
    @GetMapping("/User/Alumnos/AlumnoPorId/{alumnoId}")
    @ApiOperation("Devuelve un alumno, dada su Id")
    public ResponseEntity<Alumno> FindById(@PathVariable Long alumnoId) {

        Optional<Alumno> aluOpt= alumnoRepository.findById(alumnoId);
        if(aluOpt.isPresent())
        {
            return ResponseEntity.ok(aluOpt.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *  Post Un alumno concreto
     *
     *  http://localhost:8080/api/Alumnos/CrearAlumno
     *
     *  Devuelve un ResponseEntity<Alumno>
     **/
    @PostMapping("/Admin/Alumnos/CrearAlumno")
    @ApiOperation("Crea un alumno nuevo")
    public ResponseEntity<Alumno> CreateAlumno(Long cursoId, @RequestBody Alumno alumno)
    {
        //Si meten id, badrequest 400
        if (alumno.getId() != null)
        {
            return ResponseEntity.badRequest().build();
        }
        //Asignamos Curso a Alumno
        alumno.setCurso(cursoRepository.getById(cursoId));
        //Seteamos Activo como true
        alumno.setActivo(true);
        //Guardamos el Alumno en la DB
        Alumno result = alumnoRepository.save(alumno);

        return ResponseEntity.ok(result);
    }

    /**
     *  Updte Un alumno concreto por su Id
     *
     *  http://localhost:8080/api/Alumnos/ActualizarAlumno/{id}
     *
     *  Devuelve un ResponseEntity<Alumno>
     **/
    @PutMapping("/Admin/Alumnos/ActualizarAlumno/{alumnoId}")
    @ApiOperation("Actualiza un alumno")
    public ResponseEntity<Alumno> UpdateAlumno(@PathVariable Long alumnoId, @RequestBody Alumno alumno)
    {
        Optional<Alumno> aluViejo = alumnoRepository.findById(alumnoId);

        //Si el alumno no existe, no es PUT si no POST 404
        if(!alumnoRepository.existsById(alumnoId))
        {
            return ResponseEntity.notFound().build();
        }
        else {
            //Seteamos en el objeto de la DB los parametros pasados en la respuesta
            aluViejo.get().setNombre(alumno.getNombre());
            aluViejo.get().setActivo(alumno.getActivo());
            //Actualizamos el valor del Alumno en la DB
            alumnoRepository.flush();
        }
        //Devuelvo un ok 200 con el alumno
        return ResponseEntity.ok(aluViejo.get());
    }

    /**
     *  Updte El curso de un alumno concreto por su Id
     *
     *  http://localhost:8080/api/Alumnos/ActualizarCursoAlumno/{id}
     *
     *  Si el alumno estaba ya en un curso, le cambia de curso
     *
     *  Si el alumno no estaba en un curso, Activo = false,
     *
     *  cambio Activo a true y el curso del alumno
     *
     *  Devuelve un ResponseEntity<Alumno>
     **/
    @PutMapping("/Admin/Alumnos/ActualizarCursoAlumno/{alumnoId}")
    @ApiOperation("Apuntar o cambiar de curso a un alumno")
    public ResponseEntity<Alumno> UpdateCursoAlumno(@PathVariable Long alumnoId, Long cursoId)
    {
        Optional<Alumno> alumno = alumnoRepository.findById(alumnoId);
        Optional<Curso> curso = cursoRepository.findById(cursoId);

        if(!alumno.isPresent()||!curso.isPresent()){return ResponseEntity.badRequest().build();}
        //Seteamos Activo a true por si no lo está
        alumno.get().setActivo(true);
        //Cargamos el curso nuevo en el Alumno
        alumno.get().setCurso(curso.get());
        //Guardamos los cambios en DB
        alumnoRepository.flush();

        return ResponseEntity.ok(alumno.get());
    }
    @DeleteMapping("/Admin/Alumnos/BorrarAlumno/{alumnoId}")
    @ApiOperation("Inactiva un alumno, dada su id")
    public ResponseEntity<Alumno> DeleteAlumno(@PathVariable Long alumnoId)
    {
        Optional<Alumno> aluViejo = alumnoRepository.findById(alumnoId);

        //Si no existe el alumno, 404
        if(!alumnoRepository.existsById(alumnoId))
        {
            return ResponseEntity.notFound().build();
        }
        else
        {
            //Ponemos el flag "Activo" en false
            aluViejo.get().setActivo(false);
            //Guardamos el cambio en la DB
            cursoRepository.flush();
        }
        //Devolvemos No content 204
        return ResponseEntity.noContent().build();
    }
}
