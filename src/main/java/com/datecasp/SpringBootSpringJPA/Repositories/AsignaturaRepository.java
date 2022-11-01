package com.datecasp.SpringBootSpringJPA.repositories;

import com.datecasp.SpringBootSpringJPA.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long>
{
    List<Asignatura> findAsignaturasByCursosCursoId(Long cursoId);
}
