package com.datecasp.SpringBootSpringJPA.entities;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "Alumnos")
public class Alumno
{
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alumnoId", nullable = false)
    private Long alumnoId;
    @Column(name = "nombre")
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cursoId", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Curso curso;

    //Constructores

    public Alumno(String nombre, Curso curso)
    {
        this.nombre = nombre;
        this.curso = curso;
    }

    public Alumno() { }

    //Getters y Setters

    public Long getId()
    {
        return alumnoId;
    }

    public void setId(Long id)
    {
        this.alumnoId = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Curso getCurso()
    {
        return curso;
    }

    public void setCurso(Curso curso)
    {
        this.curso = curso;
    }

    //ToString

    @Override
    public String toString()
    {
        return "Alumno{" +
                "_id=" + alumnoId +
                ", _nombre='" + nombre + '\'' +
                ", _curso=" + curso +
                '}';
    }
}
