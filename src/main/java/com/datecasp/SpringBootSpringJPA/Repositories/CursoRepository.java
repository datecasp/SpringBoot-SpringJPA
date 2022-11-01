package com.datecasp.SpringBootSpringJPA.repositories;

import com.datecasp.SpringBootSpringJPA.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>
{
    List<Curso> findCursosByAsignaturasAsignaturaId(Long asignaturaId);
}
