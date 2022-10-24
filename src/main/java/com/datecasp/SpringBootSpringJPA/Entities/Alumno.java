package com.datecasp.SpringBootSpringJPA.Entities;

import javax.persistence.*;

@Entity
@Table(name = "Alumnos")
public class Alumno
{
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer curso;

    //Constructores

    public Alumno(String nombre, Integer curso)
    {
        this.nombre = nombre;
        this.curso = curso;
    }

    public Alumno() { }

    //Getters y Setters

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Integer getCurso()
    {
        return curso;
    }

    public void setCurso(Integer curso)
    {
        this.curso = curso;
    }

    //ToString

    @Override
    public String toString()
    {
        return "Alumno{" +
                "_id=" + id +
                ", _nombre='" + nombre + '\'' +
                ", _curso=" + curso +
                '}';
    }
}
