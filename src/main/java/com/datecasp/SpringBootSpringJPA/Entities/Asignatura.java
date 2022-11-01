package com.datecasp.SpringBootSpringJPA.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "asignaturas")
public class Asignatura
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asignaturaId", nullable = false)
    private Long asignaturaId;
    @Column(name = "asignatura")
    private String asignatura;
    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy = "asignaturas")
    @JsonIgnore
    private Set<Curso> cursos = new HashSet<>();

    //Constructores

    public Asignatura(){}

    public Asignatura(String asignatura, String descripcion)
    {
        this.asignatura = asignatura;
        this.descripcion = descripcion;
    }

    //getters y setters

    public Long getAsignaturaId()
    {
        return asignaturaId;
    }

    public String getAsignatura()
    {
        return asignatura;
    }

    public void setAsignatura(String asignatura)
    {
        this.asignatura = asignatura;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    //ToString

    @Override
    public String toString()
    {
        return "Asignatura{" +
                "asignaturaId=" + asignaturaId +
                ", asignatura='" + asignatura + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
