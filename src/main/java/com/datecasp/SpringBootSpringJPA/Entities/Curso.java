package com.datecasp.SpringBootSpringJPA.entities;

import com.datecasp.SpringBootSpringJPA.helpers.Enumerations;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Cursos")
public class Curso
{
    //atributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cursoId", nullable = false)
    private Long cursoId;
    @Column(name = "curso")
    private String curso;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "nivelCurso")
    private Enumerations.nivelCurso nivelCurso;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade =
            {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
        @JoinTable(name = "curso_asignaturas",
            joinColumns =  {@JoinColumn(name = "cursoId")},
            inverseJoinColumns = {@JoinColumn(name = "asignaturaId")})
    private Set<Asignatura> asignaturas = new HashSet<>();

    //constructors

    //Full atributes constructor
    public Curso(String curso, String descripcion, Enumerations.nivelCurso nivelCurso)
    {
        this.curso = curso;
        this.descripcion = descripcion;
        this.nivelCurso = nivelCurso;
    }

    //empty constructor
    public Curso(){}

    //getters and setters
    public Long getId(){return cursoId;}

    public String getCurso(){return curso;}

    public void setCurso(String curso){this.curso = curso;}

    public String getDescripcion(){return descripcion;}

    public void setDescripcion(String descripcion){this.descripcion = descripcion;}

    public Enumerations.nivelCurso getNivelCurso(){return nivelCurso;}

    public void setNivelCurso(Enumerations.nivelCurso nivelCurso){this.nivelCurso = nivelCurso;}
}
