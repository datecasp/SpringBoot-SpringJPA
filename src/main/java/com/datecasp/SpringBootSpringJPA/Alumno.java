package com.datecasp.SpringBootSpringJPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Alumno
{
    //Atributos
    @Id
    @GeneratedValue
    private Long _id;
    private String _nombre;
    private Integer _curso;

    //Constructores

    public Alumno(String _nombre, Integer _curso)
    {
        this._nombre = _nombre;
        this._curso = _curso;
    }

    public Alumno() { }

    //Getters y Setters

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public String get_nombre()
    {
        return _nombre;
    }

    public void set_nombre(String _nombre)
    {
        this._nombre = _nombre;
    }

    public Integer get_curso()
    {
        return _curso;
    }

    public void set_curso(Integer _curso)
    {
        this._curso = _curso;
    }

    //ToString

    @Override
    public String toString()
    {
        return "Alumno{" +
                "_id=" + _id +
                ", _nombre='" + _nombre + '\'' +
                ", _curso=" + _curso +
                '}';
    }
}
