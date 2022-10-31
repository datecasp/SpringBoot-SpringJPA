package com.datecasp.SpringBootSpringJPA.controllers;

import com.datecasp.SpringBootSpringJPA.repositories.AlumnoRepository;
import com.datecasp.SpringBootSpringJPA.entities.Alumno;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
     *  Devuelve un ResponseEntity<Alumno>
     **/
    @GetMapping("/api/Alumnos/AlumnoPorId/{id}")
    public ResponseEntity<Alumno> FindById(@PathVariable Long id) {

        Optional<Alumno> aluOpt= alumnoRepository.findById(id);
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
    @PostMapping("/api/Alumnos/CrearAlumno")
    public ResponseEntity<Alumno> CreateAlumno(@RequestBody Alumno alumno)
    {
        //Si meten id, badrequest 400
        if (alumno.getId() != null)
        {
            return ResponseEntity.badRequest().build();
        }

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
    @PutMapping("/api/Alumnos/ActualizarAlumno")
    public ResponseEntity<Alumno> UpdateAlumno(@RequestBody Alumno alumno)
    {
        //Si no existe el id, no es PUT si no POST 400
        if(alumno.getId() == null)
        {
            return ResponseEntity.badRequest().build();
        }
        //Si el alumno no existe, no es PUT si no POST 404
        if(!alumnoRepository.existsById(alumno.getId()))
        {
            return ResponseEntity.notFound().build();
        }
        //Si el alumno existe, lo actualizamos en bbdd
        Alumno result = alumnoRepository.save(alumno);
        //Devuelvo un ok 200 con el alumno
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/Alumnos/BorrarAlumno/{id}")
    public ResponseEntity<Alumno> DeleteAlumno(@PathVariable Long id)
    {
        //Si no existe el alumno, 404
        if(!alumnoRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        //Si existe el alumno, lo borramos
        alumnoRepository.deleteById(id);
        //Devolvemos No content 204
        return ResponseEntity.noContent().build();
    }
}
