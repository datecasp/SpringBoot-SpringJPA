package com.datecasp.SpringBootSpringJPA.Controllers;

import com.datecasp.SpringBootSpringJPA.Exceptions.AlumnoNotFoundException;
import com.datecasp.SpringBootSpringJPA.Repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.Entities.Alumno;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlumnoController
{
    //Atributo para tener acceso al contexto
    private AlumnoRepository alumnoRepository;

    //Constructor con el contexto inyectado
    public AlumnoController(AlumnoRepository alumnoRepository)
    {
        this.alumnoRepository = alumnoRepository;
    }

    /**
     *  GET Todos los alumnos
     *
     *  http://localhost:8080/api/Alumnos/TodosLosAlumnos
     *
     *  Devuelve una lista con todos los alumnos
     **/
    @GetMapping("/api/Alumnos/TodosLosAlumnos")
    public List<Alumno> FindAll()
    {
        return alumnoRepository.findAll();
    }

    /**
     *  GET Un alumno concreto por su Id
     *
     *  http://localhost:8080/api/Alumnos/AlumnoPorId
     *
     *  Devuelve un alumno
     **/
    @GetMapping("/api/Alumnos/AlumnoPorId/{id}")
    public Alumno FindById(@PathVariable Long id) {

        return alumnoRepository.findById(id)
                .orElseThrow(() -> new AlumnoNotFoundException(id));
    }

    @PostMapping("/api/Alumnos/GuardarAlumnos")
    public Alumno CreateAlumnos(@RequestBody Alumno alumno)
    {
        return alumnoRepository.save(alumno);
    }
}
